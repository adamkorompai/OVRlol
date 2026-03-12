package lol.ovr.ovr_engine.infrastructure.config;

import lol.ovr.ovr_engine.domain.port.in.GenerateOvrCardUseCase;
import lol.ovr.ovr_engine.domain.port.out.OvrCardPublisher;
import lol.ovr.ovr_engine.domain.service.LoLOvrCalculator;
import lol.ovr.ovr_engine.domain.service.OvrGenerationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public LoLOvrCalculator lolOvrCalculator() {
        return new LoLOvrCalculator();
    }

    @Bean
    public GenerateOvrCardUseCase generateOvrCardUseCase(LoLOvrCalculator calculator, OvrCardPublisher publisher) {
        return new OvrGenerationService(calculator, publisher);
    }
}
