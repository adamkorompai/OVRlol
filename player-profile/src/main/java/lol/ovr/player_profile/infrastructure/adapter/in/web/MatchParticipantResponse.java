package lol.ovr.player_profile.infrastructure.adapter.in.web;

public record MatchParticipantResponse(
        String summonerName,
        String championName,
        String teamPosition,
        int teamId
) {}
