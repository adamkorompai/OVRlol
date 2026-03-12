package lol.ovr.ovr_engine.infrastructure.adapter.out.stub;

import lol.ovr.ovr_engine.domain.model.OvrCard;
import lol.ovr.ovr_engine.domain.port.out.OvrCardPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StubOvrCardPublisher implements OvrCardPublisher {

    @Override
    public void publishCard(OvrCard card) {
        log.info("\n==========================================" +
                        "\n 🎮 CARTE OVR.LOL GÉNÉRÉE !" +
                        "\n==========================================" +
                        "\n Joueur (PUUID) : {}" +
                        "\n Match ID       : {}" +
                        "\n------------------------------------------" +
                        "\n 🌟 NOTE GLOBALE (OVR) : {} / 99" +
                        "\n------------------------------------------" +
                        "\n ⚔️ Mécaniques (KDA)   : {} / 99" +
                        "\n 🌾 Farming (CS)       : {} / 99" +
                        "\n 👁️ Vision             : {} / 99" +
                        "\n==========================================\n",
                card.puuid(),
                card.matchId(),
                card.overallRating(),
                card.mechanicsScore(),
                card.farmingScore(),
                card.visionScore());
    }
}
