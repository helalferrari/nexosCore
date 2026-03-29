package com.hfc.nexosCore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hfc.nexosCore.dtos.PatientRequest;
import com.hfc.nexosCore.services.PatientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
@DisplayName("Testes de Unidade - PatientController")
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    @Test
    @DisplayName("Deve retornar 201 Created ao criar um paciente com sucesso")
    void shouldReturnCreated_WhenPatientCreatedSuccessfully() throws Exception {
        // Arrange
        UUID generatedId = UUID.randomUUID();
        PatientRequest request = createPatientRequestFixture("John Doe", "12345678900");
        when(patientService.createPatient(anyString(), anyString())).thenReturn(generatedId);

        // Act & Assert
        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(generatedId.toString()));
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request quando os campos do DTO estiverem inválidos")
    void shouldReturnBadRequest_WhenDtoIsInvalid() throws Exception {
        // Arrange
        PatientRequest request = createPatientRequestFixture("", ""); // Nome e CPF vazios

        // Act & Assert
        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Erro de validação"))
                .andExpect(jsonPath("$.invalidFields.fullName").exists())
                .andExpect(jsonPath("$.invalidFields.cpf").exists());
    }

    // --- Fixtures ---

    private PatientRequest createPatientRequestFixture(String fullName, String cpf) {
        return new PatientRequest(fullName, cpf);
    }
}
