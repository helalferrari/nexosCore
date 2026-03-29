package com.hfc.nexoscore.dtos;

import jakarta.validation.constraints.NotBlank;

public record PatientRequest(
    @NotBlank(message = "O nome completo é obrigatório")
    String fullName,
    
    @NotBlank(message = "O CPF é obrigatório")
    String cpf
) {
}
