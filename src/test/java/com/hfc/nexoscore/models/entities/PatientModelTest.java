package com.hfc.nexoscore.models.entities;

import com.hfc.nexoscore.models.enums.Guardianship;
import com.hfc.nexoscore.models.enums.MaritalStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testes de Unidade - Model Patient")
class PatientModelTest {

    @Test
    @DisplayName("Deve adicionar e remover medicação corretamente")
    void shouldAddAndRemoveMedication() {
        // Arrange
        Patient patient = Patient.builder().fullName("John Doe").build();
        Medication medication = Medication.builder().name("Med1").build();

        // Act - Add
        patient.addMedication(medication);

        // Assert - Add
        assertThat(patient.getMedications()).contains(medication);
        assertThat(medication.getPatient()).isEqualTo(patient);

        // Act - Remove
        patient.removeMedication(medication);

        // Assert - Remove
        assertThat(patient.getMedications()).doesNotContain(medication);
        assertThat(medication.getPatient()).isNull();
    }

    @Test
    @DisplayName("Deve testar getters e setters básicos")
    void shouldTestGettersAndSetters() {
        UUID id = UUID.randomUUID();
        LocalDate birthDate = LocalDate.now().minusYears(10);
        Patient patient = new Patient();
        
        patient.setId(id);
        patient.setFullName("Name");
        patient.setBirthDate(birthDate);
        patient.setCpf("123");
        patient.setSchoolName("School");
        patient.setGrade("1st");
        patient.setMotherName("Mother");
        patient.setMotherAge(30);
        patient.setMotherEducation("Higher");
        patient.setMotherOccupation("Job");
        patient.setPrimaryLanguage("PT");
        patient.setLearningAge(1);
        patient.setParentsMaritalStatus(MaritalStatus.MARRIED);
        patient.setGuardianship(Guardianship.BOTH);

        assertThat(patient.getId()).isEqualTo(id);
        assertThat(patient.getFullName()).isEqualTo("Name");
        assertThat(patient.getBirthDate()).isEqualTo(birthDate);
        assertThat(patient.getCpf()).isEqualTo("123");
        assertThat(patient.getSchoolName()).isEqualTo("School");
        assertThat(patient.getGrade()).isEqualTo("1st");
        assertThat(patient.getMotherName()).isEqualTo("Mother");
        assertThat(patient.getMotherAge()).isEqualTo(30);
        assertThat(patient.getMotherEducation()).isEqualTo("Higher");
        assertThat(patient.getMotherOccupation()).isEqualTo("Job");
        assertThat(patient.getPrimaryLanguage()).isEqualTo("PT");
        assertThat(patient.getLearningAge()).isEqualTo(1);
        assertThat(patient.getParentsMaritalStatus()).isEqualTo(MaritalStatus.MARRIED);
        assertThat(patient.getGuardianship()).isEqualTo(Guardianship.BOTH);
    }

    @Test
    @DisplayName("Deve testar equals, hashCode e toString do Lombok")
    void testLombokMethods() {
        Patient p1 = Patient.builder().id(UUID.randomUUID()).cpf("123").build();
        Patient p2 = Patient.builder().id(p1.getId()).cpf("123").build();

        assertThat(p1)
                .isEqualTo(p2)
                .hasSameHashCodeAs(p2)
                .extracting(Object::toString).asString().contains("123");

        Medication m1 = Medication.builder().name("Med").build();
        Medication m2 = Medication.builder().name("Med").build();

        assertThat(m1)
                .isEqualTo(m2)
                .hasSameHashCodeAs(m2)
                .extracting(Object::toString).asString().contains("Med");
        
        m1.setReason("Reason");
        m1.setDosage("10mg");
        m1.setStartDate(LocalDate.now());
        assertThat(m1.getReason()).isEqualTo("Reason");
        assertThat(m1.getDosage()).isEqualTo("10mg");
        assertThat(m1.getStartDate()).isNotNull();
    }
}
