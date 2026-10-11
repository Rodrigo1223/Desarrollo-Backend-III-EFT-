package com.duoc.bff.controller;

import com.duoc.bff.client.BackendClient;
import com.duoc.bff.dto.WebResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web")
public class WebBffController {

    private final BackendClient backendClient;

    public WebBffController(BackendClient backendClient) {
        this.backendClient = backendClient;
    }

    @GetMapping("/dashboard/{clienteId}")
    public ResponseEntity<WebResponseDto> getDashboardWeb(@PathVariable Long clienteId) {
        // Obtiene datos dinámicos del backend
        String datosBackend = backendClient.obtenerDatosBackend(clienteId);

        WebResponseDto response = new WebResponseDto();
        response.setClienteId(clienteId);
        response.setNombreCompleto("Rodrigo Olavarría (Portal Web)");
        response.setCanalOrigen("WEB-BROWSER");
        response.setCuentasAsociadas(List.of("Cuenta Corriente: 123456-8", "Línea de Crédito: 987654-1"));
        response.setTransaccionesRecientes(List.of("Transferencia recibida: $500.000", "Pago de Tarjeta: $120.000"));

        return ResponseEntity.ok(response);
    }
}