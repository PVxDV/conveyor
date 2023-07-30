package pvxdv.conveyor.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pvxdv.conveyor.model.LoanApplicationRequestDTO;
import pvxdv.conveyor.model.LoanOfferDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/conveyor")
public class OffersController {
    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDTO>> getOffers(LoanApplicationRequestDTO loanApplicationRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<LoanOfferDTO>());
    }
}
