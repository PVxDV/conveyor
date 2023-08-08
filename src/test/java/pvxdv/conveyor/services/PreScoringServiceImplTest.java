package pvxdv.conveyor.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pvxdv.conveyor.calculators.MonthlyPaymentCalculator;
import pvxdv.conveyor.calculators.RateCalculator;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;
import pvxdv.conveyor.dto.PreScoringDTO;
import pvxdv.conveyor.mapper.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PreScoringServiceImplTest {
    @Mock
    MonthlyPaymentCalculator monthlyPaymentCalculator;
    @Mock
    RateCalculator rateCalculator;
    @Mock
    Mapper mapper;

    @InjectMocks
    PreScoringServiceImpl preScoringService;

    LoanApplicationRequestDTO loanApplicationRequestDTO;
    PreScoringDTO preScoringDTO;

    @BeforeEach
    void setUp() {
        preScoringDTO = new PreScoringDTO(new BigDecimal(300000), 18);
    }

    @Test
    void generateAvailableOffersApproved() {
        loanApplicationRequestDTO = new LoanApplicationRequestDTO(BigDecimal.valueOf(300000),
                18, "firstName", "lastName", null, "test@email.test",
                LocalDate.of(1993, 3, 18), "1234", "123456");

        Long applicationId = 99L;

        when(mapper.loanApplicationRequestDTOToPreScoringDTO(any())).thenReturn(preScoringDTO);
        when(rateCalculator.calculateRateForPreScoring(any(), any())).thenReturn(new BigDecimal("20"));
        when(monthlyPaymentCalculator
                .calculateMonthlyPaymentForPreScoring(any(), any(), any(), any()))
                .thenReturn(new BigDecimal("15000"));

        List<LoanOfferDTO> loanOfferDTOList = preScoringService.generateAvailableOffers(loanApplicationRequestDTO, applicationId);

        verify(mapper, times(1)).loanApplicationRequestDTOToPreScoringDTO(any());
        verify(rateCalculator, times(4)).calculateRateForPreScoring(any(), any());
        verify(monthlyPaymentCalculator, times(4)).calculateMonthlyPaymentForPreScoring(any(), any(), any(), any());

        assertEquals(4, loanOfferDTOList.size());

        assertEquals(loanApplicationRequestDTO.getAmount(),loanOfferDTOList.get(0).getRequestedAmount());
        assertEquals(loanApplicationRequestDTO.getAmount(),loanOfferDTOList.get(1).getRequestedAmount());
        assertEquals(loanApplicationRequestDTO.getAmount(),loanOfferDTOList.get(2).getRequestedAmount());
        assertEquals(loanApplicationRequestDTO.getAmount(),loanOfferDTOList.get(3).getRequestedAmount());

        assertEquals(loanApplicationRequestDTO.getTerm(),loanOfferDTOList.get(0).getTerm());
        assertEquals(loanApplicationRequestDTO.getTerm(),loanOfferDTOList.get(1).getTerm());
        assertEquals(loanApplicationRequestDTO.getTerm(),loanOfferDTOList.get(2).getTerm());
        assertEquals(loanApplicationRequestDTO.getTerm(),loanOfferDTOList.get(3).getTerm());

        assertEquals(applicationId,loanOfferDTOList.get(0).getApplicationId());
        assertEquals(applicationId,loanOfferDTOList.get(1).getApplicationId());
        assertEquals(applicationId,loanOfferDTOList.get(2).getApplicationId());
        assertEquals(applicationId,loanOfferDTOList.get(3).getApplicationId());
    }

    @Test
    void generateAvailableOffersRejection() {
        LocalDate birthdayForAMinor = LocalDate.now().minusYears(16);

        loanApplicationRequestDTO = new LoanApplicationRequestDTO(BigDecimal.valueOf(300000),
                18, "firstName", "lastName", null, "test@email.test",
                birthdayForAMinor, "1234", "123456");

        List<LoanOfferDTO> loanOfferDTOList = preScoringService.generateAvailableOffers(loanApplicationRequestDTO, 99L);

        verify(mapper, times(0)).loanApplicationRequestDTOToPreScoringDTO(any());
        verify(rateCalculator, times(0)).calculateRateForPreScoring(any(), any());
        verify(monthlyPaymentCalculator, times(0)).calculateMonthlyPaymentForPreScoring(any(), any(), any(), any());

       assertNull(loanOfferDTOList);
    }
}