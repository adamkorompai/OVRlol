package lol.ovr.player_profile.infrastructure.adapter.out.persistence;

import java.util.List;

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
    private final PlayerCardPersistenceMapper mapper;

    @Override
    public void save(PlayerCard card) {
        if (repository.existsByPuuidAndMatchId(card.puuid(), card.matchId())) return;
        repository.save(mapper.toEntity(card));
    }

    @Override
    public List<PlayerCard> findByPuuid(String puuid) {
        return repository.findByPuuidOrderByGameCreationDesc(puuid).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
