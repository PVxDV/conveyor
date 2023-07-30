package pvxdv.conveyor.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pvxdv.conveyor.model.CreditDTO;
import pvxdv.conveyor.model.ScoringDataDTO;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/conveyor")
public class OffersController {
    @PostMapping("/calculation")
    public ResponseEntity<CreditDTO> getScoring(ScoringDataDTO scoringDataDTO){
        return ResponseEntity.status(HttpStatus.OK).body(new CreditDTO());
    }
}
