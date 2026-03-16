package lol.ovr.player_profile.domain.model;

public record KdaStats(
        int kills,
        int deaths,
        int assists
) {}