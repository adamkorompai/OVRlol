package lol.ovr.ovr_engine.domain.service;

import lol.ovr.ovr_engine.domain.model.OvrCard;
import lol.ovr.ovr_engine.domain.port.in.GenerateOvrCardUseCase;
import lol.ovr.ovr_engine.domain.port.out.OvrCardPublisher;

import java.util.List;

public class OvrGenerationService implements GenerateOvrCardUseCase {

    private final LoLOvrCalculator calculator;
    private final OvrCardPublisher publisher;

    public OvrGenerationService(LoLOvrCalculator calculator, OvrCardPublisher publisher) {
        this.calculator = calculator;
        this.publisher = publisher;
    }

    @Override
    public void processMatchStats(
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
        OvrCard card = calculator.calculate(
                matchId, puuid, championName, kills, deaths, assists, cs, vision, win, gameCreation, gameDuration,
                itemIds, primaryRuneIds, secondaryRuneIds, enemyChampions
        );
        publisher.publishCard(card);
    }
}
