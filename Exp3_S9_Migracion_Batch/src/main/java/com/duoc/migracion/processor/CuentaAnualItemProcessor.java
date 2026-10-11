package com.duoc.migracion.processor;

import com.duoc.migracion.dto.CuentaAnualCsvDto;
import com.duoc.migracion.model.CuentaAnualEntity;
import com.duoc.migracion.util.DateParserUtil; // <--- Usamos el utilitario central
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CuentaAnualItemProcessor implements ItemProcessor<CuentaAnualCsvDto, CuentaAnualEntity> {

    @Override
    public CuentaAnualEntity process(CuentaAnualCsvDto item) throws Exception {
        CuentaAnualEntity entity = new CuentaAnualEntity();

        entity.setCuentaId(item.getCuentaId());
        entity.setMonto(item.getMonto());
        entity.setTransaccion(item.getTransaccion());
        entity.setDescripcion(item.getDescripcion());

        // Parseo flexible unificado
        LocalDate fechaParseada = DateParserUtil.parsearFecha(item.getFecha());
        entity.setFecha(fechaParseada != null ? fechaParseada : LocalDate.now());

        return entity;
    }
}