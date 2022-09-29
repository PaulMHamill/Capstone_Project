package models;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Converts a String in format {@code @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)} back to a {@code LocalDateTime}.
 */
@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String value) {
        return LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
    }
}