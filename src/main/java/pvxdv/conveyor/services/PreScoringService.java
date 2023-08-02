package pvxdv.conveyor.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pvxdv.conveyor.calculators.MonthlyPaymentCalculator;
import pvxdv.conveyor.calculators.RateCalculator;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;
import pvxdv.conveyor.dto.local.PreScoringRequestDTO;
import pvxdv.conveyor.mapper.Mapper;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class PreScoringService {
    MonthlyPaymentCalculator monthlyPaymentCalculator;
    RateCalculator rateCalculator;
    Mapper mapper;

    public List<LoanOfferDTO> generateAvailableOffers(LoanApplicationRequestDTO loanApplicationRequestDTO, Long applicationId) {
        log.info("Starting generateAvailableOffers() for applicationId:{}", applicationId);

        PreScoringRequestDTO preScoringRequestDTO = mapper.loanApplicationRequestDTOToPreScoringRequestDTO(loanApplicationRequestDTO);


        List<LoanOfferDTO> result = new ArrayList<>();
        result.add(generateOffer(preScoringRequestDTO, true, true, applicationId));
        result.add(generateOffer(preScoringRequestDTO, true, false, applicationId));
        result.add(generateOffer(preScoringRequestDTO, false, true, applicationId));
        result.add(generateOffer(preScoringRequestDTO, false, false, applicationId));

        result.sort(Comparator.comparing(LoanOfferDTO::getRate).reversed());

        log.info("Available offers for applicationId:{} are generated", applicationId);
        return result;
    }

    private LoanOfferDTO generateOffer(PreScoringRequestDTO preScoringRequestDTO,
                                       Boolean isInsuranceEnabled,
                                       Boolean isSalaryClient, Long applicationId) {
        log.info("Starting generation and calculation with generateOffer() for applicationId:{}", applicationId);

        Integer term = preScoringRequestDTO.getTerm();

        BigDecimal finalLoanRate = rateCalculator.calculateRateForPreScoring(isInsuranceEnabled, isSalaryClient);
        BigDecimal monthlyPayment = monthlyPaymentCalculator.calculateMonthlyPaymentForPreScoring(preScoringRequestDTO,
                isInsuranceEnabled, isSalaryClient, finalLoanRate);

        BigDecimal totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(term));

        return new LoanOfferDTO(applicationId, preScoringRequestDTO.getAmount(), totalAmount, term, monthlyPayment,
                finalLoanRate, isInsuranceEnabled, isSalaryClient);

    }
}
