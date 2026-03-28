package com.hfc.nexosCore.dtos;

import com.hfc.nexosCore.models.enums.Guardianship;
import com.hfc.nexosCore.models.enums.MaritalStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PatientRequestDTO(
        @NotBlank(message = "Full name cannot be blank")
        @Size(min = 3, max = 150, message = "Full name must be between 3 and 150 characters")
        String fullName,

        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @Size(max = 100, message = "School name must not exceed 100 characters")
        String schoolName,

        @Size(max = 50, message = "Grade must not exceed 50 characters")
        String grade,

        @NotBlank(message = "Mother name cannot be blank")
        @Size(min = 3, max = 150, message = "Mother name must be between 3 and 150 characters")
        String motherName,

        @NotNull(message = "Mother age is required")
        @PositiveOrZero(message = "Mother age must be positive or zero")
        Integer motherAge,

        @Size(max = 100, message = "Mother education must not exceed 100 characters")
        String motherEducation,

        @Size(max = 100, message = "Mother occupation must not exceed 100 characters")
        String motherOccupation,

        @NotBlank(message = "Primary language cannot be blank")
        @Size(max = 50, message = "Primary language must not exceed 50 characters")
        String primaryLanguage,

        @NotNull(message = "Learning age is required")
        @PositiveOrZero(message = "Learning age must be positive or zero")
        Integer learningAge,

        @NotNull(message = "Parents marital status is required")
        MaritalStatus parentsMaritalStatus,

        @NotNull(message = "Guardianship is required")
        Guardianship guardianship
) {}
