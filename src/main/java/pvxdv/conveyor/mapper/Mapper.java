package pvxdv.conveyor.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pvxdv.conveyor.dto.CreditDTO;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.ScoringDataDTO;
import pvxdv.conveyor.localDto.*;

@Component
@Slf4j
public class Mapper {
    public PreScoringRequestDTO loanApplicationRequestDTOToPreScoringRequestDTO(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("{} making localDTO with loanApplicationRequestDTOToPreScoringRequestDTO() method", this.getClass().getSimpleName());
        return new PreScoringRequestDTO(loanApplicationRequestDTO.getAmount(), loanApplicationRequestDTO.getTerm());
    }

    public ClientDTO scoringDataDTOToClientDTO(ScoringDataDTO scoringDataDTO) {
        log.info("{} making localDTO with scoringDataDTOToClientDTO method", this.getClass().getSimpleName());
        return new ClientDTO(scoringDataDTO.getAmount(), scoringDataDTO.getGender(), scoringDataDTO.getBirthdate(),
                scoringDataDTO.getMaritalStatus(), scoringDataDTO.getDependentAmount(), scoringDataDTO.getEmployment().getEmploymentStatus(),
                scoringDataDTO.getEmployment().getSalary(), scoringDataDTO.getEmployment().getPosition(),
                scoringDataDTO.getEmployment().getWorkExperienceTotal(), scoringDataDTO.getEmployment().getWorkExperienceCurrent());
    }

    public ScoringRequestDTO scoringDataDTOToScoringRequestDTO(ScoringDataDTO scoringDataDTO) {
        log.info("{} making localDTO with scoringDataDTOToScoringRequestDTO method", this.getClass().getSimpleName());
        return new ScoringRequestDTO(scoringDataDTO.getAmount(), scoringDataDTO.getTerm(),
                scoringDataDTO.getIsInsuranceEnabled(), scoringDataDTO.getIsSalaryClient());
    }

    public CreditDTO scoringResponseDTOToCreditDTO(ScoringResponseDTO scoringResponseDTO) {
        log.info("{} making CreditDTO with scoringResponseDTOToCreditDTO method", this.getClass().getSimpleName());
        return new CreditDTO(scoringResponseDTO.getAmount(), scoringResponseDTO.getTerm(), scoringResponseDTO.getMonthlyPayment(),
                scoringResponseDTO.getRate(), scoringResponseDTO.getPsk(), scoringResponseDTO.getIsInsuranceEnabled(),
                scoringResponseDTO.getIsSalaryClient(), scoringResponseDTO.getPaymentSchedule());
    }
}
