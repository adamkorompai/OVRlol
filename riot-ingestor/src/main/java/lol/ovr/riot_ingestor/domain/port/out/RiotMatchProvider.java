package lol.ovr.riot_ingestor.domain.port.out;

import lol.ovr.riot_ingestor.domain.model.PlayerMatchPerformance;

import java.util.List;

public interface RiotMatchProvider {
    PlayerMatchPerformance fetchMatchPerformance(String matchId, String puuid);
    List<String> fetchRecentMatchIds(String puuid, int count);
}
