package pvxdv.conveyor.calculators;

import pvxdv.conveyor.dto.ScoringClientDTO;

import java.math.BigDecimal;

public interface RateCalculator {
    BigDecimal calculateRateForScoring(ScoringClientDTO clientDTO);
    BigDecimal calculateRateForPreScoring(Boolean isInsuranceEnabled, Boolean isSalaryClient);
}
