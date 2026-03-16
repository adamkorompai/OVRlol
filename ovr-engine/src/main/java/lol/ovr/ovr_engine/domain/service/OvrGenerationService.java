package lol.ovr.ovr_engine.domain.service;

import lol.ovr.ovr_engine.domain.model.MatchPerformanceInput;
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
    public void processMatchStats(MatchPerformanceInput input) {
        OvrCard card = calculator.calculate(input);
        publisher.publishCard(card);
    }
}
