package lol.ovr.player_profile.infrastructure.adapter.in.web;

import lol.ovr.player_profile.domain.model.PlayerCard;
import lol.ovr.player_profile.domain.port.in.GetPlayerCardsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PlayerProfileController {

    private final GetPlayerCardsUseCase getPlayerCardsUseCase;

    @GetMapping("/{puuid}/cards")
    public ResponseEntity<List<PlayerCard>> getPlayerCards(@PathVariable String puuid) {
        List<PlayerCard> cards = getPlayerCardsUseCase.getCards(puuid);
        return ResponseEntity.ok(cards);
    }
}
