package com.duoc.bff.dto;

import java.util.List;

public class WebResponseDto {
    private Long clienteId;
    private String nombreCompleto;
    private String canalOrigen;
    private List<String> cuentasAsociadas;
    private List<String> transaccionesRecientes;

    // Getters y Setters
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCanalOrigen() { return canalOrigen; }
    public void setCanalOrigen(String canalOrigen) { this.canalOrigen = canalOrigen; }

    public List<String> getCuentasAsociadas() { return cuentasAsociadas; }
    public void setCuentasAsociadas(List<String> cuentasAsociadas) { this.cuentasAsociadas = cuentasAsociadas; }

    public List<String> getTransaccionesRecientes() { return transaccionesRecientes; }
    public void setTransaccionesRecientes(List<String> transaccionesRecientes) { this.transaccionesRecientes = transaccionesRecientes; }
}