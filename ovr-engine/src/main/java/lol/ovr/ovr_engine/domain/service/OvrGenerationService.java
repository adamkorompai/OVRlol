package lol.ovr.ovr_engine.domain.service;

import lol.ovr.ovr_engine.domain.model.OvrCard;
import lol.ovr.ovr_engine.domain.port.in.GenerateOvrCardUseCase;
import lol.ovr.ovr_engine.domain.port.out.OvrCardPublisher;

public class OvrGenerationService implements GenerateOvrCardUseCase {

    private final LoLOvrCalculator calculator;
    private final OvrCardPublisher publisher;

    public OvrGenerationService(LoLOvrCalculator calculator, OvrCardPublisher publisher) {
        this.calculator = calculator;
        this.publisher = publisher;
    }

    @Override
    public void processMatchStats(String matchId, String puuid, int kills, int deaths, int assists, int cs, int vision, boolean win, long gameCreation) {
        OvrCard card = calculator.calculate(matchId, puuid, kills, deaths, assists, cs, vision, win, gameCreation);

        publisher.publishCard(card);
    }
}
