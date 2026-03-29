package com.hfc.nexosCore.services;

import com.hfc.nexosCore.dtos.PatientCreatedEvent;
import com.hfc.nexosCore.models.entities.Patient;
import com.hfc.nexosCore.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
