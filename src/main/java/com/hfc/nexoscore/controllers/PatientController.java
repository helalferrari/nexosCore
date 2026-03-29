package com.hfc.nexoscore.controllers;

import com.hfc.nexoscore.dtos.PatientRequest;
import com.hfc.nexoscore.services.PatientService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createPatient(@Valid @RequestBody PatientRequest request) {
        return patientService.createPatient(request.fullName(), request.cpf());
    }
}
