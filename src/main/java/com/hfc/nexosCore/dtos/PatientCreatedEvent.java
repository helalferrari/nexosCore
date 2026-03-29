package com.hfc.nexosCore.dtos;

import java.util.UUID;

public record PatientCreatedEvent(UUID patientId, String name, String cpf) {
}
