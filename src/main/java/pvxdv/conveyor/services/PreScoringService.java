package pvxdv.conveyor.services;


import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;

import java.util.List;

public interface PreScoringService {
    List<LoanOfferDTO> generateAvailableOffers(LoanApplicationRequestDTO loanApplicationRequestDTO, Long applicationId);
}
