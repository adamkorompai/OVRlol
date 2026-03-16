package lol.ovr.player_profile.domain.model;

import java.util.List;

public record PlayerCard(
        String puuid,
        String matchId,
        int overallRating,
        int mechanicsScore,
        int farmingScore,
        int visionScore,
        String championName,
        int creepScore,
        KdaStats kda,
        MatchContext context,
        SummonerSpells summonerSpells,
        ItemSlots items,
        List<Integer> itemIds,
        RuneSetup runes,
        List<String> enemyChampions,
        List<MatchParticipant> participants
) {
        public PlayerCard {
                if (puuid == null || puuid.isBlank()) throw new IllegalArgumentException("puuid cannot be empty");
                if (matchId == null || matchId.isBlank()) throw new IllegalArgumentException("matchId cannot be empty");
                if (kda == null) throw new IllegalArgumentException("kda cannot be null");
                if (context == null) throw new IllegalArgumentException("context cannot be null");
                if (summonerSpells == null) throw new IllegalArgumentException("summonerSpells cannot be null");
                if (items == null) throw new IllegalArgumentException("items cannot be null");
                if (runes == null) throw new IllegalArgumentException("runes cannot be null");
                if (itemIds == null) itemIds = List.of();
                if (enemyChampions == null) enemyChampions = List.of();
                       if (participants == null) participants = List.of();
        }
}