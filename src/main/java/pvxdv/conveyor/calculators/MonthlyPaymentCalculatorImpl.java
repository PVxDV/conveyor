package pvxdv.conveyor.calculators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import pvxdv.conveyor.dto.PreScoringRequestDTO;
import pvxdv.conveyor.dto.ScoringRequestDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Slf4j
public class MonthlyPaymentCalculatorImpl implements MonthlyPaymentCalculator {

    private final BigDecimal insuranceCost = BigDecimal.valueOf(5000);
    @Override
    public BigDecimal calculateMonthlyPaymentForScoring(ScoringRequestDTO scoringRequestDTO, BigDecimal rate) {
        log.info("calculateMonthlyPaymentForScoring()");

        Integer term = scoringRequestDTO.getTerm();
        BigDecimal amount = scoringRequestDTO.getAmount();

        if (scoringRequestDTO.getIsInsuranceEnabled()) {
            return calculateMonthlyPayment(calculateInsurance(insuranceCost), calculateAnnuityRatio(term, rate), amount);
        } else {
            return calculateMonthlyPayment(calculateAnnuityRatio(term, rate), amount);
        }
    }

    @Override
    public BigDecimal calculateMonthlyPaymentForPreScoring(PreScoringRequestDTO preScoringRequestDTO, Boolean isInsuranceEnabled,
                                                           Boolean isSalaryClient, BigDecimal rate) {
        log.info("calculateMonthlyPaymentForPreScoring()");

        Integer term = preScoringRequestDTO.getTerm();
        BigDecimal amount = preScoringRequestDTO.getAmount();

        if (isInsuranceEnabled) {
            return calculateMonthlyPayment(calculateInsurance(insuranceCost), calculateAnnuityRatio(term, rate), amount);
        } else {
            return calculateMonthlyPayment(calculateAnnuityRatio(term, rate), amount);
        }
    }

    private BigDecimal calculateAnnuityRatio(Integer term, BigDecimal rate) {
        log.info("calculateAnnuityRatio()");
        BigDecimal monthlyInterestRate = rate.
                divide(BigDecimal.valueOf(1200), 10, RoundingMode.CEILING);

        BigDecimal monthlyInterestRatePlusOneAndPow = BigDecimal.valueOf(1).
                add(monthlyInterestRate).
                pow(term);

        BigDecimal numeratorForCalculation = monthlyInterestRatePlusOneAndPow.
                multiply(monthlyInterestRate);

        BigDecimal denominatorForCalculation = monthlyInterestRatePlusOneAndPow.
                subtract(BigDecimal.valueOf(1));

        return numeratorForCalculation.divide(denominatorForCalculation, 20, RoundingMode.CEILING);
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal insuranceCost, BigDecimal annuityRatio, BigDecimal amount) {
        BigDecimal amountWithInsurance = amount.add(insuranceCost);
        log.info("calculateMonthlyPayment_with_insurance()");
        return amountWithInsurance.multiply(annuityRatio).setScale(2, RoundingMode.CEILING);
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal annuityRatio, BigDecimal amount) {
        log.info("calculateMonthlyPayment_without_insurance()");
        return amount.multiply(annuityRatio).setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal calculateInsurance(BigDecimal amount) {
        log.info("calculateInsurance()");

        if (amount.doubleValue() > 50000) {
            return  amount.multiply(BigDecimal.valueOf(0.1));
        }
        return insuranceCost;
    }
}
