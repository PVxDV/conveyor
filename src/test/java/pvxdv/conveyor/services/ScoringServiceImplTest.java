package pvxdv.conveyor.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class ScoringServiceImplTest {
    @Mock
    MonthlyPaymentCalculator monthlyPaymentCalculator;
    @Mock
    RateCalculator rateCalculator;
    @Mock
    PskCalculator pskCalculator;
    @Mock
    Mapper mapper;
    @InjectMocks
    ScoringServiceImpl scoringService;

    ScoringDataDTO scoringDataDTO;
    ScoringDTO scoringDTO;
    ScoringClientDTO scoringClientDTO;

    @BeforeEach
    void setUp() {
        scoringDataDTO = new ScoringDataDTO(new BigDecimal("300000"), 18, "firstName", "lastName",
                "middleName", Gender.MALE, LocalDate.of(1993, 3, 18), "1234",
                "123456", LocalDate.of(2007, 3, 10), "12345678",
                MaritalStatus.SINGLE, 0, new EmploymentDTO(EmploymentStatus.SELF_EMPLOYED, "12345",
                new BigDecimal("100000"), Position.MIDDLE_MANAGER, 84, 48),
                "testAccount", true, true);

        scoringDTO = new ScoringDTO(new BigDecimal("300000"), 18, true);

        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), Gender.MALE, LocalDate.of(1993, 3, 18),
                MaritalStatus.SINGLE, 0, EmploymentStatus.SELF_EMPLOYED, new BigDecimal("100000"),
                Position.MIDDLE_MANAGER, 84, 48, true, true);
    }

    @Test
    void calculateScoringApproved() {
        when(mapper.scoringDataDTOToScoringDTO(any())).thenReturn(scoringDTO);
        when(mapper.scoringDataDTOToScoringClientDTO(any())).thenReturn(scoringClientDTO);

        when(rateCalculator.calculateRateForScoring(any())).thenReturn(new BigDecimal("20"));

        when(monthlyPaymentCalculator.calculateMonthlyPaymentForScoring(any(), any())).thenReturn(new BigDecimal("20000"));

        when(pskCalculator.calculatePsk(any(), any(), any())).thenReturn(new BigDecimal("25"));

        CreditDTO creditDTO = scoringService.calculateScoring(scoringDataDTO);

        verify(mapper, times(1)).scoringDataDTOToScoringClientDTO(any());
        verify(mapper, times(1)).scoringDataDTOToScoringDTO(any());
        verify(rateCalculator, times(1)).calculateRateForScoring(any());
        verify(monthlyPaymentCalculator, times(1)).calculateMonthlyPaymentForScoring(any(), any());
        verify(pskCalculator, times(1)).calculatePsk(any(), any(), any());

        assertEquals(scoringDataDTO.getTerm(), creditDTO.getPaymentSchedule().size());
        assertEquals(scoringDataDTO.getTerm(), creditDTO.getTerm());
        assertEquals(scoringDataDTO.getAmount(), creditDTO.getAmount());
        assertEquals(scoringDataDTO.getIsInsuranceEnabled(), creditDTO.getIsInsuranceEnabled());
        assertEquals(scoringDataDTO.getIsSalaryClient(), creditDTO.getIsSalaryClient());
    }

    @Test
    void calculateScoringRejection() {
        when(mapper.scoringDataDTOToScoringDTO(any())).thenReturn(scoringDTO);
        when(mapper.scoringDataDTOToScoringClientDTO(any())).thenReturn(scoringClientDTO);

        when(rateCalculator.calculateRateForScoring(any())).thenReturn(new BigDecimal("-1"));
        CreditDTO creditDTO = scoringService.calculateScoring(scoringDataDTO);

        verify(mapper, times(1)).scoringDataDTOToScoringClientDTO(any());
        verify(mapper, times(1)).scoringDataDTOToScoringDTO(any());
        verify(rateCalculator, times(1)).calculateRateForScoring(any());
        verify(monthlyPaymentCalculator, times(0)).calculateMonthlyPaymentForScoring(any(), any());
        verify(pskCalculator, times(0)).calculatePsk(any(), any(), any());

        assertNull(creditDTO);
    }
}