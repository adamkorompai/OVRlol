package lol.ovr.ovr_engine.infrastructure.adapter.in.kafka;

import lol.ovr.ovr_engine.domain.port.in.GenerateOvrCardUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMatchCompletedConsumer {

    private final GenerateOvrCardUseCase generateOvrCardUseCase;

    @KafkaListener(topics = "${ovr.kafka.topic.match-completed}", groupId = "ovr-engine-group")
    public void consume(MatchCompletedEventPayload event) {
        log.info("[ENGINE<-KAFKA] matchId={} itemIds={}", event.matchId(), event.itemIds());
        generateOvrCardUseCase.processMatchStats(
                event.matchId(),
                event.puuid(),
                event.championName(),
                event.kills(),
                event.deaths(),
                event.assists(),
                event.creepScore(),
                event.visionScore(),
                event.isWin(),
                event.gameCreation(),
                event.gameDuration(),
                event.itemIds(),
                event.primaryRuneIds(),
                event.secondaryRuneIds(),
                event.enemyChampions()
        );
    }

    public record MatchCompletedEventPayload(
            String matchId,
            String puuid,
            String championName,
            int kills,
            int deaths,
            int assists,
            int creepScore,
            int visionScore,
            boolean isWin,
            long gameCreation,
            long gameDuration,
            List<Integer> itemIds,
            List<Integer> primaryRuneIds,
            List<Integer> secondaryRuneIds,
            List<String> enemyChampions
    ) {}
}