# Diretrizes do Módulo Backend (NexosCore)

Este arquivo define as regras e convenções específicas para o desenvolvimento do backend Nexos. Ele estende e aplica as diretrizes definidas no arquivo `GEMINI.md` global.

## Stack Tecnológico Principal
*   **Linguagem:** Java (Verificar a versão do projeto no build.gradle)
*   **Framework:** Spring Boot
*   **Build Tool:** Gradle

## Padrões Arquiteturais e de Design
*   **Camadas (Exemplo de Padrão - A Definir pela Equipe):**
    *   `controllers`: Lida com requisições HTTP, validação de entrada e retorno de DTOs.
    *   `services`: Contém a regra de negócio central.
    *   `repositories`: Acesso a dados (Spring Data JPA ou afins).
    *   `models/entities`: Entidades de banco de dados.
    *   `dtos`: Objetos de Transferência de Dados para entrada e saída de APIs.
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
