package com.hfc.nexosCore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hfc.nexosCore.dtos.PatientRequest;
import com.hfc.nexosCore.services.PatientService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnCreatedStatusAndPatientId() throws Exception {
        UUID expectedId = UUID.randomUUID();
        when(patientService.createPatient(anyString(), anyString())).thenReturn(expectedId);

        PatientRequest request = new PatientRequest("Maria Sousa", "09876543211");

        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("\"" + expectedId.toString() + "\""));
    }
}
