package com.hfc.nexosCore.dtos;

import com.hfc.nexosCore.models.enums.Guardianship;
import com.hfc.nexosCore.models.enums.MaritalStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PatientRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    private PatientRequestDTO createValidDTO() {
        return new PatientRequestDTO(
                "John Doe",
                LocalDate.now().minusYears(10),
                "81650393043",
                "Sample School",
                "5th Grade",
                "Jane Doe",
                35,
                "High School",
                "Teacher",
                "English",
                3,
                MaritalStatus.MARRIED,
                Guardianship.BOTH
        );
    }

    @Test
    void shouldPassValidationWhenValidDTOIsProvided() {
        PatientRequestDTO validDto = createValidDTO();

        Set<ConstraintViolation<PatientRequestDTO>> violations = validator.validate(validDto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWhenCpfIsBlank() {
        PatientRequestDTO dto = new PatientRequestDTO(
                "John Doe",
                LocalDate.now().minusYears(10),
                "", // Blank CPF
                "Sample School",
                "5th Grade",
                "Jane Doe",
                35,
                "High School",
                "Teacher",
                "English",
                3,
                MaritalStatus.MARRIED,
                Guardianship.BOTH
        );

        Set<ConstraintViolation<PatientRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("cpf") &&
                v.getMessage().contains("CPF is required"));
    }

    @Test
    void shouldFailValidationWhenFullNameIsBlank() {
        PatientRequestDTO dto = new PatientRequestDTO(
                "",
                LocalDate.now().minusYears(10),
                "81650393043",
                "Sample School",
                "5th Grade",
                "Jane Doe",
                35,
                "High School",
                "Teacher",
                "English",
                3,
                MaritalStatus.MARRIED,
                Guardianship.BOTH
        );

        Set<ConstraintViolation<PatientRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("fullName"));
    }

    @Test
    void shouldFailValidationWhenBirthDateIsInTheFuture() {
        PatientRequestDTO dto = new PatientRequestDTO(
                "John Doe",
                LocalDate.now().plusDays(1), // Future date
                "81650393043",
                "Sample School",
                "5th Grade",
                "Jane Doe",
                35,
                "High School",
                "Teacher",
                "English",
                3,
                MaritalStatus.MARRIED,
                Guardianship.BOTH
        );

        Set<ConstraintViolation<PatientRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("birthDate") &&
                v.getMessage().contains("must be in the past"));
    }

    @Test
    void shouldFailValidationWhenMotherAgeIsNegative() {
        PatientRequestDTO dto = new PatientRequestDTO(
                "John Doe",
                LocalDate.now().minusYears(10),
                "81650393043",
                "Sample School",
                "5th Grade",
                "Jane Doe",
                -1, // Negative age
                "High School",
                "Teacher",
                "English",
                3,
                MaritalStatus.MARRIED,
                Guardianship.BOTH
        );

        Set<ConstraintViolation<PatientRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("motherAge"));
    }
}
