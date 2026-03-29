package com.hfc.nexoscore.services;

import com.hfc.nexoscore.dtos.PatientCreatedEvent;
import com.hfc.nexoscore.exception.BusinessRuleException;
import com.hfc.nexoscore.exception.ResourceNotFoundException;
import com.hfc.nexoscore.models.entities.Patient;
import com.hfc.nexoscore.repositories.PatientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Unidade - PatientService")
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private PatientService patientService;

    @Test
    @DisplayName("Deve criar um paciente com sucesso")
    void shouldCreatePatient_Success() {
        // Arrange
        String fullName = "John Doe";
        String cpf = "12345678900";
        UUID generatedId = UUID.randomUUID();
        Patient savedPatient = createPatientFixture(generatedId, fullName, cpf);

        when(patientRepository.findByCpf(cpf)).thenReturn(Optional.empty());
        when(patientRepository.save(any(Patient.class))).thenReturn(savedPatient);

        // Act
        UUID resultId = patientService.createPatient(fullName, cpf);

        // Assert
        assertThat(resultId).isEqualTo(generatedId);
        verify(patientRepository, times(1)).findByCpf(cpf);
        verify(patientRepository, times(1)).save(any(Patient.class));
        verify(kafkaTemplate, times(1)).send(eq("patient-created-topic"), eq(generatedId.toString()), any(PatientCreatedEvent.class));
    }

    @Test
    @DisplayName("Deve lançar BusinessRuleException ao tentar criar paciente com CPF já existente")
    void shouldThrowBusinessRuleException_WhenCpfAlreadyExists() {
        // Arrange
        String fullName = "John Doe";
        String cpf = "12345678900";
        Patient existingPatient = createPatientFixture(UUID.randomUUID(), fullName, cpf);

        when(patientRepository.findByCpf(cpf)).thenReturn(Optional.of(existingPatient));

        // Act & Assert
        assertThatThrownBy(() -> patientService.createPatient(fullName, cpf))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Já existe um paciente cadastrado com este CPF.");

        verify(patientRepository, times(1)).findByCpf(cpf);
        verify(patientRepository, never()).save(any());
        verify(kafkaTemplate, never()).send(anyString(), anyString(), any());
    }

    @Test
    @DisplayName("Deve retornar um paciente quando o ID existir")
    void shouldReturnPatient_WhenIdExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        Patient patient = createPatientFixture(id, "John Doe", "12345678900");
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        // Act
        Patient result = patientService.getPatientById(id);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getFullName()).isEqualTo("John Doe");
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando o paciente não for encontrado por ID")
    void shouldThrowResourceNotFoundException_WhenPatientNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> patientService.getPatientById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Paciente não encontrado com o ID: " + id);

        verify(patientRepository, times(1)).findById(id);
    }

    // --- Fixtures ---

    private Patient createPatientFixture(UUID id, String fullName, String cpf) {
        return Patient.builder()
                .id(id)
                .fullName(fullName)
                .cpf(cpf)
                .build();
    }
}
