package pvxdv.conveyor.calculators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import pvxdv.conveyor.localDto.PreScoringRequestDTO;
import pvxdv.conveyor.localDto.PreSoringResponseDTO;
import pvxdv.conveyor.localDto.ScoringRequestDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Slf4j
public class MonthlyPaymentCalculatorImpl implements MonthlyPaymentCalculator {
    private final BigDecimal insuranceCost = BigDecimal.valueOf(5000);
    private final BigDecimal baseRate = BigDecimal.valueOf(15);

    @Override
    public BigDecimal calculateMonthlyPaymentForScoring(ScoringRequestDTO scoringRequestDTO, BigDecimal rate) {
        log.info("calculateMonthlyPaymentForScoring()");

        Integer term = scoringRequestDTO.getTerm();
        BigDecimal amount = scoringRequestDTO.getAmount();
        BigDecimal creditRate = rate;

        if (scoringRequestDTO.getIsInsuranceEnabled()) {
            if (scoringRequestDTO.getIsSalaryClient()) {
                creditRate = creditRate.subtract(BigDecimal.valueOf(4));
            } else {
                creditRate = creditRate.subtract(BigDecimal.valueOf(3));
            }
            return calculateMonthlyPayment(insuranceCost, calculateAnnuityRatio(term, creditRate), amount);

        } else {
            if (!scoringRequestDTO.getIsSalaryClient()) {
                creditRate = creditRate.add(BigDecimal.valueOf(3));
            }
            return calculateMonthlyPayment(calculateAnnuityRatio(term, creditRate), amount);
        }
    }

    @Override
    public PreSoringResponseDTO calculateMonthlyPaymentForPreScoring(PreScoringRequestDTO preScoringRequestDTO, Boolean isInsuranceEnabled,
                                                                     Boolean isSalaryClient) {
        log.info("calculateMonthlyPaymentForPreScoring()");
        BigDecimal creditRate = baseRate;
        Integer term = preScoringRequestDTO.getTerm();
        BigDecimal amount = preScoringRequestDTO.getAmount();

        BigDecimal monthlyPayment;
        BigDecimal totalAmount;

        if (isInsuranceEnabled) {
            if (isSalaryClient) {
                creditRate = creditRate.subtract(BigDecimal.valueOf(4));
            } else {
                creditRate = creditRate.subtract(BigDecimal.valueOf(3));
            }
            monthlyPayment = calculateMonthlyPayment(insuranceCost, calculateAnnuityRatio(term, creditRate), amount);

        } else {
            if (!isSalaryClient) {
                creditRate = creditRate.add(BigDecimal.valueOf(3));
            }
            monthlyPayment = calculateMonthlyPayment(calculateAnnuityRatio(term, creditRate), amount);
        }

        totalAmount = calculateTotalAmount(monthlyPayment, term);

        log.info("The calculation is finished");
        return new PreSoringResponseDTO(amount, totalAmount, term, monthlyPayment, creditRate);
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
        BigDecimal amountWithInsurance = amount.add(calculateInsurance(insuranceCost, amount));
        log.info("calculateMonthlyPayment_with_insurance()");
        return amountWithInsurance.multiply(annuityRatio).setScale(2, RoundingMode.CEILING);
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal annuityRatio, BigDecimal amount) {
        log.info("calculateMonthlyPayment_without_insurance()");
        return amount.multiply(annuityRatio).setScale(2, RoundingMode.CEILING);
    }

    private BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term) {
        log.info("calculateTotalAmount()");
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }

    private BigDecimal calculateInsurance(BigDecimal insuranceCost, BigDecimal amount) {
        log.info("calculateInsurance()");
        BigDecimal insurance = insuranceCost;
        if (amount.doubleValue() > 50000) {
            insurance = amount.multiply(BigDecimal.valueOf(0.1));
        }
        return insurance;
    }
}
