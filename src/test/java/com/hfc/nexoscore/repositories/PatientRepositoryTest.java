package com.hfc.nexoscore.repositories;

import com.hfc.nexoscore.models.entities.Patient;
import com.hfc.nexoscore.models.enums.Guardianship;
import com.hfc.nexoscore.models.enums.MaritalStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void shouldSaveAndFindPatientById() {
        Patient patientToSave = Patient.builder()
                .fullName("John Doe")
                .cpf("81650393043")
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
    void shouldFindPatientByCpf() {
        String cpf = "81650393043";
        Patient patient = Patient.builder()
                .fullName("John Doe")
                .cpf(cpf)
                .birthDate(LocalDate.now().minusYears(10))
                .motherName("Jane Doe")
                .motherAge(35)
                .primaryLanguage("English")
                .learningAge(3)
                .parentsMaritalStatus(MaritalStatus.MARRIED)
                .guardianship(Guardianship.BOTH)
                .build();
        patientRepository.save(patient);

        Optional<Patient> foundPatient = patientRepository.findByCpf(cpf);

        assertThat(foundPatient).isPresent();
        assertThat(foundPatient.get().getCpf()).isEqualTo(cpf);
    }

    @Test
    void shouldThrowExceptionWhenSavingDuplicateCpf() {
        String cpf = "81650393043";
        Patient patient1 = Patient.builder()
                .fullName("John Doe")
                .cpf(cpf)
                .birthDate(LocalDate.now().minusYears(10))
                .motherName("Jane Doe")
                .motherAge(35)
                .primaryLanguage("English")
                .learningAge(3)
                .parentsMaritalStatus(MaritalStatus.MARRIED)
                .guardianship(Guardianship.BOTH)
                .build();
        patientRepository.save(patient1);

        Patient patient2 = Patient.builder()
                .fullName("Another John")
                .cpf(cpf)
                .birthDate(LocalDate.now().minusYears(12))
                .motherName("Mary Doe")
                .motherAge(40)
                .primaryLanguage("English")
                .learningAge(4)
                .parentsMaritalStatus(MaritalStatus.DIVORCED)
                .guardianship(Guardianship.MOTHER)
                .build();

        assertThatThrownBy(() -> patientRepository.saveAndFlush(patient2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldFindPatientByFullNameContainingIgnoreCase() {
        Patient patient1 = Patient.builder()
                .fullName("John Albert Doe")
                .cpf("81650393043")
                .birthDate(LocalDate.now().minusYears(10))
                .motherName("Jane Doe")
                .motherAge(35)
                .primaryLanguage("English")
                .learningAge(3)
                .parentsMaritalStatus(MaritalStatus.MARRIED)
                .guardianship(Guardianship.BOTH)
                .build();
        Patient patient2 = Patient.builder()
                .fullName("Jane Doe")
                .cpf("05445731054")
                .birthDate(LocalDate.now().minusYears(10))
                .motherName("Mary Doe")
                .motherAge(35)
                .primaryLanguage("English")
                .learningAge(3)
                .parentsMaritalStatus(MaritalStatus.MARRIED)
                .guardianship(Guardianship.BOTH)
                .build();
        Patient patient3 = Patient.builder()
                .fullName("Albert Smith")
                .cpf("76891230000")
                .birthDate(LocalDate.now().minusYears(10))
                .motherName("Lucy Doe")
                .motherAge(35)
                .primaryLanguage("English")
                .learningAge(3)
                .parentsMaritalStatus(MaritalStatus.MARRIED)
                .guardianship(Guardianship.BOTH)
                .build();

        patientRepository.saveAll(List.of(patient1, patient2, patient3));

        List<Patient> foundPatients = patientRepository.findByFullNameContainingIgnoreCase("albert");

        assertThat(foundPatients).hasSize(2);
        assertThat(foundPatients).extracting(Patient::getFullName)
                .containsExactlyInAnyOrder("John Albert Doe", "Albert Smith");
    }
}
