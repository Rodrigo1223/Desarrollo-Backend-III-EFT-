package com.duoc.migracion.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class DateParserUtil {

    private static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd")
    );

    public static LocalDate parsearFecha(String fechaStr) {
        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            return null;
        }
        String fechaLimpia = fechaStr.trim();
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(fechaLimpia, formatter);
            } catch (DateTimeParseException e) {
                // Sigue intentando con el siguiente formato
            }
        }
        System.err.println("[ADVERTENCIA] Formato de fecha no reconocido, se asigna fecha por defecto: " + fechaStr);
        return LocalDate.now();
    }
}