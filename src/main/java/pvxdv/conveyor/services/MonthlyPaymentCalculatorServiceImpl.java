package pvxdv.conveyor.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import pvxdv.conveyor.localDto.PreScoringRequestDTO;
import pvxdv.conveyor.localDto.PreSoringResponseDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class MonthlyPaymentCalculatorServiceImpl implements MonthlyPaymentCalculatorService {

    private BigDecimal insuranceCost = BigDecimal.valueOf(5000);
    private Integer baseRate = 15;

    @Override
    public BigDecimal calculateMonthlyPaymentForScoring(BigDecimal amount, Integer term, Integer rate, Boolean isInsuranceEnabled,
                                                        Boolean isSalaryClient) {
        log.info("calculateMonthlyPaymentForScoring()");
        return null;
    }

    @Override
    public PreSoringResponseDTO calculateMonthlyPaymentForPreScoring(PreScoringRequestDTO preScoringRequestDTO, Boolean isInsuranceEnabled,
                                                                     Boolean isSalaryClient) {
        log.info("calculateMonthlyPaymentForPreScoring()");
        Integer creditRate = baseRate;
        Integer term = preScoringRequestDTO.getTerm();
        BigDecimal amount = preScoringRequestDTO.getAmount();

        BigDecimal monthlyPayment;
        BigDecimal totalAmount;

        if (isInsuranceEnabled) {
            if (isSalaryClient) {
                creditRate -= 4;
            } else {
                creditRate -= 3;
            }
            monthlyPayment = calculateMonthlyPayment(insuranceCost, calculateAnnuityRatio(term, creditRate), amount);

        } else {
            if (!isSalaryClient) {
                creditRate += 3;
            }
            monthlyPayment = calculateMonthlyPayment(calculateAnnuityRatio(term, creditRate), amount);
        }

        totalAmount = calculateTotalAmount(monthlyPayment, term);

        log.info("The calculation is finished");
        return new PreSoringResponseDTO(amount, totalAmount , term, monthlyPayment, BigDecimal.valueOf(creditRate));
    }

    private BigDecimal calculateAnnuityRatio(Integer term, Integer rate) {
        log.info("calculateAnnuityRatio()");
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(rate).
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
        if(amount.doubleValue() > 50000) {
            insurance = amount.multiply(BigDecimal.valueOf(0.1));
        }
        return insurance;
    }

}
