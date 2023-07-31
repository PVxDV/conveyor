package pvxdv.conveyor.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;
import pvxdv.conveyor.localDto.PreScoringRequestDTO;
import pvxdv.conveyor.localDto.PreSoringResponseDTO;

@Component
@Slf4j
public class Mapper {
    public PreScoringRequestDTO loanApplicationRequestDTOToPreScoringRequestDTO(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        return new PreScoringRequestDTO(loanApplicationRequestDTO.getAmount(), loanApplicationRequestDTO.getTerm());
    }

}
