package lol.ovr.ovr_engine.domain.port.out;

import lol.ovr.ovr_engine.domain.model.OvrCard;

public interface OvrCardPublisher {
    void publishCard(OvrCard card);
}
