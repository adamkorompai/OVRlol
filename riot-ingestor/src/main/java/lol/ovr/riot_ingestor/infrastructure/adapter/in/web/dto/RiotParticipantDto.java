package lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RiotParticipantDto(
        String puuid,
        String championName,
        int teamId,
        String teamPosition,
        int kills,
        int deaths,
        int assists,
        int totalMinionsKilled,
        int neutralMinionsKilled,
        int visionScore,
        boolean win,
        int item0,
        int item1,
        int item2,
        int item3,
        int item4,
        int item5,
        int item6,
        RiotPerksDto perks
) {
    public int getTotalCs() {
        return totalMinionsKilled + neutralMinionsKilled;
    }
}
