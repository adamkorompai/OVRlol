package lol.ovr.ovr_engine.domain.service;

import lol.ovr.ovr_engine.domain.model.OvrCard;

import java.util.List;

public class LoLOvrCalculator {

    public OvrCard calculate(
            String matchId,
            String puuid,
            String championName,
            int kills,
            int deaths,
            int assists,
            int cs,
            int vision,
            boolean win,
            long gameCreation,
            long gameDuration,
            List<Integer> itemIds,
            List<Integer> primaryRuneIds,
            List<Integer> secondaryRuneIds,
            List<String> enemyChampions
    ) {
        int mechanicsScore = calculateMechanics(kills, deaths, assists);
        int farmingScore = calculateFarming(cs);
        int visionScore = calculateVision(vision);

        double rawOverall = (mechanicsScore * 0.5) + (farmingScore * 0.3) + (visionScore * 0.2);
        int overallRating = (int) Math.min(99, Math.round(rawOverall));
        if (win) overallRating = Math.min(99, overallRating + 3);

        return new OvrCard(
                matchId, puuid, overallRating, mechanicsScore, farmingScore, visionScore, gameCreation,
                championName, kills, deaths, assists, cs, win, gameDuration,
                itemIds, primaryRuneIds, secondaryRuneIds, enemyChampions
        );
    }

    private int calculateMechanics(int kills, int deaths, int assists) {
        double kda = (double) (kills + assists) / Math.max(1, deaths);
        return (int) Math.min(99, (kda / 5.0) * 99);
    }

    private int calculateFarming(int cs) {
        return (int) Math.min(99, ((double) cs / 250.0) * 99);
    }

    private int calculateVision(int vision) {
        return (int) Math.min(99, ((double) vision / 50.0) * 99);
    }
}
