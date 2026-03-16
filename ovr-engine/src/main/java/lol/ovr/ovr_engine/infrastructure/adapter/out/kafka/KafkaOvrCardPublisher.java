package lol.ovr.ovr_engine.infrastructure.adapter.out.kafka;

import lol.ovr.ovr_engine.domain.model.OvrCard;
import lol.ovr.ovr_engine.domain.port.out.OvrCardPublisher;
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
public class KafkaOvrCardPublisher implements OvrCardPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${ovr.kafka.topic.card-generated}")
    private String topicName;

    @Override
    public void publishCard(OvrCard card) {
        log.info("[KAFKA PUBLISHER] Expédition de la carte OVR finale pour le joueur {} sur le topic : {}", card.puuid(), topicName);
        log.info("[ENGINE->KAFKA] matchId={} itemIds={}", card.matchId(), card.itemIds());
        kafkaTemplate.send(topicName, card.puuid(), card);
    }
}
