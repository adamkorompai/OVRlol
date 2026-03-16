package lol.ovr.ovr_engine.domain.port.in;

import lol.ovr.ovr_engine.domain.model.MatchPerformanceInput;

public interface GenerateOvrCardUseCase {
    void processMatchStats(MatchPerformanceInput input);
}
