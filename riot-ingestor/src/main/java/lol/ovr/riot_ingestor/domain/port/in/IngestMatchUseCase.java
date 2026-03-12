package lol.ovr.riot_ingestor.domain.port.in;

public interface IngestMatchUseCase {
    void ingestMatch(String matchId, String puuid);
}
