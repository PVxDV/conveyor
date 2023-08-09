package pvxdv.conveyor.calculators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pvxdv.conveyor.dto.*;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MonthlyPaymentCalculatorImplTest {

    @Autowired
    MonthlyPaymentCalculatorImpl monthlyPaymentCalculator;

    PreScoringDTO preScoringDTO;
    ScoringDTO scoringDTO;

    @Test
    void calculateMonthlyPaymentForScoringWithInsurance() {
        scoringDTO = new ScoringDTO(new BigDecimal("1000000"), 24, true);
        BigDecimal monthlyPayment = monthlyPaymentCalculator
                .calculateMonthlyPaymentForScoring(scoringDTO, new BigDecimal("15"));
        assertEquals(new BigDecimal("53335.32"), monthlyPayment);
    }

    @Test
    void calculateMonthlyPaymentForScoringWithOutInsurance() {
        scoringDTO = new ScoringDTO(new BigDecimal("1000000"), 24, false);
        BigDecimal monthlyPayment = monthlyPaymentCalculator
                .calculateMonthlyPaymentForScoring(scoringDTO, new BigDecimal("15"));
        assertEquals(new BigDecimal("48486.65"), monthlyPayment);
    }

    @Test
    void calculateMonthlyPaymentForPreScoringWithInsurance() {
        preScoringDTO = new PreScoringDTO(new BigDecimal("1000000"), 24);

        BigDecimal monthlyPayment = monthlyPaymentCalculator.calculateMonthlyPaymentForPreScoring(preScoringDTO,
                true, new BigDecimal("15"));

        assertEquals(new BigDecimal("53335.32"), monthlyPayment);
    }

    @Test
    void calculateMonthlyPaymentForPreScoringWithOutInsurance() {
        preScoringDTO = new PreScoringDTO(new BigDecimal("1000000"), 24);

        BigDecimal monthlyPayment = monthlyPaymentCalculator.calculateMonthlyPaymentForPreScoring(preScoringDTO,
                false, new BigDecimal("15"));

        assertEquals(new BigDecimal("48486.65"), monthlyPayment);
    }
}