package pvxdv.conveyor.calculators;


import java.math.BigDecimal;

public interface PskCalculator {
    BigDecimal calculatePsk(BigDecimal monthlyPayment, BigDecimal amount, Integer term);
}
