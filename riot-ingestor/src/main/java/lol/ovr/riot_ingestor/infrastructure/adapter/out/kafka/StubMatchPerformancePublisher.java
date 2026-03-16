package lol.ovr.riot_ingestor.infrastructure.adapter.out.kafka;

import lol.ovr.riot_ingestor.domain.model.PlayerMatchPerformance;
import lol.ovr.riot_ingestor.domain.port.out.MatchPerformancePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StubMatchPerformancePublisher implements MatchPerformancePublisher {

    @Override
    public void publish(PlayerMatchPerformance performance) {
        log.info("[STUB KAFKA] Événement prêt à être publié sur le topic : {}", performance);
    }
}
