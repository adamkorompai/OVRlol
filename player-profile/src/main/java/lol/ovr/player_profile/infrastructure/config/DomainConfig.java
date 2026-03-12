package lol.ovr.player_profile.infrastructure.config;

import lol.ovr.player_profile.domain.port.in.SaveCardUseCase;
import lol.ovr.player_profile.domain.port.out.PlayerCardPort;
import lol.ovr.player_profile.domain.service.PlayerProfileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public PlayerProfileService playerProfileService(PlayerCardPort playerCardPort) {
        return new PlayerProfileService(playerCardPort);
    }
}
