package pvxdv.conveyor.controllers;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pvxdv.conveyor.dto.CreditDTO;
import pvxdv.conveyor.dto.ScoringDataDTO;
import pvxdv.conveyor.services.ScoringService;

//@Tag(name = "Скоринг данных, высчитывание ставки(rate), полной стоимости кредита(psk), " +
//        "размер ежемесячного платежа(monthlyPayment), " +
//        "график ежемесячных платежей (List<PaymentScheduleElement>)")

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/conveyor")
public class ScoringController {
    private final ScoringService scoringService;

//        @Operation(summary = "Get a product by id", description = "Returns a product as per the id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
//            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
//    })
    @PostMapping("/calculation/{clientId}")
    public ResponseEntity<CreditDTO> getScoring(@Valid @RequestBody ScoringDataDTO scoringDataDTO, @PathVariable Long clientId) {
        log.info("{} starting scoring for applicationId:{}", this.getClass().getSimpleName(), clientId);
        return ResponseEntity.status(HttpStatus.OK).body(scoringService.calculateScoring(scoringDataDTO, clientId));
    }
}
