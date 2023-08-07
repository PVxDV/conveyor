package pvxdv.conveyor.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PreScoringDTO {
    BigDecimal amount;
    Integer term;
}
