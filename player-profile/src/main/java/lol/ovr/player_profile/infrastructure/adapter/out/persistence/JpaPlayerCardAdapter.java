package lol.ovr.player_profile.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lol.ovr.player_profile.domain.model.PlayerCard;
import lol.ovr.player_profile.domain.port.out.PlayerCardPort;
import lol.ovr.player_profile.infrastructure.adapter.out.persistence.entity.PlayerCardEntity;
import lol.ovr.player_profile.infrastructure.adapter.out.persistence.repository.PlayerCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JpaPlayerCardAdapter implements PlayerCardPort {

    private final PlayerCardRepository repository;

    private static <T> List<T> safe(List<T> v) {
        return v == null ? List.of() : v;
    }

    @Override
    public void save(PlayerCard card) {
        if (repository.existsByPuuidAndMatchId(card.puuid(), card.matchId())) return;

        PlayerCardEntity entity = PlayerCardEntity.builder()
                .puuid(card.puuid())
                .matchId(card.matchId())
                .overallRating(card.overallRating())
                .mechanicsScore(card.mechanicsScore())
                .farmingScore(card.farmingScore())
                .visionScore(card.visionScore())
                .gameCreation(card.gameCreation())
                .championName(card.championName())
                .kills(card.kills())
                .deaths(card.deaths())
                .assists(card.assists())
                .creepScore(card.creepScore())
                .win(card.win())
                .gameDuration(card.gameDuration())
                .itemIds(safe(card.itemIds()))
                .primaryRuneIds(safe(card.primaryRuneIds()))
                .secondaryRuneIds(safe(card.secondaryRuneIds()))
                .enemyChampions(safe(card.enemyChampions()))
                .build();

        repository.save(entity);
    }

    @Override
    public List<PlayerCard> findByPuuid(String puuid) {
        return repository.findByPuuidOrderByGameCreationDesc(puuid).stream()
                .map(e -> new PlayerCard(
                        e.getPuuid(),
                        e.getMatchId(),
                        e.getOverallRating(),
                        e.getMechanicsScore(),
                        e.getFarmingScore(),
                        e.getVisionScore(),
                        e.getGameCreation(),
                        e.getChampionName(),
                        e.getKills(),
                        e.getDeaths(),
                        e.getAssists(),
                        e.getCreepScore(),
                        e.isWin(),
                        e.getGameDuration(),
                        e.getItemIds(),
                        e.getPrimaryRuneIds(),
                        e.getSecondaryRuneIds(),
                        e.getEnemyChampions()
                ))
                .toList();
    }
}
