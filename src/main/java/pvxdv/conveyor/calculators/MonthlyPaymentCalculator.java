package pvxdv.conveyor.calculators;


import pvxdv.conveyor.localDto.PreScoringRequestDTO;
import pvxdv.conveyor.localDto.PreSoringResponseDTO;
import pvxdv.conveyor.localDto.ScoringRequestDTO;

import java.math.BigDecimal;

public interface MonthlyPaymentCalculator {

    BigDecimal calculateMonthlyPaymentForScoring(ScoringRequestDTO scoringRequestDTO, BigDecimal rate);

    PreSoringResponseDTO calculateMonthlyPaymentForPreScoring(PreScoringRequestDTO preSoringResponseDTO, Boolean isInsuranceEnabled,
                                                              Boolean isSalaryClient);


}
