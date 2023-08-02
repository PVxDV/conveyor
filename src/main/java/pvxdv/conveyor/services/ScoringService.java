package pvxdv.conveyor.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pvxdv.conveyor.calculators.MonthlyPaymentCalculator;
import pvxdv.conveyor.calculators.PskCalculator;
import pvxdv.conveyor.calculators.RateCalculator;
import pvxdv.conveyor.dto.CreditDTO;
import pvxdv.conveyor.dto.PaymentScheduleElement;
import pvxdv.conveyor.dto.ScoringDataDTO;
import pvxdv.conveyor.dto.local.ScoringClientDTO;
import pvxdv.conveyor.dto.local.ScoringRequestDTO;
import pvxdv.conveyor.mapper.Mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ScoringService {
    private final MonthlyPaymentCalculator monthlyPaymentCalculator;
    private final PskCalculator pskCalculator;
    private final RateCalculator rateCalculator;
    private final Mapper mapper;

    public CreditDTO calculateScoring(ScoringDataDTO scoringDataDTO, Long clientId) {
        log.info("Starting calculateScoring() for clientId:{}", clientId);

        ScoringRequestDTO scoringRequestDTO = mapper.scoringDataDTOToScoringRequestDTO(scoringDataDTO);
        ScoringClientDTO clientDTO = mapper.scoringDataDTOToScoringClientDTO(scoringDataDTO);

        BigDecimal finalLoanRate = rateCalculator.calculateRateForScoring(clientDTO);
        BigDecimal monthlyPayment = monthlyPaymentCalculator.calculateMonthlyPaymentForScoring(scoringRequestDTO, finalLoanRate);
        BigDecimal psk = pskCalculator.calculatePsk(monthlyPayment, scoringRequestDTO.getAmount(), scoringDataDTO.getTerm());

        List<PaymentScheduleElement> paymentScheduleElementList = generatePaymentScheduleList(scoringRequestDTO.getTerm(),
                monthlyPayment, scoringRequestDTO.getAmount(), finalLoanRate);

        return new CreditDTO(scoringRequestDTO.getAmount(), scoringRequestDTO.getTerm(), monthlyPayment, finalLoanRate,
                psk, scoringRequestDTO.getIsInsuranceEnabled(), scoringRequestDTO.getIsSalaryClient(), paymentScheduleElementList);
    }

    private List<PaymentScheduleElement> generatePaymentScheduleList(Integer temp, BigDecimal monthlyPayment,
                                                                     BigDecimal amount, BigDecimal rate) {
        BigDecimal rateCalculated = rate.divide(BigDecimal.valueOf(100), 2, RoundingMode.CEILING);
        BigDecimal remainingDebt = amount;
        LocalDate date = LocalDate.now();

        List<PaymentScheduleElement> result = new ArrayList<>();

        for (int i = 1; i < temp + 1; i++) {
            LocalDate paymentDate = date.plusMonths(i);

            BigDecimal interestPay = remainingDebt.multiply(rateCalculated).multiply(BigDecimal.valueOf(paymentDate.lengthOfMonth()).
                    divide(BigDecimal.valueOf(paymentDate.lengthOfYear()), 2, RoundingMode.CEILING));

            BigDecimal debtPay = monthlyPayment.subtract(interestPay);

            remainingDebt = remainingDebt.subtract(debtPay);

            log.info("generatePaymentScheduleList record:{}", i);
            result.add(new PaymentScheduleElement(i, paymentDate, monthlyPayment, interestPay, debtPay, remainingDebt));
        }
        log.info("generatePaymentScheduleList");
        return result;
    }
}
