package lol.ovr.player_profile.domain.model;

public record PlayerCard(
        String puuid,
        String matchId,
        int overallRating,
        int mechanicsScore,
        int farmingScore,
        int visionScore,
        long gameCreation
) {}