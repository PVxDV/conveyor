package pvxdv.conveyor.calculators;

import pvxdv.conveyor.localDto.ClientDTO;
import pvxdv.conveyor.localDto.PreScoringRequestDTO;

import java.math.BigDecimal;

public interface RateCalculator {
    BigDecimal calculateRateForScoring(ClientDTO clientDTO, BigDecimal rate);
    BigDecimal calculateRateForPreScoring(PreScoringRequestDTO preScoringRequestDTO, BigDecimal rate);
}
