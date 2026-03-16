package lol.ovr.ovr_engine.domain.service;

import java.util.List;

import lol.ovr.ovr_engine.domain.model.MatchPerformanceInput;
import lol.ovr.ovr_engine.domain.model.OvrCard;

public class LoLOvrCalculator {

    public OvrCard calculate(MatchPerformanceInput input) {
        int mechanicsScore = calculateMechanics(input.kda().kills(), input.kda().deaths(), input.kda().assists());
        int farmingScore = calculateFarming(input.creepScore());
        int visionScore = calculateVision(input.visionScore());

        double rawOverall = (mechanicsScore * 0.5) + (farmingScore * 0.3) + (visionScore * 0.2);
        int overallRating = (int) Math.min(99, Math.round(rawOverall));
        if (input.context().win()) overallRating = Math.min(99, overallRating + 3);

        List<Integer> itemIds = List.of(
            input.items().item0(),
            input.items().item1(),
            input.items().item2(),
            input.items().item3(),
            input.items().item4(),
            input.items().item5(),
            input.items().item6()
        );

        return new OvrCard(
                input.matchId(), input.puuid(), overallRating, mechanicsScore, farmingScore, visionScore, input.context().gameCreation(),
                input.championName(), input.kda().kills(), input.kda().deaths(), input.kda().assists(), input.creepScore(), input.context().win(), input.context().gameDuration(),
            input.context().queueId(),
            input.summonerSpells().summoner1Id(),
            input.summonerSpells().summoner2Id(),
            input.items().item0(),
            input.items().item1(),
            input.items().item2(),
            input.items().item3(),
            input.items().item4(),
            input.items().item5(),
            input.items().item6(),
            itemIds,
            input.runes().primaryRuneIds(), input.runes().secondaryRuneIds(), input.enemyChampions()
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
