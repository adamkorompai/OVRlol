package lol.ovr.ovr_engine.domain.model;

public record MatchParticipant(
        String summonerName,
        String championName,
        String teamPosition,
        int teamId
) {}
