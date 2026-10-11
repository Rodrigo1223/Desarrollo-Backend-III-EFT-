package com.duoc.bff.dto;

import java.util.List;

public class MovilResponseDto {
    private String mensajeBienvenida;
    private Double saldoConsolidado;
    private List<String> ultimosMovimientos;

    // Getters y Setters
    public String getMensajeBienvenida() { return mensajeBienvenida; }
    public void setMensajeBienvenida(String mensajeBienvenida) { this.mensajeBienvenida = mensajeBienvenida; }
    public Double getSaldoConsolidado() { return saldoConsolidado; }
    public void setSaldoConsolidado(Double saldoConsolidado) { this.saldoConsolidado = saldoConsolidado; }
    public List<String> getUltimosMovimientos() { return ultimosMovimientos; }
    public void setUltimosMovimientos(List<String> ultimosMovimientos) { this.ultimosMovimientos = ultimosMovimientos; }
}