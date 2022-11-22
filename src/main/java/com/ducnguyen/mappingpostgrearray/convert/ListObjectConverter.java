package com.ducnguyen.mappingpostgrearray.convert;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.LinkedList;
import java.util.List;

@Converter
public class ListObjectConverter<T> implements AttributeConverter<List<T>, String> {

    private static final Logger log = LoggerFactory.getLogger(ListObjectConverter.class);
    private final ObjectMapper mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

    @Override
    public String convertToDatabaseColumn(List<T> o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("Error when converting to database column", e);
            return null;
        }
    }

    @Override
    public List<T> convertToEntityAttribute(String o) {
        List<T> res = new LinkedList<>();
        if (Strings.isNotBlank(o)) {
            try {
                TypeReference<List<T>> typeRef = new TypeReference<List<T>>() {
                };
                res = mapper.readValue(o, typeRef);
            } catch (JsonProcessingException e) {
                log.error("Error when converting to entity attribute", e);
                return null;
            }
        }

        return res;
    }
}
