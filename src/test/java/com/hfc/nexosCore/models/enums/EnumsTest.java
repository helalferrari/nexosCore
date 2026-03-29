package com.hfc.nexosCore.models.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testes de Unidade - Enums")
class EnumsTest {

    @Test
    @DisplayName("Deve testar valores do enum MaritalStatus")
    void testMaritalStatusEnum() {
        assertThat(MaritalStatus.values()).hasSize(5);
        assertThat(MaritalStatus.valueOf("MARRIED")).isEqualTo(MaritalStatus.MARRIED);
        assertThat(MaritalStatus.valueOf("SEPARATED")).isEqualTo(MaritalStatus.SEPARATED);
        assertThat(MaritalStatus.valueOf("DIVORCED")).isEqualTo(MaritalStatus.DIVORCED);
        assertThat(MaritalStatus.valueOf("WIDOWED")).isEqualTo(MaritalStatus.WIDOWED);
        assertThat(MaritalStatus.valueOf("OTHER")).isEqualTo(MaritalStatus.OTHER);
    }

    @Test
    @DisplayName("Deve testar valores do enum Guardianship")
    void testGuardianshipEnum() {
        assertThat(Guardianship.values()).hasSize(4);
        assertThat(Guardianship.valueOf("MOTHER")).isEqualTo(Guardianship.MOTHER);
        assertThat(Guardianship.valueOf("FATHER")).isEqualTo(Guardianship.FATHER);
        assertThat(Guardianship.valueOf("BOTH")).isEqualTo(Guardianship.BOTH);
        assertThat(Guardianship.valueOf("OTHER")).isEqualTo(Guardianship.OTHER);
    }
}
