package lol.ovr.player_profile.domain.port.in;

import lol.ovr.player_profile.domain.model.PlayerCard;

import java.util.List;

public interface GetPlayerCardsUseCase {
    List<PlayerCard> getCards(String puuid);
}
