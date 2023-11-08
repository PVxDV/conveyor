package pvxdv.conveyor.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pvxdv.conveyor.calculators.MonthlyPaymentCalculator;
import pvxdv.conveyor.calculators.RateCalculator;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;
import pvxdv.conveyor.dto.PreScoringDTO;
import pvxdv.conveyor.mapper.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class PreScoringServiceImpl implements PreScoringService {
    private final MonthlyPaymentCalculator monthlyPaymentCalculator;
    private final RateCalculator rateCalculator;
    private final  Mapper mapper;

    public List<LoanOfferDTO> generateAvailableOffers(LoanApplicationRequestDTO loanApplicationRequestDTO, Long applicationId) {
        LocalDate currentDate = LocalDate.now();
        if(Period.between(loanApplicationRequestDTO.getBirthdate(), currentDate).getYears() < 18){
            log.info("The client is under 18 years old, rejection for applicationId:{}", applicationId);
            return null;
        }

        log.info("Starting generateAvailableOffers() for applicationId:{}", applicationId);
        PreScoringDTO preScoringDTO = mapper.loanApplicationRequestDTOToPreScoringDTO(loanApplicationRequestDTO);

        List<LoanOfferDTO> result = new ArrayList<>(List.of(
                generateOffer(preScoringDTO, true, true, applicationId),
                generateOffer(preScoringDTO, true, false, applicationId),
                generateOffer(preScoringDTO, false, true, applicationId),
                generateOffer(preScoringDTO, false, false, applicationId)));

        result.sort(Comparator.comparing(LoanOfferDTO::getRate).reversed());

        log.info("Available offers for applicationId:{} are generated", applicationId);
        return result;
    }

    private LoanOfferDTO generateOffer(PreScoringDTO preScoringDTO, Boolean isInsuranceEnabled,
                                       Boolean isSalaryClient, Long applicationId) {
        log.info("Generate parameterized offer for applicationId:{}", applicationId);
        Integer term = preScoringDTO.getTerm();

        BigDecimal finalLoanRate = rateCalculator.calculateRateForPreScoring(isInsuranceEnabled, isSalaryClient);
        BigDecimal monthlyPayment = monthlyPaymentCalculator.calculateMonthlyPaymentForPreScoring(preScoringDTO,
                isInsuranceEnabled, finalLoanRate);

        BigDecimal totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(term));

        return new LoanOfferDTO(applicationId, preScoringDTO.getAmount(), totalAmount, term, monthlyPayment,
                finalLoanRate, isInsuranceEnabled, isSalaryClient);
    }
}
