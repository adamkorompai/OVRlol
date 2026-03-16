package lol.ovr.ovr_engine.domain.model;

import java.util.List;

public record RuneSetup(
        List<Integer> primaryRuneIds,
        List<Integer> secondaryRuneIds
) {}