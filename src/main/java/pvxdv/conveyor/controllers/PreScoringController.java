package pvxdv.conveyor.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;
import pvxdv.conveyor.services.PreScoringService;


import java.util.List;

//@Tag(name = "Прескоринг для 4х кредитных предложений")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/conveyor")
public class PreScoringController {
    private final PreScoringService preScoringService;

    @PostMapping("/offers/{applicationId}")
    public ResponseEntity<List<LoanOfferDTO>> getOffers(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO,
                                                        @PathVariable Long applicationId) {
        log.info("{} start PreScoring for applicationId:{}", this.getClass().getSimpleName(), applicationId);
        return ResponseEntity.status(HttpStatus.OK).body(preScoringService.generateAvailableOffers(loanApplicationRequestDTO, applicationId));
    }
}
