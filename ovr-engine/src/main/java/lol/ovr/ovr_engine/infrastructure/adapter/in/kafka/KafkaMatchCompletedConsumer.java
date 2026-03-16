package lol.ovr.ovr_engine.infrastructure.adapter.in.kafka;

import lol.ovr.ovr_engine.domain.model.KdaStats;
import lol.ovr.ovr_engine.domain.model.MatchContext;
import lol.ovr.ovr_engine.domain.model.MatchPerformanceInput;
import lol.ovr.ovr_engine.domain.model.RuneSetup;
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
    private final MatchCompletedEventMapper eventMapper;

    @KafkaListener(topics = "${ovr.kafka.topic.match-completed}", groupId = "ovr-engine-group")
    public void consume(MatchCompletedEventPayload event) {
        MatchPerformanceInput input = eventMapper.toDomain(event);
        generateOvrCardUseCase.processMatchStats(input);
    }
}