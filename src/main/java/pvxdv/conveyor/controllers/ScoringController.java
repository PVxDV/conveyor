package pvxdv.conveyor.controllers;



import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pvxdv.conveyor.dto.CreditDTO;
import pvxdv.conveyor.dto.ScoringDataDTO;
import pvxdv.conveyor.services.ScoringServiceImpl;

@Tag(name = "ScoringController")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/conveyor")
public class ScoringController {
    private final ScoringServiceImpl scoringServiceImpl;
    @PostMapping("/calculation/{clientId}")
    public ResponseEntity<CreditDTO> getScoring(@Valid @RequestBody ScoringDataDTO scoringDataDTO, @PathVariable Long clientId) {
        log.info("{} starting scoring for applicationId:{}", this.getClass().getSimpleName(), clientId);
        return ResponseEntity.status(HttpStatus.OK).body(scoringServiceImpl.calculateScoring(scoringDataDTO, clientId));
    }
}
