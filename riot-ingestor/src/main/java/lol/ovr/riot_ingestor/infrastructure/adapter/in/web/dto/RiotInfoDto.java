package lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RiotInfoDto(
        long gameCreation,
        long gameDuration,
        int queueId,
        List<RiotParticipantDto> participants) {}
