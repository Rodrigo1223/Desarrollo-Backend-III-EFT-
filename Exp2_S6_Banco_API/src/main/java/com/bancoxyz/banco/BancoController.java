package com.bancoxyz.banco;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/banco")
public class BancoController {

    @Value("${example.message:Mensaje por defecto}")
    private String exampleMessage;

    @Autowired
    private BancoResilienciaService resilienciaService;

    @Autowired
    private TransferenciaProducer transferenciaProducer;

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "Banco API running");
    }

    @GetMapping("/config")
    public Map<String, String> config() {
        return Map.of("message", exampleMessage);
    }

    // Endpoint requerido por el BFF
    @GetMapping("/cuenta/{cuentaId}")
    public Map<String, Object> obtenerCuenta(@PathVariable String cuentaId) {
        return Map.of(
                "cuentaId", cuentaId,
                "titular", "Cliente Banco XYZ",
                "saldo", 1500000,
                "estado", "ACTIVA",
                "tipoCuenta", "CORRIENTE"
        );
    }

    @GetMapping("/seguro")
    public Map<String, String> seguro() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = (auth != null) ? auth.getName() : "anonymous";
        return Map.of("mensaje", "Acceso autorizado", "usuario", usuario);
    }

    // Endpoint de prueba de Resiliencia real protegido con CircuitBreaker sobre Kafka
    @GetMapping("/resiliencia")
    public ResponseEntity<String> probarResiliencia(@RequestParam(name = "payload", defaultValue = "Transf_Prueba_CircuitBreaker") String payload) {
        resilienciaService.enviarTransferenciaReal(payload);
        return ResponseEntity.ok("Operación de resiliencia ejecutada. Verifique si el CircuitBreaker envió a Kafka o activó el Fallback.");
    }

    @PostMapping("/transferencias")
    public Map<String, Object> registrarTransferencia(@RequestBody TransferenciaRequest request) {
        TransferenciaEvent evento = new TransferenciaEvent();
        evento.setEventId(UUID.randomUUID().toString());
        evento.setTipoEvento("TRANSFERENCIA_REALIZADA");
        evento.setCuentaOrigen(request.getCuentaOrigen());
        evento.setCuentaDestino(request.getCuentaDestino());
        evento.setMonto(request.getMonto());
        evento.setMoneda(request.getMoneda());
        evento.setDescripcion(request.getDescripcion());
        evento.setTimestamp(Instant.now().toString());

        transferenciaProducer.sendTransferencia(evento);

        return Map.of(
                "status", "OK",
                "mensaje", "Transferencia publicada en Kafka",
                "eventId", evento.getEventId(),
                "tipoEvento", evento.getTipoEvento(),
                "cuentaOrigen", evento.getCuentaOrigen(),
                "cuentaDestino", evento.getCuentaDestino(),
                "monto", evento.getMonto(),
                "moneda", evento.getMoneda()
        );
    }
}