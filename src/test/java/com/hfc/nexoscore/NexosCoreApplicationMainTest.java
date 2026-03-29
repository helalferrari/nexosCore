package com.hfc.nexoscore;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Testes de Unidade - Application")
class NexosCoreApplicationMainTest {

    @Test
    @DisplayName("Deve carregar o contexto da aplicação via main")
    void main() {
        // Este teste apenas garante cobertura da classe principal
        // Passamos argumentos vazios e usamos o perfil de teste se necessário
        assertThatCode(() -> NexosCoreApplication.main(new String[]{"--server.port=0"}))
                .doesNotThrowAnyException();
    }
}
