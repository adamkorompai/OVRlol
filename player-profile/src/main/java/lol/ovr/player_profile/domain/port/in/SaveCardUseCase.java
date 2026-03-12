package lol.ovr.player_profile.domain.port.in;

import lol.ovr.player_profile.domain.model.PlayerCard;

public interface SaveCardUseCase {
    void saveCard(PlayerCard card);
}