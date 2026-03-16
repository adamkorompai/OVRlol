package lol.ovr.player_profile.domain.model;

import java.util.List;

public record RuneSetup(
        List<Integer> primaryRuneIds,
        List<Integer> secondaryRuneIds
) {}