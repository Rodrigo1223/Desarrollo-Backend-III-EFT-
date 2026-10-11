package com.duoc.bff.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BackendClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String obtenerDatosBackend(Long clienteId) {
        // Endpoint real del microservicio Backend de cuentas o clientes
        String url = "http://banco-xyz/api/clientes/" + clienteId;
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            return "Datos simulados del Backend para cliente: " + clienteId;
        }
    }
}