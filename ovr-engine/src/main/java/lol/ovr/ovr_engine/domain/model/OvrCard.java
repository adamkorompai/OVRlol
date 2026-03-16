package lol.ovr.ovr_engine.domain.model;

import java.util.List;

public record OvrCard(
        String matchId,
        String puuid,
        int overallRating,
        int mechanicsScore,
        int farmingScore,
        int visionScore,
        long gameCreation,
        String championName,
        int kills,
        int deaths,
        int assists,
        int creepScore,
        boolean win,
        long gameDuration,
        List<Integer> itemIds,
        List<Integer> primaryRuneIds,
        List<Integer> secondaryRuneIds,
        List<String> enemyChampions
) {
    public OvrCard {
        if (overallRating < 0 || overallRating > 99) throw new IllegalArgumentException("L'OVR doit être entre 0 et 99");
    }
}
