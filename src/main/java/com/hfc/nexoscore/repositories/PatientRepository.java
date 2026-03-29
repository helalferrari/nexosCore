package com.hfc.nexoscore.repositories;

import com.hfc.nexoscore.models.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    // Custom query to find patients by full name containing (case-insensitive)
    java.util.List<Patient> findByFullNameContainingIgnoreCase(String fullName);

    Optional<Patient> findByCpf(String cpf);
}
