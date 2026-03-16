package lol.ovr.player_profile.infrastructure.adapter.in.web;

import java.util.List;

import org.springframework.stereotype.Component;

import lol.ovr.player_profile.domain.model.PlayerCard;

@Component
public class PlayerCardResponseMapper {

    public PlayerCardResponse toResponse(PlayerCard card) {
    List<MatchParticipantResponse> participants = safe(card.participants()).stream()
           .map(p -> new MatchParticipantResponse(p.summonerName(), p.championName(), p.teamPosition(), p.teamId()))
           .toList();

        return new PlayerCardResponse(
                card.puuid(),
                card.matchId(),
                card.overallRating(),
                card.mechanicsScore(),
                card.farmingScore(),
                card.visionScore(),
                card.context().gameCreation(),
                card.championName(),
                card.kda().kills(),
                card.kda().deaths(),
                card.kda().assists(),
                card.creepScore(),
                card.context().win(),
                card.context().gameDuration(),
                card.context().queueId(),
                card.summonerSpells().summoner1Id(),
                card.summonerSpells().summoner2Id(),
                card.items().item0(),
                card.items().item1(),
                card.items().item2(),
                card.items().item3(),
                card.items().item4(),
                card.items().item5(),
                card.items().item6(),
                safe(card.itemIds()),
                safe(card.runes().primaryRuneIds()),
                safe(card.runes().secondaryRuneIds()),
           safe(card.enemyChampions()),
           participants
        );
    }

    private static <T> List<T> safe(List<T> value) {
        return value == null ? List.of() : value;
    }
}