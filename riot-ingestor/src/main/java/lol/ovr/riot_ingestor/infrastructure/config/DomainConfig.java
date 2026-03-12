package lol.ovr.riot_ingestor.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lol.ovr.riot_ingestor.domain.port.in.IngestMatchUseCase;
import lol.ovr.riot_ingestor.domain.port.in.IngestRecentMatchesUseCase;
import lol.ovr.riot_ingestor.domain.port.out.MatchPerformancePublisher;
import lol.ovr.riot_ingestor.domain.port.out.RiotMatchProvider;
import lol.ovr.riot_ingestor.domain.service.MatchIngestionService;

@Configuration
public class DomainConfig {

    @Bean
    public MatchIngestionService matchIngestionService(
            RiotMatchProvider riotProvider,
            MatchPerformancePublisher publisher) {
        return new MatchIngestionService(riotProvider, publisher);
    }

    @Bean
    public IngestMatchUseCase ingestMatchUseCase(@Qualifier("matchIngestionService") MatchIngestionService service) {
        return service;
    }

    @Bean
    public IngestRecentMatchesUseCase ingestRecentMatchesUseCase(@Qualifier("matchIngestionService") MatchIngestionService service) {
        return service;
    }
}
