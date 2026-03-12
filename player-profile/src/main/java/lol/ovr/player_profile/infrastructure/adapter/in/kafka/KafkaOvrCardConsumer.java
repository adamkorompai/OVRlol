package lol.ovr.player_profile.infrastructure.adapter.in.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import lol.ovr.player_profile.domain.model.PlayerCard;
import lol.ovr.player_profile.domain.port.in.SaveCardUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
                payload.puuid(), payload.matchId(), payload.overallRating(),
                payload.mechanicsScore(), payload.farmingScore(), payload.visionScore(),
                payload.gameCreation()
        );

        saveCardUseCase.saveCard(card);

        // Pousse la carte vers Angular
        messagingTemplate.convertAndSend("/topic/cards/" + card.puuid(), card);
    }

    public record OvrCardEventPayload(
            String matchId, String puuid, int overallRating,
            int mechanicsScore, int farmingScore, int visionScore,
            long gameCreation
    ) {}
}