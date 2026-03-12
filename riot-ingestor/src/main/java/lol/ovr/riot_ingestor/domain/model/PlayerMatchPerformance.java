package lol.ovr.riot_ingestor.domain.model;


public record PlayerMatchPerformance(
        String matchId,
        String puuid,
        String championId,
        int kills,
        int deaths,
        int assists,
        int creepScore,
        int visionScore,
        boolean isWin,
        long gameCreation
) {
    public PlayerMatchPerformance {
        if (matchId == null || matchId.isBlank()) throw new IllegalArgumentException("matchId cannot be empty");
        if (puuid == null || puuid.isBlank()) throw new IllegalArgumentException("puuid cannot be empty");
        if (deaths < 0) throw new IllegalArgumentException("deaths cannot be negative");
    }
}