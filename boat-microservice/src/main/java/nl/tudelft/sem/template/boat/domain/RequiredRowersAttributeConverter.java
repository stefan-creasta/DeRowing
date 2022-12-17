package nl.tudelft.sem.template.boat.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RequiredRowersAttributeConverter implements AttributeConverter<RequiredRowers, String> {
    @Override
    public String convertToDatabaseColumn(RequiredRowers attribute) {
        ObjectMapper obj = new ObjectMapper();
        try {
            return obj.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RequiredRowers convertToEntityAttribute(String dbData) {
        ObjectMapper obj = new ObjectMapper();
        try {
            return obj.readValue(dbData, RequiredRowers.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}