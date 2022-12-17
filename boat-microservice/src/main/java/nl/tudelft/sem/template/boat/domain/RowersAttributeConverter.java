package nl.tudelft.sem.template.boat.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RowersAttributeConverter implements AttributeConverter<Rowers, String> {

    @Override
    public String convertToDatabaseColumn(Rowers attribute) {
        ObjectMapper obj = new ObjectMapper();
        try {
            return obj.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Rowers convertToEntityAttribute(String dbData) {
        ObjectMapper obj = new ObjectMapper();
        try {
            return obj.readValue(dbData, Rowers.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}