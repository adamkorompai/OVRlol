package lol.ovr.ovr_engine.infrastructure.adapter.in.kafka;

import lol.ovr.ovr_engine.domain.port.in.GenerateOvrCardUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMatchCompletedConsumer {

    private final GenerateOvrCardUseCase generateOvrCardUseCase;

    @KafkaListener(topics = "${ovr.kafka.topic.match-completed}", groupId = "ovr-engine-group")
    public void consume(MatchCompletedEventPayload event) {
        log.info("[KAFKA CONSUMER] Nouvel événement de match reçu pour le joueur : {}", event.puuid());

        generateOvrCardUseCase.processMatchStats(
                event.matchId(), event.puuid(),
                event.kills(), event.deaths(), event.assists(),
                event.creepScore(), event.visionScore(), event.isWin(),
                event.gameCreation()
        );
    }

    public record MatchCompletedEventPayload(
            String matchId, String puuid, String championId,
            int kills, int deaths, int assists,
            int creepScore, int visionScore, boolean isWin,
            long gameCreation
    ) {}
}