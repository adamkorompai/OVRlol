package lol.ovr.riot_ingestor.infrastructure.adapter.out.riot;

import lol.ovr.riot_ingestor.domain.model.PlayerMatchPerformance;
import lol.ovr.riot_ingestor.domain.port.out.RiotMatchProvider;
import lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto.RiotMatchDto;
import lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto.RiotParticipantDto;
import lol.ovr.riot_ingestor.infrastructure.adapter.out.riot.mapper.RiotMatchPerformanceMapper;
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
    private final RiotMatchPerformanceMapper mapper;

    public HttpRiotMatchProvider(
            RestClient.Builder restClientBuilder,
            @Value("${riot.api.key}") String apiKey,
            @Value("${riot.api.base-url}") String baseUrl,
            RiotMatchPerformanceMapper mapper) {

        this.mapper = mapper;
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
            .defaultHeader("X-Riot-Token", apiKey)
                .build();
    }

    @Override
    public PlayerMatchPerformance fetchMatchPerformance(String matchId, String puuid) {
        log.info("[RIOT API] Récupération du match {} pour le joueur {}", matchId, puuid);

        RiotMatchDto match = restClient.get()
                .uri("/lol/match/v5/matches/{matchId}", matchId)
                .retrieve()
                .body(RiotMatchDto.class);

        if (match == null || match.info() == null || match.info().participants() == null) {
            throw new IllegalStateException("Invalid Riot API response for matchId=" + matchId);
        }

        return mapper.toDomain(matchId, puuid, match);
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
