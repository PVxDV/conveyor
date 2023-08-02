package pvxdv.conveyor.calculators;


import pvxdv.conveyor.dto.PreScoringRequestDTO;
import pvxdv.conveyor.dto.ScoringRequestDTO;

import java.math.BigDecimal;

public interface MonthlyPaymentCalculator {

    BigDecimal calculateMonthlyPaymentForScoring(ScoringRequestDTO scoringRequestDTO, BigDecimal rate);

    BigDecimal calculateMonthlyPaymentForPreScoring(PreScoringRequestDTO preSoringResponseDTO, Boolean isInsuranceEnabled,
                                                              Boolean isSalaryClient, BigDecimal rate);


}
