package lol.ovr.riot_ingestor.infrastructure.adapter.out.kafka;

public record MatchParticipantPayload(
        String summonerName,
        String championName,
        String teamPosition,
        int teamId
) {}
