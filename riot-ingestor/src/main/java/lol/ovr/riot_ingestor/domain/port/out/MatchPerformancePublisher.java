package lol.ovr.riot_ingestor.domain.port.out;

import lol.ovr.riot_ingestor.domain.model.PlayerMatchPerformance;

public interface MatchPerformancePublisher {
    void publish(PlayerMatchPerformance performance);
}
