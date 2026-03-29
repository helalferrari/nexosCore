# Diretrizes do Módulo Backend (NexosCore)

Este arquivo define as regras e convenções específicas para o desenvolvimento do backend Nexos. Ele estende e aplica as diretrizes definidas no arquivo `GEMINI.md` global.

## Stack Tecnológico Principal
*   **Linguagem:** Java 21
*   **Framework:** Spring Boot 3.2.4
*   **Build Tool:** Gradle
*   **Banco de Dados:** PostgreSQL (Docker porta 5434)

## Padrões de Mensageria (Kafka)
*   **Papel:** Produtor de eventos.
*   **Tópicos:** `patient-created-topic`.
*   **Serialização:** `JsonSerializer`.
*   **Chave de Partição:** `patientId` (UUID string) para garantir ordem por paciente.

## Padrões Arquiteturais
*   **Persistência:** Entidades devem ser persistidas via `JpaRepository` antes do disparo de eventos Kafka (Padrão Transactional).
*   **Camadas:** Controller -> Service (@Transactional) -> Repository.
*   **Injeção de Dependências:** Utilize injeção via construtor (preferencialmente usando Lombok `@RequiredArgsConstructor` se o projeto utilizar Lombok) em vez de `@Autowired` em campos.
*   **Nomenclatura:** Padrão Java (CamelCase para variáveis/métodos, PascalCase para Classes). Interfaces costumam não ter o prefixo 'I' ou sufixo 'Interface'. Implementações usam o sufixo 'Impl' se necessário.

## Comandos Comuns (Gradle)
O agente deve utilizar estes comandos ao realizar validações ou builds:
*   **Build e Testes:** `./gradlew build`
*   **Apenas Testes:** `./gradlew test`
*   **Limpar e Compilar:** `./gradlew clean build`
*(Nota: Sempre execute os comandos do Gradle a partir do diretório raiz do NexosCore ou use caminhos relativos para o `./gradlew`)*.

## Diretrizes de Testes (Resumo)
*   **Localização:** A mesma estrutura de pacotes de `src/main/java` deve ser refletida em `src/test/java`.
*   **Nomenclatura:** Classes de teste devem terminar com `Tests` ou `Test` (ex: `UserServiceTests.java`).
*   **Frameworks Esperados:** JUnit 5, Mockito.
*   *Para diretrizes detalhadas de como mockar, dados e fluxos específicos, consulte o arquivo de skill de testes correspondente (ex: `.gemini/skills/backend-testing.md`).*
