package pvxdv.conveyor.calculators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pvxdv.conveyor.dto.ScoringClientDTO;
import pvxdv.conveyor.dto.enums.EmploymentStatus;
import pvxdv.conveyor.dto.enums.Gender;
import pvxdv.conveyor.dto.enums.MaritalStatus;
import pvxdv.conveyor.dto.enums.Position;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PskCalculatorImplTest {
    ScoringClientDTO scoringClientDTO;
    @BeforeEach
    void setUp() {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), Gender.MALE, LocalDate.of(1993, 3, 18),
                MaritalStatus.SINGLE, 0, EmploymentStatus.SELF_EMPLOYED, new BigDecimal("100000"),
                Position.MIDDLE_MANAGER, 84, 48, true, true);
    }

    @Test
    void calculatePsk() {
    }
}