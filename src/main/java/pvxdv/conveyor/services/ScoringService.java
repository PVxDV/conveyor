package pvxdv.conveyor.services;

import pvxdv.conveyor.dto.CreditDTO;
import pvxdv.conveyor.dto.ScoringDataDTO;

public interface ScoringService {
    CreditDTO calculateScoring(ScoringDataDTO scoringDataDTO, Long clientId);
}
