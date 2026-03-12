package lol.ovr.riot_ingestor.infrastructure.adapter.in.web.controller;

import lol.ovr.riot_ingestor.infrastructure.adapter.out.riot.HttpRiotAccountAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountController {

    private final HttpRiotAccountAdapter riotAccountAdapter;

    @GetMapping("/by-riot-id/{gameName}/{tagLine}")
    public String resolvePuuid(@PathVariable String gameName, @PathVariable String tagLine) {
        log.info("Recherche du PUUID pour le joueur {}#{}", gameName, tagLine);
        return riotAccountAdapter.getPuuid(gameName, tagLine);
    }
}