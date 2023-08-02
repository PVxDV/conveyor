package pvxdv.conveyor.calculators;


import pvxdv.conveyor.dto.local.PreScoringRequestDTO;
import pvxdv.conveyor.dto.local.ScoringRequestDTO;

import java.math.BigDecimal;

public interface MonthlyPaymentCalculator {

    BigDecimal calculateMonthlyPaymentForScoring(ScoringRequestDTO scoringRequestDTO, BigDecimal rate);

    BigDecimal calculateMonthlyPaymentForPreScoring(PreScoringRequestDTO preSoringResponseDTO, Boolean isInsuranceEnabled,
                                                              Boolean isSalaryClient, BigDecimal rate);


}
