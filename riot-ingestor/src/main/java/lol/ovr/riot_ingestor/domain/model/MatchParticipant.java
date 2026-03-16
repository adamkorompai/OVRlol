package lol.ovr.riot_ingestor.domain.model;

public record MatchParticipant(
        String summonerName,
        String championName,
        String teamPosition,
        int teamId
) {}
