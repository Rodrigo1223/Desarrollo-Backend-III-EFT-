package com.duoc.bff.controller;

import com.duoc.bff.dto.MovilResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movil")
public class MovilBffController {

    @GetMapping("/resumen/{clienteId}")
    public ResponseEntity<MovilResponseDto> getResumenMovil(@PathVariable Long clienteId) {
        MovilResponseDto response = new MovilResponseDto();
        response.setMensajeBienvenida("Hola de nuevo, Rodrigo");
        response.setSaldoConsolidado(15420000.0);
        // Respuesta ligera y optimizada para la app móvil
        response.setUltimosMovimientos(List.of("Compra Supermercado: -$45.000", "Abono Nómina: +$1.200.000"));

        return ResponseEntity.ok(response);
    }
}