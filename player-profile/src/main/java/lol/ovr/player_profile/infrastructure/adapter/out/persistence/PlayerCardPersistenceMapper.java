package lol.ovr.player_profile.infrastructure.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import lol.ovr.player_profile.domain.model.ItemSlots;
import lol.ovr.player_profile.domain.model.KdaStats;
import lol.ovr.player_profile.domain.model.MatchContext;
import lol.ovr.player_profile.domain.model.PlayerCard;
import lol.ovr.player_profile.domain.model.RuneSetup;
import lol.ovr.player_profile.domain.model.SummonerSpells;
import lol.ovr.player_profile.infrastructure.adapter.out.persistence.entity.PlayerCardEntity;

@Component
public class PlayerCardPersistenceMapper {

    public PlayerCardEntity toEntity(PlayerCard card) {
        return PlayerCardEntity.builder()
                .puuid(card.puuid())
                .matchId(card.matchId())
                .overallRating(card.overallRating())
                .mechanicsScore(card.mechanicsScore())
                .farmingScore(card.farmingScore())
                .visionScore(card.visionScore())
                .gameCreation(card.context().gameCreation())
                .championName(card.championName())
                .kills(card.kda().kills())
                .deaths(card.kda().deaths())
                .assists(card.kda().assists())
                .creepScore(card.creepScore())
                .win(card.context().win())
                .gameDuration(card.context().gameDuration())
                .queueId(card.context().queueId())
                .summoner1Id(card.summonerSpells().summoner1Id())
                .summoner2Id(card.summonerSpells().summoner2Id())
                .item0(card.items().item0())
                .item1(card.items().item1())
                .item2(card.items().item2())
                .item3(card.items().item3())
                .item4(card.items().item4())
                .item5(card.items().item5())
                .item6(card.items().item6())
                .itemIds(safe(card.itemIds()))
                .primaryRuneIds(safe(card.runes().primaryRuneIds()))
                .secondaryRuneIds(safe(card.runes().secondaryRuneIds()))
                .enemyChampions(safe(card.enemyChampions()))
                .build();
    }

    public PlayerCard toDomain(PlayerCardEntity entity) {
        return new PlayerCard(
                entity.getPuuid(),
                entity.getMatchId(),
                entity.getOverallRating(),
                entity.getMechanicsScore(),
                entity.getFarmingScore(),
                entity.getVisionScore(),
                entity.getChampionName(),
                entity.getCreepScore(),
                new KdaStats(entity.getKills(), entity.getDeaths(), entity.getAssists()),
                new MatchContext(entity.getGameCreation(), entity.isWin(), entity.getGameDuration(), entity.getQueueId()),
                new SummonerSpells(entity.getSummoner1Id(), entity.getSummoner2Id()),
                new ItemSlots(
                    entity.getItem0(),
                    entity.getItem1(),
                    entity.getItem2(),
                    entity.getItem3(),
                    entity.getItem4(),
                    entity.getItem5(),
                    entity.getItem6()
                ),
                safe(entity.getItemIds()),
                new RuneSetup(safe(entity.getPrimaryRuneIds()), safe(entity.getSecondaryRuneIds())),
                safe(entity.getEnemyChampions())
        );
    }

    private static <T> List<T> safe(List<T> value) {
        return value == null ? List.of() : value;
    }
}