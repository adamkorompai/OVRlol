package lol.ovr.riot_ingestor.infrastructure.adapter.out.riot;

import lol.ovr.riot_ingestor.domain.model.PlayerMatchPerformance;
import lol.ovr.riot_ingestor.domain.port.out.RiotMatchProvider;
import lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto.RiotMatchDto;
import lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto.RiotParticipantDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
@Primary
public class HttpRiotMatchProvider implements RiotMatchProvider {

    private final RestClient restClient;

    public HttpRiotMatchProvider(
            RestClient.Builder restClientBuilder,
            @Value("${riot.api.key}") String apiKey,
            @Value("${riot.api.base-url}") String baseUrl) {

        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader("X-Riot-Token", apiKey) // Authentification Riot
                .build();
    }

    @Override
    public PlayerMatchPerformance fetchMatchPerformance(String matchId, String puuid) {
        log.info("[RIOT API] Récupération du match {} pour le joueur {}", matchId, puuid);

        RiotMatchDto matchDto = restClient.get()
                .uri("/lol/match/v5/matches/{matchId}", matchId)
                .retrieve()
                .body(RiotMatchDto.class);

        if (matchDto == null || matchDto.info() == null) {
            throw new IllegalArgumentException("Match introuvable ou vide chez Riot");
        }

        RiotParticipantDto participant = matchDto.info().participants().stream()
                .filter(p -> p.puuid().equals(puuid))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Joueur non trouvé dans ce match"));

        return new PlayerMatchPerformance(
                matchId,
                puuid,
                participant.championName(),
                participant.kills(),
                participant.deaths(),
                participant.assists(),
                participant.getTotalCs(),
                participant.visionScore(),
                participant.win(),
                matchDto.info().gameCreation()
        );
    }

    @Override
    public List<String> fetchRecentMatchIds(String puuid, int count) {
        log.info("[RIOT API] Récupération des {} derniers matchs pour {}", count, puuid);

        String[] matchIds = restClient.get()
                .uri("/lol/match/v5/matches/by-puuid/{puuid}/ids?count={count}", puuid, count)
                .retrieve()
                .body(String[].class);

        if (matchIds == null) return List.of();
        return List.of(matchIds);
    }
}
