package com.hfc.nexosCore.repositories;

import com.hfc.nexosCore.models.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    // Custom query to find patients by full name containing (case-insensitive)
    java.util.List<Patient> findByFullNameContainingIgnoreCase(String fullName);
}
