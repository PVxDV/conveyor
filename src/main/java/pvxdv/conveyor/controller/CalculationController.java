package pvxdv.conveyor.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pvxdv.conveyor.dto.CreditDTO;
import pvxdv.conveyor.dto.ScoringDataDTO;


@RestController
@AllArgsConstructor
@RequestMapping("/conveyor")
public class CalculationController {
    @PostMapping("/calculation")
    public ResponseEntity<CreditDTO> getScoring(@Valid @RequestBody ScoringDataDTO scoringDataDTO){
        return ResponseEntity.status(HttpStatus.OK).body(new CreditDTO());
    }
}
