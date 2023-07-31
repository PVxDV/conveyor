package pvxdv.conveyor.localDto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PreSoringResponseDTO {
    BigDecimal requestedAmount;
    BigDecimal totalAmount;
    Integer term;
    BigDecimal monthlyPayment;
    BigDecimal rate;
}
