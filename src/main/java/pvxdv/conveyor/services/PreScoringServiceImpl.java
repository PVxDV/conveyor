package pvxdv.conveyor.services;

import io.swagger.v3.oas.annotations.tags.Tag;
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
    MonthlyPaymentCalculator monthlyPaymentCalculator;
    RateCalculator rateCalculator;
    Mapper mapper;

    @Tag(name = "test")
    public List<LoanOfferDTO> generateAvailableOffers(LoanApplicationRequestDTO loanApplicationRequestDTO, Long applicationId) {
        LocalDate currentDate = LocalDate.now();
        if(Period.between(loanApplicationRequestDTO.getBirthdate(), currentDate).getYears() < 18){
            log.info("The client is under 18 years old, rejection for applicationId:{}", applicationId);
            return null;
        }

        log.info("Starting generateAvailableOffers() for applicationId:{}", applicationId);
        PreScoringDTO preScoringDTO = mapper.loanApplicationRequestDTOToPreScoringDTO(loanApplicationRequestDTO);

        List<LoanOfferDTO> result = new ArrayList<>();
        result.add(generateOffer(preScoringDTO, true, true, applicationId));
        result.add(generateOffer(preScoringDTO, true, false, applicationId));
        result.add(generateOffer(preScoringDTO, false, true, applicationId));
        result.add(generateOffer(preScoringDTO, false, false, applicationId));
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
                isInsuranceEnabled, isSalaryClient, finalLoanRate);

        BigDecimal totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(term));

        return new LoanOfferDTO(applicationId, preScoringDTO.getAmount(), totalAmount, term, monthlyPayment,
                finalLoanRate, isInsuranceEnabled, isSalaryClient);
    }
}
