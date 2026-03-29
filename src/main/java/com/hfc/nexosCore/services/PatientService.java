package com.hfc.nexosCore.services;

import com.hfc.nexosCore.dtos.PatientCreatedEvent;
import com.hfc.nexosCore.exception.BusinessRuleException;
import com.hfc.nexosCore.exception.ResourceNotFoundException;
import com.hfc.nexosCore.models.entities.Patient;
import com.hfc.nexosCore.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "patient-created-topic";

    @Transactional
    public UUID createPatient(String fullName, String cpf) {
        log.info("Iniciando criação de paciente: {}", fullName);
        
        // Regra de Negócio: Verificar se o CPF já está cadastrado
        if (patientRepository.findByCpf(cpf).isPresent()) {
            log.warn("Tentativa de cadastro com CPF já existente: {}", cpf);
            throw new BusinessRuleException("Já existe um paciente cadastrado com este CPF.");
        }

        // Persistência real da entidade no banco de dados PostgreSQL do NexosCore
        Patient patient = Patient.builder()
                .fullName(fullName)
                .cpf(cpf)
                .build();
        
        patient = patientRepository.save(patient);
        UUID patientId = patient.getId();
        
        log.info("Paciente salvo no banco PostgreSQL (NexosCore) com ID: {}", patientId);

        // Envia o nome correto no evento para o Kafka
        PatientCreatedEvent event = new PatientCreatedEvent(patientId, fullName, cpf);
        
        // Publica no Kafka usando o ID como chave para garantir ordenação por paciente
        kafkaTemplate.send(TOPIC, patientId.toString(), event);
        log.info("Evento publicado no Kafka: {}", event);

        return patientId;
    }

    public Patient getPatientById(UUID id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com o ID: " + id));
    }
}
