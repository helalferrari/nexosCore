package com.hfc.nexosCore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada para falhas em regras de negócio específicas da aplicação.
 * Exemplo: Tentar cadastrar um paciente com um CPF que já existe na base.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
