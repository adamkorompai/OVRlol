package lol.ovr.player_profile.infrastructure.adapter.in.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import lol.ovr.player_profile.domain.model.PlayerCard;
import lol.ovr.player_profile.domain.port.in.SaveCardUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaOvrCardConsumer {

    private final SaveCardUseCase saveCardUseCase;
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "${ovr.kafka.topic.card-generated}", groupId = "player-profile-group")
    public void consume(OvrCardEventPayload payload) {
        log.info("[KAFKA CONSUMER] Nouvelle carte OVR pour {}. Diffusion WebSockets...", payload.puuid());

        PlayerCard card = new PlayerCard(
                payload.puuid(),
                payload.matchId(),
                payload.overallRating(),
                payload.mechanicsScore(),
                payload.farmingScore(),
                payload.visionScore(),
                payload.gameCreation(),
                payload.championName(),
                payload.kills(),
                payload.deaths(),
                payload.assists(),
                payload.creepScore(),
                payload.win(),
                payload.gameDuration(),
                payload.itemIds(),
                payload.primaryRuneIds(),
                payload.secondaryRuneIds(),
                payload.enemyChampions()
        );

        log.info("[PROFILE<-KAFKA] matchId={} itemIds={}", payload.matchId(), payload.itemIds());
        saveCardUseCase.saveCard(card);
        messagingTemplate.convertAndSend("/topic/cards/" + card.puuid(), card);
    }

    public record OvrCardEventPayload(
            String matchId,
            String puuid,
            int overallRating,
            int mechanicsScore,
            int farmingScore,
            int visionScore,
            long gameCreation,
            String championName,
            int kills,
            int deaths,
            int assists,
            int creepScore,
            boolean win,
            long gameDuration,
            List<Integer> itemIds,
            List<Integer> primaryRuneIds,
            List<Integer> secondaryRuneIds,
            List<String> enemyChampions
    ) {}
}