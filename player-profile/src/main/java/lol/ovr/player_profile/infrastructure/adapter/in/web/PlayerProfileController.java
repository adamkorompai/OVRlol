package lol.ovr.player_profile.infrastructure.adapter.in.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lol.ovr.player_profile.domain.port.in.GetPlayerCardsUseCase;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PlayerProfileController {

    private final GetPlayerCardsUseCase getPlayerCardsUseCase;
    private final PlayerCardResponseMapper responseMapper;

    @GetMapping("/{puuid}/cards")
    public ResponseEntity<List<PlayerCardResponse>> getPlayerCards(@PathVariable String puuid) {
        List<PlayerCardResponse> cards = getPlayerCardsUseCase.getCards(puuid).stream()
                .map(responseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(cards);
    }
}
