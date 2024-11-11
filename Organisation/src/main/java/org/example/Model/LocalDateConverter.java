package org.example.Model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, String> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String convertToDatabaseColumn(LocalDate attribute) {
        return (attribute == null) ? null : attribute.format(formatter);
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : LocalDate.parse(dbData, formatter);
    }
}
