package lol.ovr.player_profile.infrastructure.adapter.in.kafka;

public record MatchParticipantPayload(
        String summonerName,
        String championName,
        String teamPosition,
        int teamId
) {}
