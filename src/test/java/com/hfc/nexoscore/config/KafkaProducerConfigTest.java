package com.hfc.nexoscore.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testes de Unidade - KafkaProducerConfig")
class KafkaProducerConfigTest {

    private final KafkaProducerConfig config = new KafkaProducerConfig();

    @Test
    @DisplayName("Deve criar o tópico patient-created-topic com as configurações corretas")
    void testPatientCreatedTopic() {
        NewTopic topic = config.patientCreatedTopic();
        assertThat(topic.name()).isEqualTo("patient-created-topic");
        assertThat(topic.numPartitions()).isEqualTo(3);
        assertThat(topic.replicationFactor()).isEqualTo((short) 1);
    }
}
