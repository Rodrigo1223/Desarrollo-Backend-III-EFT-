package com.duoc.migracion.processor;

import com.duoc.migracion.dto.TransaccionDiariaCsvDto;
import com.duoc.migracion.model.TransaccionDiariaEntity;
import com.duoc.migracion.util.DateParserUtil; // <--- Importas tu utilitario
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TransaccionDiariaItemProcessor implements ItemProcessor<TransaccionDiariaCsvDto, TransaccionDiariaEntity> {

    @Override
    public TransaccionDiariaEntity process(TransaccionDiariaCsvDto item) throws Exception {
        TransaccionDiariaEntity entity = new TransaccionDiariaEntity();

        entity.setMonto(item.getMonto());
        entity.setTipoTransaccion(item.getTipo());

        // Aplicas el parser robusto para soportar múltiples formatos de fecha sin que se caiga el job
        if (item.getFecha() != null) {
            entity.setFecha(DateParserUtil.parsearFecha(item.getFecha()));
        }

        return entity;
    }
}