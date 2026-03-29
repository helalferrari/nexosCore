package com.hfc.nexoscore.dtos;

import com.hfc.nexoscore.models.enums.Guardianship;
import com.hfc.nexoscore.models.enums.MaritalStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testes de Unidade - DTOs")
class DtoTest {

    @Test
    @DisplayName("Deve testar PatientRequestDTO e seus métodos de record")
    void testPatientRequestDTO() {
        PatientRequestDTO dto = new PatientRequestDTO(
                "John Doe", LocalDate.now().minusYears(10), "123", "School", "5th",
                "Mother", 35, "PhD", "Doctor", "Portuguese", 1,
                MaritalStatus.MARRIED, Guardianship.BOTH
        );

        assertThat(dto.fullName()).isEqualTo("John Doe");
        assertThat(dto.cpf()).isEqualTo("123");
        assertThat(dto.toString()).contains("John Doe");
        
        PatientRequestDTO sameDto = new PatientRequestDTO(
                "John Doe", dto.birthDate(), "123", "School", "5th",
                "Mother", 35, "PhD", "Doctor", "Portuguese", 1,
                MaritalStatus.MARRIED, Guardianship.BOTH
        );
        assertThat(dto)
                .isEqualTo(sameDto)
                .hasSameHashCodeAs(sameDto);
    }

    @Test
    @DisplayName("Deve testar PatientRequest e seus métodos de record")
    void testPatientRequest() {
        PatientRequest dto = new PatientRequest("John Doe", "123");
        assertThat(dto.fullName()).isEqualTo("John Doe");
        assertThat(dto.cpf()).isEqualTo("123");
        assertThat(dto.toString()).contains("John Doe");
    }

    @Test
    @DisplayName("Deve testar PatientCreatedEvent e seus métodos de record")
    void testPatientCreatedEvent() {
        UUID id = UUID.randomUUID();
        PatientCreatedEvent event = new PatientCreatedEvent(id, "John Doe", "123");
        assertThat(event.patientId()).isEqualTo(id);
        assertThat(event.name()).isEqualTo("John Doe");
        assertThat(event.cpf()).isEqualTo("123");
        assertThat(event.toString()).contains(id.toString());
    }
}
