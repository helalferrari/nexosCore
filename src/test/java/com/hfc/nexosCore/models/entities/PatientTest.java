package com.hfc.nexosCore.models.entities;

import com.hfc.nexosCore.models.enums.Guardianship;
import com.hfc.nexosCore.models.enums.MaritalStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PatientTest {

    @Test
    void shouldCreatePatientUsingBuilder() {
        UUID patientId = UUID.randomUUID();
        LocalDate birthDate = LocalDate.now().minusYears(10);

        Patient patient = Patient.builder()
                .id(patientId)
                .fullName("John Doe")
                .birthDate(birthDate)
                .schoolName("Sample School")
                .grade("5th Grade")
                .motherName("Jane Doe")
                .motherAge(35)
                .motherEducation("High School")
                .motherOccupation("Teacher")
                .primaryLanguage("English")
                .learningAge(3)
                .parentsMaritalStatus(MaritalStatus.MARRIED)
                .guardianship(Guardianship.BOTH)
                .build();

        assertThat(patient.getId()).isEqualTo(patientId);
        assertThat(patient.getFullName()).isEqualTo("John Doe");
        assertThat(patient.getBirthDate()).isEqualTo(birthDate);
        assertThat(patient.getSchoolName()).isEqualTo("Sample School");
        assertThat(patient.getGrade()).isEqualTo("5th Grade");
        assertThat(patient.getMotherName()).isEqualTo("Jane Doe");
        assertThat(patient.getMotherAge()).isEqualTo(35);
        assertThat(patient.getMotherEducation()).isEqualTo("High School");
        assertThat(patient.getMotherOccupation()).isEqualTo("Teacher");
        assertThat(patient.getPrimaryLanguage()).isEqualTo("English");
        assertThat(patient.getLearningAge()).isEqualTo(3);
        assertThat(patient.getParentsMaritalStatus()).isEqualTo(MaritalStatus.MARRIED);
        assertThat(patient.getGuardianship()).isEqualTo(Guardianship.BOTH);
        assertThat(patient.getMedications()).isEmpty();
    }

    @Test
    void shouldAddAndRemoveMedicationProperly() {
        Patient patient = Patient.builder().fullName("John Doe").build();
        Medication medication = Medication.builder().name("Ritalin").dosage("10mg").build();

        // Add
        patient.addMedication(medication);

        assertThat(patient.getMedications()).containsExactly(medication);
        assertThat(medication.getPatient()).isEqualTo(patient);

        // Remove
        patient.removeMedication(medication);

        assertThat(patient.getMedications()).isEmpty();
        assertThat(medication.getPatient()).isNull();
    }
}
