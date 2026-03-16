package lol.ovr.player_profile.infrastructure.adapter.in.kafka;

import java.util.List;

public record OvrCardEventPayload(
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
        int queueId,
        int summoner1Id,
        int summoner2Id,
        int item0,
        int item1,
        int item2,
        int item3,
        int item4,
        int item5,
        int item6,
        List<Integer> itemIds,
        List<Integer> primaryRuneIds,
        List<Integer> secondaryRuneIds,
        List<String> enemyChampions
) {}