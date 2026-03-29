package com.hfc.nexosCore.controllers;

import com.hfc.nexosCore.dtos.PatientRequest;
import com.hfc.nexosCore.services.PatientService;
import lombok.RequiredArgsConstructor;
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
    public UUID createPatient(@RequestBody PatientRequest request) {
        return patientService.createPatient(request.fullName(), request.cpf());
    }
}
