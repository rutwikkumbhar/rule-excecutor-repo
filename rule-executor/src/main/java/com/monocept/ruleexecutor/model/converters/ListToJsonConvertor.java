package com.monocept.ruleexecutor.model.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Converter
public class ListToJsonConvertor implements AttributeConverter<List<String>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List strings) {
        try {
            return objectMapper.writeValueAsString(strings);
        } catch (JsonProcessingException jpe) {
            log.warn("Cannot convert list into JSON");
            return null;
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, List.class);
        } catch (JsonProcessingException e) {
            log.warn("Cannot convert JSON into List");
            return null;
        }
    }
}
