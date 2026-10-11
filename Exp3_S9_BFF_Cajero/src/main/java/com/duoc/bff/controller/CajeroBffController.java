package com.duoc.bff.controller;

import com.duoc.bff.dto.RetiroRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cajero")
public class CajeroBffController {

    @GetMapping("/saldo/{cuentaId}")
    public ResponseEntity<String> consultarSaldoCajero(@PathVariable Long cuentaId) {
        return ResponseEntity.ok("Saldo disponible en cajero físico para la cuenta: " + cuentaId);
    }

    // Operación crítica y exclusiva del canal Cajero Automático
    @PostMapping("/retiro")
    public ResponseEntity<String> procesarRetiroCajero(@RequestBody RetiroRequestDto request) {
        return ResponseEntity.ok("Transacción aprobada. Retiro exitoso de $" + request.getMontoRetiro() + " en la cuenta " + request.getCuentaId());
    }
}