package com.hfc.nexoscore.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Componente central para tratamento global de exceções da API NexosCore.
 * Utiliza o padrão RFC 7807 (Problem Details for HTTP APIs).
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String TIMESTAMP_KEY = "timestamp";

    /**
     * Tratamento para recursos não encontrados (404 Not Found).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.warn("Recurso não encontrado: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Recurso não encontrado");
        problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    /**
     * Tratamento para falhas em regras de negócio (400 Bad Request).
     */
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ProblemDetail> handleBusinessRuleException(BusinessRuleException ex, WebRequest request) {
        log.warn("Falha em regra de negócio: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Violação de regra de negócio");
        problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Tratamento para falhas de validação de DTOs (400 Bad Request).
     * Formata os erros das anotações como @NotBlank, @NotNull, etc.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Erro de validação nos dados de entrada: {}", ex.getObjectName());
        
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Campo inválido",
                        (existing, replacement) -> existing
                ));

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Um ou mais campos estão inválidos.");
        problemDetail.setTitle("Erro de validação");
        problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
        problemDetail.setProperty("invalidFields", errors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Tratamento genérico para exceções não mapeadas (500 Internal Server Error).
     * Garante que o stack trace seja logado, mas o cliente receba uma mensagem genérica por segurança.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneralException(Exception ex, WebRequest request) {
        log.error("Ocorreu um erro interno inesperado: ", ex);
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Ocorreu um erro interno inesperado no servidor. Por favor, tente novamente mais tarde."
        );
        problemDetail.setTitle("Erro interno no servidor");
        problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }
}
