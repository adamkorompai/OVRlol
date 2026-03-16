package lol.ovr.player_profile.infrastructure.adapter.out.persistence.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;

@Converter
public class StringListJsonConverter implements AttributeConverter<List<String>, String> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            return attribute == null ? "[]" : MAPPER.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to serialize string list", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            return (dbData == null || dbData.isBlank())
                    ? List.of()
                    : MAPPER.readValue(dbData, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to deserialize string list", e);
        }
    }
}