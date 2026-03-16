package lol.ovr.riot_ingestor.infrastructure.adapter.out.riot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class HttpRiotAccountAdapter {

    private final RestClient restClient;

    public HttpRiotAccountAdapter(
            RestClient.Builder restClientBuilder,
            @Value("${riot.api.key}") String apiKey) {

        this.restClient = restClientBuilder
                .baseUrl("https://europe.api.riotgames.com")
                .defaultHeader("X-Riot-Token", apiKey)
                .build();
    }

    public String getPuuid(String gameName, String tagLine) {
        RiotAccountDto account = restClient.get()
                .uri("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}", gameName, tagLine)
                .retrieve()
                .body(RiotAccountDto.class);

        if (account == null || account.puuid() == null) {
            throw new IllegalArgumentException("Joueur introuvable chez Riot Games");
        }

        return account.puuid();
    }

    public record RiotAccountDto(String puuid, String gameName, String tagLine) {}
}