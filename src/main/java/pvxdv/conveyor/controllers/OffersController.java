package pvxdv.conveyor.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;
import pvxdv.conveyor.services.AbleOffersService;
import pvxdv.conveyor.services.MonthlyPaymentCalculatorService;


import java.util.List;

@PropertySource("classpath:calculation.properties")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/conveyor")
public class OffersController {
    private final MonthlyPaymentCalculatorService creditCalculatorService;
    private final AbleOffersService ableOffersService;

    @PostMapping("/offers/{applicationId}")
    public ResponseEntity<List<LoanOfferDTO>> getOffers(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO,
                                                        @PathVariable Long applicationId) {
        log.info("getOffers() for applicationId:{}", applicationId);
        return ResponseEntity.status(HttpStatus.OK).body(ableOffersService.generateAvailableOffers(loanApplicationRequestDTO, applicationId));
    }
}
