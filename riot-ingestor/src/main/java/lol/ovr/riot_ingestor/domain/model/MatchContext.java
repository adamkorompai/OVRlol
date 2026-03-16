package lol.ovr.riot_ingestor.domain.model;

public record MatchContext(
        boolean isWin,
        long gameCreation,
        long gameDuration,
        int queueId
) {}