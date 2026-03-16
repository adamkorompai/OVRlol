package lol.ovr.ovr_engine.domain.port.in;

import java.util.List;

public interface GenerateOvrCardUseCase {
    void processMatchStats(
            String matchId,
            String puuid,
            String championName,
            int kills,
            int deaths,
            int assists,
            int cs,
            int vision,
            boolean win,
            long gameCreation,
            long gameDuration,
            List<Integer> itemIds,
            List<Integer> primaryRuneIds,
            List<Integer> secondaryRuneIds,
            List<String> enemyChampions
    );
}
