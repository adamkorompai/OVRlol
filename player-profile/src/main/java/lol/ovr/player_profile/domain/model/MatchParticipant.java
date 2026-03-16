package lol.ovr.player_profile.domain.model;

public record MatchParticipant(
        String summonerName,
        String championName,
        String teamPosition,
        int teamId
) {}
