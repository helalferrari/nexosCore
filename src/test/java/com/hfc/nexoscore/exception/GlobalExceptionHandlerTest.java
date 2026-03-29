package com.hfc.nexoscore.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Test
    @DisplayName("Deve retornar 404 Not Found quando ResourceNotFoundException for lançada")
    void shouldReturnNotFound_WhenResourceNotFoundExceptionThrown() {
        String message = "Recurso não encontrado";
        ResourceNotFoundException ex = new ResourceNotFoundException(message);

        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handleResourceNotFoundException(ex, webRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Recurso não encontrado");
        assertThat(response.getBody().getDetail()).isEqualTo(message);
        assertThat(response.getBody().getProperties()).containsKey("timestamp");
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request quando BusinessRuleException for lançada")
    void shouldReturnBadRequest_WhenBusinessRuleExceptionThrown() {
        String message = "Violação de regra de negócio";
        BusinessRuleException ex = new BusinessRuleException(message);

        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handleBusinessRuleException(ex, webRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Violação de regra de negócio");
        assertThat(response.getBody().getDetail()).isEqualTo(message);
        assertThat(response.getBody().getProperties()).containsKey("timestamp");
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request com campos inválidos quando MethodArgumentNotValidException for lançada")
    void shouldReturnBadRequest_WhenMethodArgumentNotValidExceptionThrown() {
        // Mocking MethodArgumentNotValidException is complex, we use mockito for the dependencies
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("dto", "cpf", "CPF inválido");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
                mock(MethodParameter.class), bindingResult);

        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handleMethodArgumentNotValidException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Erro de validação");
        
        @SuppressWarnings("unchecked")
        Map<String, String> invalidFields = (Map<String, String>) response.getBody().getProperties().get("invalidFields");
        assertThat(invalidFields).containsEntry("cpf", "CPF inválido");
    }

    @Test
    @DisplayName("Deve retornar 500 Internal Server Error quando uma exceção genérica for lançada")
    void shouldReturnInternalServerError_WhenGeneralExceptionThrown() {
        Exception ex = new Exception("Erro inesperado");

        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handleGeneralException(ex, webRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Erro interno no servidor");
        assertThat(response.getBody().getDetail()).contains("erro interno inesperado");
    }
}
