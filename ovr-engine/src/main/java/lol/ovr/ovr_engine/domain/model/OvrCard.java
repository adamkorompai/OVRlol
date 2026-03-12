package lol.ovr.ovr_engine.domain.model;

public record OvrCard(
        String matchId,
        String puuid,
        int overallRating,
        int mechanicsScore,
        int farmingScore,
        int visionScore,
        long gameCreation
) {
    public OvrCard {
        if (overallRating < 0 || overallRating > 99) throw new IllegalArgumentException("L'OVR doit être entre 0 et 99");
    }
}
