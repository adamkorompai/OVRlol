package lol.ovr.player_profile.infrastructure.adapter.in.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import lol.ovr.player_profile.domain.model.PlayerCard;
import lol.ovr.player_profile.domain.port.in.SaveCardUseCase;
import lol.ovr.player_profile.infrastructure.adapter.in.web.PlayerCardResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaOvrCardConsumer {

    private final SaveCardUseCase saveCardUseCase;
    private final SimpMessagingTemplate messagingTemplate;
    private final OvrCardEventMapper eventMapper;
    private final PlayerCardResponseMapper responseMapper;

    @KafkaListener(topics = "${ovr.kafka.topic.card-generated}", groupId = "player-profile-group")
    public void consume(OvrCardEventPayload payload) {
        log.info("[KAFKA CONSUMER] Nouvelle carte OVR pour {}. Diffusion WebSockets...", payload.puuid());

        PlayerCard card = eventMapper.toDomain(payload);

        saveCardUseCase.saveCard(card);
        messagingTemplate.convertAndSend("/topic/cards/" + card.puuid(), responseMapper.toResponse(card));
    }
}