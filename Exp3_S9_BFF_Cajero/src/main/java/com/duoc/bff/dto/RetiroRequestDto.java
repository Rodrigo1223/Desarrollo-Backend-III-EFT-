package com.duoc.bff.dto;

public class RetiroRequestDto {
    private Long cuentaId;
    private Double montoRetiro;

    public Long getCuentaId() { return cuentaId; }
    public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }
    public Double getMontoRetiro() { return montoRetiro; }
    public void setMontoRetiro(Double montoRetiro) { this.montoRetiro = montoRetiro; }
}