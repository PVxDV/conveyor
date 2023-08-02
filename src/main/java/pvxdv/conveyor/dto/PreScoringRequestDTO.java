package pvxdv.conveyor.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PreScoringRequestDTO {
    BigDecimal amount;
    Integer term;
}
