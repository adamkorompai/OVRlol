package lol.ovr.player_profile.infrastructure.adapter.out.persistence.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;

@Converter
public class IntegerListJsonConverter implements AttributeConverter<List<Integer>, String> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        try {
            return attribute == null ? "[]" : MAPPER.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to serialize integer list", e);
        }
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        try {
            return (dbData == null || dbData.isBlank())
                    ? List.of()
                    : MAPPER.readValue(dbData, new TypeReference<List<Integer>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to deserialize integer list", e);
        }
    }
}