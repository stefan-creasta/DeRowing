package nl.tudelft.sem.template.activity.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Converter;

@Converter
public class NetIdConverter implements AttributeConverter<NetId, Long> {


    @Override
    public Long convertToDatabaseColumn(NetId attribute) {
        return attribute.getNetId();
    }

    @Override
    public NetId convertToEntityAttribute(Long dbData) {
        return new NetId(dbData);
    }
}
