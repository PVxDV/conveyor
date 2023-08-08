package pvxdv.conveyor.calculators;

import org.springframework.beans.factory.annotation.Value;
import pvxdv.conveyor.dto.ScoringClientDTO;

import java.math.BigDecimal;

public interface RateCalculator {
    BigDecimal calculateRateForScoring(ScoringClientDTO clientDTO);
    BigDecimal calculateRateForPreScoring(Boolean isInsuranceEnabled, Boolean isSalaryClient);
}
