package lol.ovr.ovr_engine.domain.port.in;

public interface GenerateOvrCardUseCase {
    void processMatchStats(String matchId, String puuid, int kills, int deaths, int assists, int cs, int vision, boolean win, long gameCreation);
}
