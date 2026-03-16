package lol.ovr.player_profile.domain.model;

public record MatchContext(
        long gameCreation,
        boolean win,
        long gameDuration,
        int queueId
) {}