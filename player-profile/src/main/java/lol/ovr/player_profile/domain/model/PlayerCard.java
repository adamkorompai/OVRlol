package lol.ovr.player_profile.domain.model;

import java.util.List;

public record PlayerCard(
        String puuid,
        String matchId,
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
) {}