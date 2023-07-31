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
    private Integer baseRate = 5;

    @Override
    public BigDecimal calculateMonthlyPaymentForScoring(BigDecimal amount, Integer term, Integer rate, Boolean isInsuranceEnabled,
                                                        Boolean isSalaryClient) {
        return null;
    }



    @Override
    public PreSoringResponseDTO calculateMonthlyPaymentForPreScoring(PreScoringRequestDTO preScoringRequestDTO, Boolean isInsuranceEnabled,
                                                                     Boolean isSalaryClient) {
        Integer creditRate = baseRate;
        Integer term = preScoringRequestDTO.getTerm();
        BigDecimal amount = preScoringRequestDTO.getAmount();

        BigDecimal monthlyPayment;

        if (isInsuranceEnabled) {
            if (isSalaryClient) {
                creditRate -= 4;
            } else {
                creditRate -= 3;
            }
            monthlyPayment = calculateMonthlyPayment(insuranceCost, calculateAnnuityRatio(term, creditRate));

        } else {
            if (!isSalaryClient) {
                creditRate += 3;
            }
            monthlyPayment = calculateMonthlyPayment(calculateAnnuityRatio(term, creditRate), amount);
        }

        return new PreSoringResponseDTO(amount, calculateTotalAmount(monthlyPayment, term), term, monthlyPayment, BigDecimal.valueOf(creditRate));
    }

    private BigDecimal calculateAnnuityRatio(Integer term, Integer rate) {
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(rate).
                divide(BigDecimal.valueOf(1200), 6, RoundingMode.CEILING);

        BigDecimal numeratorForCalculation = monthlyInterestRate.
                add(BigDecimal.valueOf(1)).
                pow(term).
                multiply(monthlyInterestRate);

        BigDecimal denominatorForCalculation = BigDecimal.valueOf(1).
                add(monthlyInterestRate).
                pow(rate).
                subtract(BigDecimal.valueOf(1));

        return numeratorForCalculation.divide(denominatorForCalculation, 2, RoundingMode.CEILING);
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal insuranceCost, BigDecimal annuityRatio, BigDecimal amount) {
        return amount.add(insuranceCost).multiply(annuityRatio);
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal annuityRatio, BigDecimal amount) {
        return amount.multiply(annuityRatio);
    }

    private BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term) {
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }

}
