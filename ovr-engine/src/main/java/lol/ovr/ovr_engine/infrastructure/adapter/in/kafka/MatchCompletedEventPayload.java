package lol.ovr.ovr_engine.infrastructure.adapter.in.kafka;

import java.util.List;

public record MatchCompletedEventPayload(
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
        List<Integer> primaryRuneIds,
        List<Integer> secondaryRuneIds,
        List<String> enemyChampions
) {}