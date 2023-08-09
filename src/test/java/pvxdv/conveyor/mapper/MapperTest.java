package pvxdv.conveyor.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pvxdv.conveyor.dto.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static pvxdv.conveyor.dto.enums.EmploymentStatus.SELF_EMPLOYED;
import static pvxdv.conveyor.dto.enums.Gender.MALE;

import static pvxdv.conveyor.dto.enums.MaritalStatus.SINGLE;
import static pvxdv.conveyor.dto.enums.Position.MIDDLE_MANAGER;

@ExtendWith(MockitoExtension.class)
class MapperTest {
    @InjectMocks
    Mapper mapper;
    ScoringDataDTO scoringDataDTO;
    LoanApplicationRequestDTO loanApplicationRequestDTO;

    @Test
    void loanApplicationRequestDTOToPreScoringDTO() {
        loanApplicationRequestDTO = new LoanApplicationRequestDTO(new BigDecimal("300000"),
                18, "firstName", "lastName", null, "test@email.test",
                LocalDate.of(1993, 3, 18), "1234", "123456");

        PreScoringDTO preScoringDTO = mapper.loanApplicationRequestDTOToPreScoringDTO(loanApplicationRequestDTO);

        assertEquals(new BigDecimal("300000"), preScoringDTO.getAmount());
        assertEquals(18, preScoringDTO.getTerm());
    }

    @Test
    void scoringDataDTOToScoringClientDTO() {
        scoringDataDTO = new ScoringDataDTO(new BigDecimal("300000"), 18, "firstName", "lastName",
                "middleName", MALE, LocalDate.of(1993, 3, 18), "1234",
                "123456", LocalDate.of(2007, 3, 10), "12345678",
                SINGLE, 0, new EmploymentDTO(SELF_EMPLOYED, "12345",
                new BigDecimal("100000"), MIDDLE_MANAGER, 84, 48),
                "testAccount", true, true);

        ScoringClientDTO scoringClientDTO = mapper.scoringDataDTOToScoringClientDTO(scoringDataDTO);

        assertEquals(new BigDecimal("300000"), scoringClientDTO.getAmount());
        assertEquals(MALE, scoringClientDTO.getGender());
        assertEquals(LocalDate.of(1993, 3, 18), scoringClientDTO.getBirthdate());
        assertEquals(SINGLE, scoringClientDTO.getMaritalStatus());
        assertEquals(0, scoringClientDTO.getDependentAmount());
        assertEquals(SELF_EMPLOYED, scoringClientDTO.getEmploymentStatus());
        assertEquals(new BigDecimal("100000"), scoringClientDTO.getSalary());
        assertEquals(MIDDLE_MANAGER, scoringClientDTO.getPosition());
        assertEquals(84, scoringClientDTO.getWorkExperienceTotal());
        assertEquals(48, scoringClientDTO.getWorkExperienceCurrent());
        assertEquals(true, scoringClientDTO.getIsInsuranceEnabled());
        assertEquals(true, scoringClientDTO.getIsSalaryClient());
    }

    @Test
    void scoringDataDTOToScoringDTO() {
        scoringDataDTO = new ScoringDataDTO(new BigDecimal("300000"), 18, "firstName", "lastName",
                "middleName", MALE, LocalDate.of(1993, 3, 18), "1234",
                "123456", LocalDate.of(2007, 3, 10), "12345678",
                SINGLE, 0, new EmploymentDTO(SELF_EMPLOYED, "12345",
                new BigDecimal("100000"), MIDDLE_MANAGER, 84, 48),
                "testAccount", true, true);

        ScoringDTO scoringDTO = mapper.scoringDataDTOToScoringDTO(scoringDataDTO);

        assertEquals(new BigDecimal("300000"), scoringDataDTO.getAmount());
        assertEquals(18, scoringDTO.getTerm());
        assertEquals(true, scoringDTO.getIsInsuranceEnabled());
    }
}