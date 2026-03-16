package lol.ovr.riot_ingestor.domain.model;

public record KdaStats(
        int kills,
        int deaths,
        int assists
) {}