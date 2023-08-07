package pvxdv.conveyor.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.ScoringDataDTO;
import pvxdv.conveyor.dto.PreScoringDTO;
import pvxdv.conveyor.dto.ScoringClientDTO;
import pvxdv.conveyor.dto.ScoringDTO;

@Component
@Slf4j
public class Mapper {
    public PreScoringDTO loanApplicationRequestDTOToPreScoringDTO(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("{} making localDTO with loanApplicationRequestDTOToPreScoringRequestDTO() method", this.getClass().getSimpleName());
        return new PreScoringDTO(loanApplicationRequestDTO.getAmount(), loanApplicationRequestDTO.getTerm());
    }

    public ScoringClientDTO scoringDataDTOToScoringClientDTO(ScoringDataDTO scoringDataDTO) {
        log.info("{} making localDTO with scoringDataDTOToScoringClientDTO method", this.getClass().getSimpleName());
        return new ScoringClientDTO(scoringDataDTO.getAmount(), scoringDataDTO.getGender(), scoringDataDTO.getBirthdate(),
                scoringDataDTO.getMaritalStatus(), scoringDataDTO.getDependentAmount(), scoringDataDTO.getEmployment().getEmploymentStatus(),
                scoringDataDTO.getEmployment().getSalary(), scoringDataDTO.getEmployment().getPosition(),
                scoringDataDTO.getEmployment().getWorkExperienceTotal(), scoringDataDTO.getEmployment().getWorkExperienceCurrent(),
                scoringDataDTO.getIsInsuranceEnabled(), scoringDataDTO.getIsSalaryClient());
    }

    public ScoringDTO scoringDataDTOToScoringDTO(ScoringDataDTO scoringDataDTO) {
        log.info("{} making localDTO with scoringDataDTOToScoringRequestDTO method", this.getClass().getSimpleName());
        return new ScoringDTO(scoringDataDTO.getAmount(), scoringDataDTO.getTerm(),
                scoringDataDTO.getIsInsuranceEnabled(), scoringDataDTO.getIsSalaryClient());
    }

}
