package lol.ovr.player_profile.infrastructure.adapter.out.persistence.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;
import lol.ovr.player_profile.domain.model.MatchParticipant;

@Converter
public class MatchParticipantListJsonConverter implements AttributeConverter<List<MatchParticipant>, String> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<MatchParticipant> attribute) {
        try {
            return attribute == null ? "[]" : MAPPER.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to serialize participants", e);
        }
    }

    @Override
    public List<MatchParticipant> convertToEntityAttribute(String dbData) {
        try {
            return (dbData == null || dbData.isBlank())
                    ? List.of()
                    : MAPPER.readValue(dbData, new TypeReference<>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to deserialize participants", e);
        }
    }
}
