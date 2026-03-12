package lol.ovr.riot_ingestor.infrastructure.adapter.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lol.ovr.riot_ingestor.domain.port.in.IngestMatchUseCase;
import lol.ovr.riot_ingestor.domain.port.in.IngestRecentMatchesUseCase;
import lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto.IngestRecentRequest;
import lol.ovr.riot_ingestor.infrastructure.adapter.in.web.dto.IngestRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ingest")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Validated
public class MatchIngestionController {

    private final IngestMatchUseCase ingestMatchUseCase;
    private final IngestRecentMatchesUseCase ingestRecentMatchesUseCase;


    @PostMapping
    public ResponseEntity<Void> ingestMatch(@Valid @RequestBody IngestRequest request) {
        ingestMatchUseCase.ingestMatch(request.matchId(), request.puuid());

        return ResponseEntity.accepted().build();
    }

    @PostMapping("/recent")
    public ResponseEntity<Void> ingestRecentMatches(@Valid @RequestBody IngestRecentRequest request) {
        ingestRecentMatchesUseCase.ingestRecentMatches(request.puuid(), request.count());
        return ResponseEntity.accepted().build();
    }
}
