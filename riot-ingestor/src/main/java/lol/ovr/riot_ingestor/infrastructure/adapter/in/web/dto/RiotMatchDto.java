package lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RiotMatchDto(RiotInfoDto info) {}
