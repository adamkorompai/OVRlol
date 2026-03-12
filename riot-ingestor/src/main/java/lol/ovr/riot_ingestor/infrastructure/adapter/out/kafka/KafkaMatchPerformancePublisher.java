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
@Primary // Crucial : Dit à Spring d'utiliser ce bean plutôt que le Stub
@RequiredArgsConstructor
public class KafkaMatchPerformancePublisher implements MatchPerformancePublisher {

    // L'outil magique de Spring Boot pour parler à Kafka
    private final KafkaTemplate<String, Object> kafkaTemplate;

    // On récupère le nom du topic depuis l'application.yml
    @Value("${ovr.kafka.topic.match-completed}")
    private String topicName;

    @Override
    public void publish(PlayerMatchPerformance performance) {
        log.info("[KAFKA ADAPTER] Publication de la performance sur le topic : {}", topicName);

        // On envoie le message.
        // Clé = matchId (garantit que les events d'un même match vont sur la même partition)
        // Valeur = l'objet métier (qui sera sérialisé en JSON)
        kafkaTemplate.send(topicName, performance.matchId(), performance);
    }
}
