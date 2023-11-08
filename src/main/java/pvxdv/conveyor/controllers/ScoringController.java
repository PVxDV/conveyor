package pvxdv.conveyor.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pvxdv.conveyor.dto.CreditDTO;
import pvxdv.conveyor.dto.ScoringDataDTO;
import pvxdv.conveyor.services.ScoringService;


@Tag(name = "ScoringController")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/conveyor")
public class ScoringController {
    private final ScoringService scoringService;

    @PostMapping("/calculation/")
    public ResponseEntity<CreditDTO> getScoring(@Valid @RequestBody ScoringDataDTO scoringDataDTO) {
        log.info("{} starting scoring for client:{}", this.getClass().getSimpleName(), scoringDataDTO.getAccount());
        return ResponseEntity.ok(scoringService.calculateScoring(scoringDataDTO));
    }
}
