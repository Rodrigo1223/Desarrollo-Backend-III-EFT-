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

    public Object procesarRetiro(String cuentaId, double monto) {
        // Realiza la petición POST al backend para procesar el retiro de forma dinámica
        String url = BACKEND_URL + "/retiro?cuentaId=" + cuentaId + "&monto=" + monto;
        return restTemplate.postForObject(url, null, Object.class);
    }
}