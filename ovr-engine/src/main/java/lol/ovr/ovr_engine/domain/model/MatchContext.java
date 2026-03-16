package lol.ovr.ovr_engine.domain.model;

public record MatchContext(
        boolean win,
        long gameCreation,
        long gameDuration,
        int queueId
) {}