package pvxdv.conveyor.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;
import pvxdv.conveyor.service.AbleOffersService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/conveyor")
public class OffersController {
    private final AbleOffersService ableOffersService;
    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDTO>> getOffers(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO){
        log.info("Вызванн метод getOffers");
        /*
        результат работы сервиса в try catch, если try прошел
         return ResponseEntity.status(HttpStatus.OK).body(ableOffersService.getOffers(loanApplicationRequestDTO));

         если не прошел

         return ResponseEntity.status(HttpStatus.*сюда 500*)

         */
        return ResponseEntity.status(HttpStatus.OK).body(ableOffersService.getOffers(loanApplicationRequestDTO));
    }
}
