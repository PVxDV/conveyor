package pvxdv.conveyor.calculators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import pvxdv.conveyor.calculators.MonthlyPaymentCalculator;
import pvxdv.conveyor.calculators.PskCalculator;
import pvxdv.conveyor.calculators.RateCalculator;
import pvxdv.conveyor.dto.*;
import pvxdv.conveyor.dto.enums.EmploymentStatus;
import pvxdv.conveyor.dto.enums.Gender;
import pvxdv.conveyor.dto.enums.MaritalStatus;
import pvxdv.conveyor.dto.enums.Position;
import pvxdv.conveyor.mapper.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RateCalculatorImplTest {

    @Autowired
    RateCalculatorImpl rateCalculator;
    ScoringClientDTO scoringClientDTO;

    @ParameterizedTest
    @ValueSource(ints = {1990, 1991, 1992, 1993, 1994, 1995, 2000, 2001, 2002, 2003})
    @Test
    void calculateRateForScoringAgeVerificationApproved(int year) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), Gender.MALE, LocalDate.of(year, 3, 18),
                MaritalStatus.SINGLE, 0, EmploymentStatus.SELF_EMPLOYED, new BigDecimal("100000"),
                Position.MIDDLE_MANAGER, 84, 48, true, true);

        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);
        assertEquals(new BigDecimal("9"), rate);

    }

    @Test
    void calculateRateForPreScoring() {
    }
}