package lol.ovr.riot_ingestor.infrastructure.adapter.out.riot.mapper;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import lol.ovr.riot_ingestor.domain.model.PlayerMatchPerformance;
import lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto.RiotInfoDto;
import lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto.RiotMatchDto;
import lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto.RiotParticipantDto;
import lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto.RiotPerkSelectionDto;
import lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto.RiotPerksDto;

@Component
public class RiotMatchPerformanceMapper {

    public PlayerMatchPerformance toDomain(String matchId, String puuid, RiotMatchDto match) {
        RiotInfoDto info = match.info();

        RiotParticipantDto me = info.participants().stream()
                .filter(p -> puuid.equals(p.puuid()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Participant not found for puuid=" + puuid));

        List<Integer> itemIds = extractItemIds(me);
        List<Integer> primaryRuneIds = extractRuneIds(me.perks(), "primaryStyle");
        List<Integer> secondaryRuneIds = extractRuneIds(me.perks(), "subStyle");

        List<String> enemyChampions = info.participants().stream()
                .filter(p -> p.teamId() != me.teamId())
                .map(RiotParticipantDto::championName)
                .toList();

        return new PlayerMatchPerformance(
                matchId,
                puuid,
                me.championName(),
                me.kills(),
                me.deaths(),
                me.assists(),
                me.getTotalCs(),
                me.visionScore(),
                me.win(),
                info.gameCreation(),
                info.gameDuration(),
                itemIds,
                primaryRuneIds,
                secondaryRuneIds,
                enemyChampions
        );
    }

    private List<Integer> extractItemIds(RiotParticipantDto p) {
        return Stream.of(p.item0(), p.item1(), p.item2(), p.item3(), p.item4(), p.item5(), p.item6())
                .filter(id -> id > 0)
                .toList();
    }

    private List<Integer> extractRuneIds(RiotPerksDto perks, String description) {
        if (perks == null || perks.styles() == null) return List.of();

        return perks.styles().stream()
                .filter(style -> description.equals(style.description()))
                .findFirst()
                .map(style -> style.selections().stream().map(RiotPerkSelectionDto::perk).toList())
                .orElse(List.of());
    }
}