package com.gloddy.server.core.converter;


import com.gloddy.server.auth.entity.kind.Personality;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class EnumArrayConverter implements AttributeConverter<List<Personality>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<Personality> attribute) {

        if (attribute.isEmpty() || attribute == null) {
            return null;
        }

        List<String> personalities = convertEnumToString(attribute);

        return String.join(DELIMITER, personalities);
    }

    @Override
    public List<Personality> convertToEntityAttribute(String dbData) {
        if (Strings.isEmpty(dbData)) {
            return new ArrayList<>();
        }

        List<Personality> personalities = Arrays.stream(dbData.split(DELIMITER))
                .map(name -> convertStringToPersonality(name))
                .collect(Collectors.toUnmodifiableList());

        return personalities;
    }

    private List<String> convertEnumToString(List<Personality> attribute) {
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.toUnmodifiableList());
    }

    private Personality convertStringToPersonality(String name) {
        return Personality.valueOf(name);
    }
}
