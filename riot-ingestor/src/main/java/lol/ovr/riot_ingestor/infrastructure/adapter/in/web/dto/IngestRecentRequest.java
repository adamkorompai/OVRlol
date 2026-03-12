package lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record IngestRecentRequest(
        @NotBlank(message = "puuid est obligatoire") String puuid,
        @Min(1) @Max(20) int count
) {}
