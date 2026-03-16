package lol.ovr.riot_ingestor.infrastructure.adapter.out.kafka;

import lol.ovr.riot_ingestor.domain.model.PlayerMatchPerformance;
import lol.ovr.riot_ingestor.domain.port.out.MatchPerformancePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Primary
@RequiredArgsConstructor
public class KafkaMatchPerformancePublisher implements MatchPerformancePublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${ovr.kafka.topic.match-completed}")
    private String topicName;

    @Override
    public void publish(PlayerMatchPerformance performance) {
        log.info("[KAFKA ADAPTER] Publication de la performance sur le topic : {}", topicName);
        log.info("[INGESTOR->KAFKA] matchId={} itemIds={} primaryRunes={} secondaryRunes={}",
                performance.matchId(),
                performance.itemsIds(),
                performance.primaryRuneIds(),
                performance.secondaryRuneIds());
        kafkaTemplate.send(topicName, performance.matchId(), performance);
    }
}
