package lol.ovr.player_profile.infrastructure.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lol.ovr.player_profile.infrastructure.adapter.out.persistence.entity.PlayerCardEntity;

@Repository
public interface PlayerCardRepository extends JpaRepository<PlayerCardEntity, Long> {
    List<PlayerCardEntity> findByPuuidOrderByGameCreationDesc(String puuid);

    boolean existsByPuuidAndMatchId(String puuid, String matchId);
}
