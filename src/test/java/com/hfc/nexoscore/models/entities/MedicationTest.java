package com.hfc.nexoscore.models.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MedicationTest {

    @Test
    void shouldCreateMedicationUsingBuilder() {
        UUID medicationId = UUID.randomUUID();
        LocalDate startDate = LocalDate.now().minusMonths(1);
        Patient patient = Patient.builder().id(UUID.randomUUID()).fullName("John Doe").build();

        Medication medication = Medication.builder()
                .id(medicationId)
                .name("Ritalin")
                .reason("ADHD")
                .dosage("10mg")
                .startDate(startDate)
                .patient(patient)
                .build();

        assertThat(medication.getId()).isEqualTo(medicationId);
        assertThat(medication.getName()).isEqualTo("Ritalin");
        assertThat(medication.getReason()).isEqualTo("ADHD");
        assertThat(medication.getDosage()).isEqualTo("10mg");
        assertThat(medication.getStartDate()).isEqualTo(startDate);
        assertThat(medication.getPatient()).isEqualTo(patient);
    }
}
