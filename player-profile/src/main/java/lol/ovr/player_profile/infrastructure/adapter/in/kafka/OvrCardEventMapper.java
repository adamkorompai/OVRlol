package lol.ovr.player_profile.infrastructure.adapter.in.kafka;

import java.util.List;

import org.springframework.stereotype.Component;

import lol.ovr.player_profile.domain.model.ItemSlots;
import lol.ovr.player_profile.domain.model.KdaStats;
import lol.ovr.player_profile.domain.model.MatchContext;
import lol.ovr.player_profile.domain.model.MatchParticipant;
import lol.ovr.player_profile.domain.model.PlayerCard;
import lol.ovr.player_profile.domain.model.RuneSetup;
import lol.ovr.player_profile.domain.model.SummonerSpells;

@Component
public class OvrCardEventMapper {

    public PlayerCard toDomain(OvrCardEventPayload payload) {
               List<MatchParticipant> participants = payload.participants() == null ? List.of() :
                   payload.participants().stream()
                       .map(p -> new MatchParticipant(p.summonerName(), p.championName(), p.teamPosition(), p.teamId()))
                       .toList();

        return new PlayerCard(
                payload.puuid(),
                payload.matchId(),
                payload.overallRating(),
                payload.mechanicsScore(),
                payload.farmingScore(),
                payload.visionScore(),
                payload.championName(),
                payload.creepScore(),
                new KdaStats(payload.kills(), payload.deaths(), payload.assists()),
                new MatchContext(payload.gameCreation(), payload.win(), payload.gameDuration(), payload.queueId()),
                new SummonerSpells(payload.summoner1Id(), payload.summoner2Id()),
                new ItemSlots(
                        payload.item0(),
                        payload.item1(),
                        payload.item2(),
                        payload.item3(),
                        payload.item4(),
                        payload.item5(),
                        payload.item6()
                ),
                safeItems(payload),
                new RuneSetup(safe(payload.primaryRuneIds()), safe(payload.secondaryRuneIds())),
                   safe(payload.enemyChampions()),
                   participants
        );
    }

    private static List<Integer> safeItems(OvrCardEventPayload payload) {
        List<Integer> itemIds = safe(payload.itemIds());
        if (!itemIds.isEmpty()) {
            return itemIds;
        }
        return List.of(
                payload.item0(),
                payload.item1(),
                payload.item2(),
                payload.item3(),
                payload.item4(),
                payload.item5(),
                payload.item6()
        );
    }

    private static <T> List<T> safe(List<T> value) {
        return value == null ? List.of() : value;
    }
}