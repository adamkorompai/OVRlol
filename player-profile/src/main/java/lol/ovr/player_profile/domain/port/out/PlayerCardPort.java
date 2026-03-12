package lol.ovr.player_profile.domain.port.out;

import lol.ovr.player_profile.domain.model.PlayerCard;

import java.util.List;

public interface PlayerCardPort {
    void save(PlayerCard card);
    List<PlayerCard> findByPuuid(String puuid);
}
