package pvxdv.conveyor.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;
import pvxdv.conveyor.localDto.PreScoringRequestDTO;
import pvxdv.conveyor.localDto.PreSoringResponseDTO;
import pvxdv.conveyor.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AbleOffersService {

    MonthlyPaymentCalculatorService creditCalculatorService;
    Mapper mapper;

    public AbleOffersService(MonthlyPaymentCalculatorService creditCalculatorService, Mapper mapper) {
        this.creditCalculatorService = creditCalculatorService;
        this.mapper = mapper;
    }

    public List<LoanOfferDTO> generateAvailableOffers(LoanApplicationRequestDTO loanApplicationRequestDTO, Long applicationId) {
        log.info("generateAvailableOffers() for applicationId:{}", applicationId);

        PreScoringRequestDTO preScoringRequestDTO = mapper.loanApplicationRequestDTOToPreScoringRequestDTO(loanApplicationRequestDTO);

        List<LoanOfferDTO> result = new ArrayList<>();
        result.add(generateOffer(preScoringRequestDTO, true, true, applicationId));
        result.add(generateOffer(preScoringRequestDTO, true, false, applicationId));
        result.add(generateOffer(preScoringRequestDTO, false, true, applicationId));
        result.add(generateOffer(preScoringRequestDTO, false, false, applicationId));
        return result;
    }

    private LoanOfferDTO generateOffer(PreScoringRequestDTO preScoringRequestDTO, Boolean isInsuranceEnabled,
                                       Boolean isSalaryClient, Long applicationId) {

        PreSoringResponseDTO preSoringResponseDTO = creditCalculatorService.
                calculateMonthlyPaymentForPreScoring(preScoringRequestDTO, isInsuranceEnabled, isSalaryClient);

        return new LoanOfferDTO(applicationId, preSoringResponseDTO.getRequestedAmount(), preSoringResponseDTO.getTotalAmount(),
                preSoringResponseDTO.getTerm(), preSoringResponseDTO.getMonthlyPayment(), preSoringResponseDTO.getRate(), isInsuranceEnabled, isSalaryClient);

    }
}
