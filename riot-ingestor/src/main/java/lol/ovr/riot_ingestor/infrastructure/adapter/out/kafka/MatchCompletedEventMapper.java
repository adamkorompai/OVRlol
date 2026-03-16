package lol.ovr.riot_ingestor.infrastructure.adapter.out.kafka;

import org.springframework.stereotype.Component;

import lol.ovr.riot_ingestor.domain.model.PlayerMatchPerformance;

@Component
public class MatchCompletedEventMapper {

    public MatchCompletedEventPayload toPayload(PlayerMatchPerformance performance) {
        return new MatchCompletedEventPayload(
                performance.matchId(),
                performance.puuid(),
                performance.championName(),
                performance.kda().kills(),
                performance.kda().deaths(),
                performance.kda().assists(),
                performance.creepScore(),
                performance.visionScore(),
                performance.context().isWin(),
                performance.context().gameCreation(),
                performance.context().gameDuration(),
                performance.context().queueId(),
                performance.summonerSpells().summoner1Id(),
                performance.summonerSpells().summoner2Id(),
                performance.items().item0(),
                performance.items().item1(),
                performance.items().item2(),
                performance.items().item3(),
                performance.items().item4(),
                performance.items().item5(),
                performance.items().item6(),
                performance.runes().primaryRuneIds(),
                performance.runes().secondaryRuneIds(),
                performance.enemyChampions()
        );
    }
}