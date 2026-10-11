package com.duoc.migracion.dto;

public class EstadoFinancieroAnualCsvDto {

    private String cuentaId;
    private String fecha;
    private Double monto;
    private String tipo; // Cambiado a String para evitar problemas de tipos no encontrados
    private String descripcion;

    // Constructores por defecto y con parámetros
    public EstadoFinancieroAnualCsvDto() {
    }

    public EstadoFinancieroAnualCsvDto(String cuentaId, String fecha, Double monto, String tipo, String descripcion) {
        this.cuentaId = cuentaId;
        this.fecha = fecha;
        this.monto = monto;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(String cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}