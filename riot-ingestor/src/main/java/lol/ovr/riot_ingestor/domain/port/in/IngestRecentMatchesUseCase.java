package lol.ovr.riot_ingestor.domain.port.in;

public interface IngestRecentMatchesUseCase {
    void ingestRecentMatches(String puuid, int count);
}
