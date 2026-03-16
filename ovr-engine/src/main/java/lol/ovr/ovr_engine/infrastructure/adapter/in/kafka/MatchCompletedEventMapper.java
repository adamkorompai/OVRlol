package lol.ovr.ovr_engine.infrastructure.adapter.in.kafka;

import org.springframework.stereotype.Component;

import lol.ovr.ovr_engine.domain.model.ItemSlots;
import lol.ovr.ovr_engine.domain.model.KdaStats;
import lol.ovr.ovr_engine.domain.model.MatchContext;
import lol.ovr.ovr_engine.domain.model.MatchPerformanceInput;
import lol.ovr.ovr_engine.domain.model.RuneSetup;
import lol.ovr.ovr_engine.domain.model.SummonerSpells;

@Component
public class MatchCompletedEventMapper {

    public MatchPerformanceInput toDomain(MatchCompletedEventPayload event) {
        return new MatchPerformanceInput(
                event.matchId(),
                event.puuid(),
                event.championName(),
                event.creepScore(),
                event.visionScore(),
                new KdaStats(event.kills(), event.deaths(), event.assists()),
                new MatchContext(event.isWin(), event.gameCreation(), event.gameDuration(), event.queueId()),
                new SummonerSpells(event.summoner1Id(), event.summoner2Id()),
                new ItemSlots(
                    event.item0(),
                    event.item1(),
                    event.item2(),
                    event.item3(),
                    event.item4(),
                    event.item5(),
                    event.item6()
                ),
                new RuneSetup(event.primaryRuneIds(), event.secondaryRuneIds()),
                event.enemyChampions()
        );
    }
}