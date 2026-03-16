package lol.ovr.ovr_engine.domain.model;

import java.util.List;

public record MatchPerformanceInput(
        String matchId,
        String puuid,
        String championName,
        int creepScore,
        int visionScore,
        KdaStats kda,
        MatchContext context,
    SummonerSpells summonerSpells,
    ItemSlots items,
        RuneSetup runes,
        List<String> enemyChampions
) {
    public MatchPerformanceInput {
        if (matchId == null || matchId.isBlank()) throw new IllegalArgumentException("matchId cannot be empty");
        if (puuid == null || puuid.isBlank()) throw new IllegalArgumentException("puuid cannot be empty");
        if (kda == null) throw new IllegalArgumentException("kda cannot be null");
        if (context == null) throw new IllegalArgumentException("context cannot be null");
        if (summonerSpells == null) throw new IllegalArgumentException("summonerSpells cannot be null");
        if (items == null) throw new IllegalArgumentException("items cannot be null");
        if (runes == null) throw new IllegalArgumentException("runes cannot be null");
        if (enemyChampions == null) enemyChampions = List.of();
    }
}