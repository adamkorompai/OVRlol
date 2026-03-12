package lol.ovr.player_profile.domain.service;

import lol.ovr.player_profile.domain.model.PlayerCard;
import lol.ovr.player_profile.domain.port.in.GetPlayerCardsUseCase;
import lol.ovr.player_profile.domain.port.in.SaveCardUseCase;
import lol.ovr.player_profile.domain.port.out.PlayerCardPort;

import java.util.List;

public class PlayerProfileService implements SaveCardUseCase, GetPlayerCardsUseCase {

    private final PlayerCardPort playerCardPort;

    public PlayerProfileService(PlayerCardPort playerCardPort) {
        this.playerCardPort = playerCardPort;
    }

    @Override
    public void saveCard(PlayerCard card) {
        playerCardPort.save(card);
    }

    @Override
    public List<PlayerCard> getCards(String puuid) {
        return playerCardPort.findByPuuid(puuid);
    }
}
