package lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record IngestRequest(
        @NotBlank(message = "matchId est obligatoire") String matchId,
        @NotBlank(message = "puuid est obligatoire") String puuid
) {}
