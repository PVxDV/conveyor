package pvxdv.conveyor.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.ScoringDataDTO;
import pvxdv.conveyor.dto.PreScoringRequestDTO;
import pvxdv.conveyor.dto.ScoringClientDTO;
import pvxdv.conveyor.dto.ScoringRequestDTO;

@Component
@Slf4j
public class Mapper {
    public PreScoringRequestDTO loanApplicationRequestDTOToPreScoringRequestDTO(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("{} making localDTO with loanApplicationRequestDTOToPreScoringRequestDTO() method", this.getClass().getSimpleName());
        return new PreScoringRequestDTO(loanApplicationRequestDTO.getAmount(), loanApplicationRequestDTO.getTerm());
    }

    public ScoringClientDTO scoringDataDTOToScoringClientDTO(ScoringDataDTO scoringDataDTO) {
        log.info("{} making localDTO with scoringDataDTOToScoringClientDTO method", this.getClass().getSimpleName());
        return new ScoringClientDTO(scoringDataDTO.getAmount(), scoringDataDTO.getGender(), scoringDataDTO.getBirthdate(),
                scoringDataDTO.getMaritalStatus(), scoringDataDTO.getDependentAmount(), scoringDataDTO.getEmployment().getEmploymentStatus(),
                scoringDataDTO.getEmployment().getSalary(), scoringDataDTO.getEmployment().getPosition(),
                scoringDataDTO.getEmployment().getWorkExperienceTotal(), scoringDataDTO.getEmployment().getWorkExperienceCurrent(),
                scoringDataDTO.getIsInsuranceEnabled(), scoringDataDTO.getIsSalaryClient());
    }

    public ScoringRequestDTO scoringDataDTOToScoringRequestDTO(ScoringDataDTO scoringDataDTO) {
        log.info("{} making localDTO with scoringDataDTOToScoringRequestDTO method", this.getClass().getSimpleName());
        return new ScoringRequestDTO(scoringDataDTO.getAmount(), scoringDataDTO.getTerm(),
                scoringDataDTO.getIsInsuranceEnabled(), scoringDataDTO.getIsSalaryClient());
    }

}
