package com.duoc.migracion.processor;

import com.duoc.migracion.dto.EstadoFinancieroAnualCsvDto;
import com.duoc.migracion.model.CuentaAnualEntity;
import com.duoc.migracion.util.DateParserUtil; // <--- Usamos el utilitario central
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EstadoFinancieroAnualItemProcessor implements ItemProcessor<EstadoFinancieroAnualCsvDto, CuentaAnualEntity> {

    @Override
    public CuentaAnualEntity process(EstadoFinancieroAnualCsvDto item) throws Exception {
        CuentaAnualEntity entity = new CuentaAnualEntity();

        // Mapeo correspondiente y uso del parser de fechas robusto
        entity.setMonto(item.getMonto());
        entity.setDescripcion(item.getDescripcion());

        LocalDate fechaParseada = DateParserUtil.parsearFecha(item.getFecha());
        entity.setFecha(fechaParseada != null ? fechaParseada : LocalDate.now());

        return entity;
    }
}