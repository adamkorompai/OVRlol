package lol.ovr.riot_ingestor.domain.service;

import lol.ovr.riot_ingestor.domain.model.PlayerMatchPerformance;
import lol.ovr.riot_ingestor.domain.port.in.IngestMatchUseCase;
import lol.ovr.riot_ingestor.domain.port.in.IngestRecentMatchesUseCase;
import lol.ovr.riot_ingestor.domain.port.out.MatchPerformancePublisher;
import lol.ovr.riot_ingestor.domain.port.out.RiotMatchProvider;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class MatchIngestionService implements IngestMatchUseCase, IngestRecentMatchesUseCase {

    private final RiotMatchProvider riotProvider;
    private final MatchPerformancePublisher eventPublisher;

    public MatchIngestionService(RiotMatchProvider riotProvider, MatchPerformancePublisher eventPublisher) {
        this.riotProvider = riotProvider;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void ingestMatch(String matchId, String puuid) {
        PlayerMatchPerformance performance = riotProvider.fetchMatchPerformance(matchId, puuid);

        eventPublisher.publish(performance);
    }

    @Override
    public void ingestRecentMatches(String puuid, int count) {
        List<String> recentMatchesIds = riotProvider.fetchRecentMatchIds(puuid, count);

        recentMatchesIds.forEach(matchId -> ingestMatch(matchId, puuid));
    }
}
