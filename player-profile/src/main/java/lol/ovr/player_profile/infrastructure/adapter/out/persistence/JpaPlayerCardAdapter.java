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

    @Override
    public void save(PlayerCard card) {
        if (repository.existsByPuuidAndMatchId(card.puuid(), card.matchId())) {
            log.info("[POSTGRESQL] Carte déjà existante pour matchId: {}, on skip.", card.matchId());
            return;
        }

        PlayerCardEntity entity = PlayerCardEntity.builder()
                .puuid(card.puuid())
                .matchId(card.matchId())
                .overallRating(card.overallRating())
                .mechanicsScore(card.mechanicsScore())
                .farmingScore(card.farmingScore())
                .visionScore(card.visionScore())
                .gameCreation(card.gameCreation())
                .build();

        PlayerCardEntity savedEntity = repository.save(entity);
        log.info("[POSTGRESQL] Carte OVR sauvegardée avec succès en base de données avec l'ID : {}", savedEntity.getId());
    }

    @Override
    public List<PlayerCard> findByPuuid(String puuid) {
        return repository.findByPuuidOrderByGameCreationDesc(puuid).stream()
                .map(entity -> new PlayerCard(
                        entity.getPuuid(),
                        entity.getMatchId(),
                        entity.getOverallRating(),
                        entity.getMechanicsScore(),
                        entity.getFarmingScore(),
                        entity.getVisionScore(),
                        entity.getGameCreation()
                ))
                .collect(Collectors.toList());
    }
}
