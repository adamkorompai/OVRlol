package lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RiotParticipantDto(
        String puuid,
        String championName,
        int kills,
        int deaths,
        int assists,
        int totalMinionsKilled,
        int neutralMinionsKilled,
        int visionScore,
        boolean win
) {
    // Petit helper pour calculer le farm total (lane + jungle)
    public int getTotalCs() {
        return totalMinionsKilled + neutralMinionsKilled;
    }
}
