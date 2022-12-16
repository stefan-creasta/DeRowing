package nl.tudelft.sem.template.boat.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;
import java.util.Map;

@Converter
public class RowersAttributeConverter implements AttributeConverter<Rowers, String> {

    @Override
    public String convertToDatabaseColumn(Rowers attribute) {
        StringBuilder sb = new StringBuilder();
        ObjectMapper obj = new ObjectMapper();
        try {
            return obj.writeValueAsString(attribute);
            //obj.readValue(str, Map.class);
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