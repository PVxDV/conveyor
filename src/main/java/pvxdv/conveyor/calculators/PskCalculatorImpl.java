package pvxdv.conveyor.calculators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Slf4j
public class PskCalculatorImpl implements PskCalculator {
    @Override
    public BigDecimal calculatePsk(BigDecimal monthlyPayment, BigDecimal amount, Integer term) {
        BigDecimal termInYears = BigDecimal.valueOf(term).divide(BigDecimal.valueOf(12), 2, RoundingMode.CEILING);

        log.info("calculation of the psk");

        BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(term)).
                divide(amount, 10, RoundingMode.CEILING).
                subtract(BigDecimal.valueOf(1)).
                divide(termInYears, 10, RoundingMode.CEILING).
                multiply(BigDecimal.valueOf(100));

        return psk;
    }
}
