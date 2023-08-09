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
        BigDecimal termInYears = new BigDecimal(term).divide(new BigDecimal("12"), 10, RoundingMode.CEILING);

        log.info("calculation of the psk");

        BigDecimal totalAmount = monthlyPayment.multiply(new BigDecimal(term));
        BigDecimal psk = totalAmount.divide(amount, 10, RoundingMode.CEILING)
                .subtract(new BigDecimal("1")).divide(termInYears, 10, RoundingMode.CEILING)
                .multiply(new BigDecimal("100"));

        return psk.multiply(termInYears).setScale(2,RoundingMode.CEILING);
    }
}
