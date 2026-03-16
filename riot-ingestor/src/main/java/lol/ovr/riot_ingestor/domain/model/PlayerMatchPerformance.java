package lol.ovr.riot_ingestor.domain.model;


import java.util.List;

public record PlayerMatchPerformance(
        String matchId,
        String puuid,
        String championName,
        int kills,
        int deaths,
        int assists,
        int creepScore,
        int visionScore,
        boolean isWin,
        long gameCreation,
        long gameDuration,
        List<Integer> itemsIds,
        List<Integer> primaryRuneIds,
        List<Integer> secondaryRuneIds,
        List<String> enemyChampions
) {
    public PlayerMatchPerformance {
        if (matchId == null || matchId.isBlank()) throw new IllegalArgumentException("matchId cannot be empty");
        if (puuid == null || puuid.isBlank()) throw new IllegalArgumentException("puuid cannot be empty");
        if (deaths < 0) throw new IllegalArgumentException("deaths cannot be negative");
    }
}