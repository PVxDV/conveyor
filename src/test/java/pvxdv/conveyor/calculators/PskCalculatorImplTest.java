package pvxdv.conveyor.calculators;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PskCalculatorImplTest {
    @InjectMocks
    PskCalculatorImpl pskCalculator;

    @Test
    void calculatePsk() {
        BigDecimal psk = pskCalculator.calculatePsk(new BigDecimal("47144.93"), new BigDecimal("1000000"), 24);
        assertEquals(new BigDecimal("13.15"), psk);
    }
}