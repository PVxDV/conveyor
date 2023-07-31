package pvxdv.conveyor.services;


import pvxdv.conveyor.localDto.PreScoringRequestDTO;
import pvxdv.conveyor.localDto.PreSoringResponseDTO;

import java.math.BigDecimal;

public interface MonthlyPaymentCalculatorService {

    BigDecimal calculateMonthlyPaymentForScoring(BigDecimal amount, Integer term, Integer rate, Boolean isInsuranceEnabled,
                                                  Boolean isSalaryClient);

    PreSoringResponseDTO calculateMonthlyPaymentForPreScoring(PreScoringRequestDTO preSoringResponseDTO, Boolean isInsuranceEnabled,
                                                              Boolean isSalaryClient);


}
