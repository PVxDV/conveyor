package pvxdv.conveyor.calculators;


import pvxdv.conveyor.dto.PreScoringDTO;
import pvxdv.conveyor.dto.ScoringDTO;

import java.math.BigDecimal;

public interface MonthlyPaymentCalculator {

    BigDecimal calculateMonthlyPaymentForScoring(ScoringDTO scoringDTO, BigDecimal rate);

    BigDecimal calculateMonthlyPaymentForPreScoring(PreScoringDTO preSoringResponseDTO, Boolean isInsuranceEnabled,
                                                    BigDecimal rate);


}
