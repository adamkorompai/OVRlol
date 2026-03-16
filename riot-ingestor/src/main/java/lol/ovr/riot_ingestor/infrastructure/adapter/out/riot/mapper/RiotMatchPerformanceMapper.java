package lol.ovr.riot_ingestor.infrastructure.adapter.out.riot.mapper;

import java.util.List;
import org.springframework.stereotype.Component;

import lol.ovr.riot_ingestor.domain.model.ItemSlots;
import lol.ovr.riot_ingestor.domain.model.KdaStats;
import lol.ovr.riot_ingestor.domain.model.MatchContext;
import lol.ovr.riot_ingestor.domain.model.MatchParticipant;
import lol.ovr.riot_ingestor.domain.model.PlayerMatchPerformance;
import lol.ovr.riot_ingestor.domain.model.RuneSetup;
import lol.ovr.riot_ingestor.domain.model.SummonerSpells;
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

        List<Integer> primaryRuneIds = extractRuneIds(me.perks(), "primaryStyle");
        List<Integer> secondaryRuneIds = extractRuneIds(me.perks(), "subStyle");

        List<String> enemyChampions = info.participants().stream()
                .filter(p -> p.teamId() != me.teamId())
                .map(RiotParticipantDto::championName)
                .toList();

               List<MatchParticipant> participants = info.participants().stream()
                       .map(p -> new MatchParticipant(
                               p.riotIdGameName() != null ? p.riotIdGameName() : p.puuid(),
                               p.championName(),
                               p.teamPosition() != null ? p.teamPosition() : "",
                               p.teamId()
                       ))
                       .toList();

        KdaStats kda = new KdaStats(me.kills(), me.deaths(), me.assists());
        MatchContext context = new MatchContext(me.win(), info.gameCreation(), info.gameDuration(), info.queueId());
        SummonerSpells summonerSpells = new SummonerSpells(me.summoner1Id(), me.summoner2Id());
        ItemSlots items = new ItemSlots(
                me.item0(),
                me.item1(),
                me.item2(),
                me.item3(),
                me.item4(),
                me.item5(),
                me.item6()
        );
        RuneSetup runes = new RuneSetup(primaryRuneIds, secondaryRuneIds);

        return new PlayerMatchPerformance(
                matchId,
                puuid,
                me.championName(),
                me.getTotalCs(),
                me.visionScore(),
                kda,
                context,
                summonerSpells,
                items,
                runes,
                       enemyChampions,
                       participants
        );
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