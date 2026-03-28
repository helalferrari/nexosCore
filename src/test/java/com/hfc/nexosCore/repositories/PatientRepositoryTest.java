package com.hfc.nexosCore.repositories;

import com.hfc.nexosCore.models.entities.Patient;
import com.hfc.nexosCore.models.enums.Guardianship;
import com.hfc.nexosCore.models.enums.MaritalStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void shouldSaveAndFindPatientById() {
        Patient patientToSave = Patient.builder()
                .fullName("John Doe")
                .birthDate(LocalDate.now().minusYears(10))
                .motherName("Jane Doe")
                .motherAge(35)
                .primaryLanguage("English")
                .learningAge(3)
                .parentsMaritalStatus(MaritalStatus.MARRIED)
                .guardianship(Guardianship.BOTH)
                .build();

        Patient savedPatient = patientRepository.save(patientToSave);

        assertThat(savedPatient.getId()).isNotNull();

        Optional<Patient> retrievedPatient = patientRepository.findById(savedPatient.getId());

        assertThat(retrievedPatient).isPresent();
        assertThat(retrievedPatient.get().getFullName()).isEqualTo("John Doe");
    }

    @Test
    void shouldFindPatientByFullNameContainingIgnoreCase() {
        Patient patient1 = Patient.builder()
                .fullName("John Albert Doe")
                .build();
        Patient patient2 = Patient.builder()
                .fullName("Jane Doe")
                .build();
        Patient patient3 = Patient.builder()
                .fullName("Albert Smith")
                .build();

        patientRepository.saveAll(List.of(patient1, patient2, patient3));

        List<Patient> foundPatients = patientRepository.findByFullNameContainingIgnoreCase("albert");

        assertThat(foundPatients).hasSize(2);
        assertThat(foundPatients).extracting(Patient::getFullName)
                .containsExactlyInAnyOrder("John Albert Doe", "Albert Smith");
    }
}
