package lol.ovr.riot_ingestor.domain.model;

import java.util.List;

public record RuneSetup(
        List<Integer> primaryRuneIds,
        List<Integer> secondaryRuneIds
) {}