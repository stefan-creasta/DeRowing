package nl.tudelft.sem.template.activity.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class NetIdConverter implements AttributeConverter<NetId, long> {


    @Override
    public long convertToDatabaseColumn(NetId attribute) {
        return attribute.getNetId();
    }

    @Override
    public NetId convertToEntityAttribute(long dbData) {
        return new NetId(dbData);
    }
}
