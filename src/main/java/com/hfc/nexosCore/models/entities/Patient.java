package com.hfc.nexosCore.models.entities;

import com.hfc.nexosCore.models.enums.Guardianship;
import com.hfc.nexosCore.models.enums.MaritalStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fullName;
    private LocalDate birthDate;
    private String schoolName;
    private String grade;

    // Mother Data
    private String motherName;
    private Integer motherAge;
    private String motherEducation;
    private String motherOccupation;

    // Languages
    private String primaryLanguage;
    private Integer learningAge;

    @Enumerated(EnumType.STRING)
    private MaritalStatus parentsMaritalStatus;

    @Enumerated(EnumType.STRING)
    private Guardianship guardianship;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Medication> medications = new ArrayList<>();

    public void addMedication(Medication medication) {
        medications.add(medication);
        medication.setPatient(this);
    }

    public void removeMedication(Medication medication) {
        medications.remove(medication);
        medication.setPatient(null);
    }
}
