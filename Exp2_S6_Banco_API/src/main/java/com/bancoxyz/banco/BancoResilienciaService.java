package com.bancoxyz.banco;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BancoResilienciaService {

    private static final Logger log = LoggerFactory.getLogger(BancoResilienciaService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public BancoResilienciaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Protege la operación real de envío a Kafka
    @CircuitBreaker(name = "kafkaService", fallbackMethod = "fallbackKafka")
    public void enviarTransferenciaReal(String payload) {
        log.info("Intentando enviar evento de transferencia a Kafka...");
        kafkaTemplate.send("banco-xyz.transferencias", payload);
        log.info("Evento enviado a Kafka con éxito.");
    }

    // Fallback real que se ejecuta si Kafka cae (estado OPEN)
    public void fallbackKafka(String payload, Throwable t) {
        log.error("¡FALLBACK ACTIVADO! Kafka no disponible por: {}. Guardando transacción en cola de respaldo local.", t.getMessage());
    }
}