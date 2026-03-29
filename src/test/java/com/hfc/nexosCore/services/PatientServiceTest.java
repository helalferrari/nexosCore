package com.hfc.nexosCore.services;

import com.hfc.nexosCore.dtos.PatientCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private PatientService patientService;

    @Test
    void shouldCreatePatientAndPublishEvent() {
        // Act
        UUID resultId = patientService.createPatient("João Silva", "12345678900");

        // Assert
        assertThat(resultId).isNotNull();
        verify(kafkaTemplate, times(1)).send(
                eq("patient-created-topic"), 
                eq(resultId.toString()), 
                any(PatientCreatedEvent.class)
        );
    }
}
