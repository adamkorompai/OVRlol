package lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto;

import java.util.List;

public record RiotPerkStyleDto(String description, int style, List<RiotPerkSelectionDto> selections) {
}
