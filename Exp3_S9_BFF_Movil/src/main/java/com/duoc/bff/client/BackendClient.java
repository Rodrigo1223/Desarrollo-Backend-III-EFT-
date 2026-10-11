package com.duoc.bff.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BackendClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BACKEND_URL = "http://localhost:8081/api/banco";

    public Object obtenerDatosCuenta(String cuentaId) {
        return restTemplate.getForObject(BACKEND_URL + "/cuenta/" + cuentaId, Object.class);
    }
}