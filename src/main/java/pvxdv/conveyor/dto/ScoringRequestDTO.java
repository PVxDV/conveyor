package pvxdv.conveyor.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ScoringRequestDTO {
    BigDecimal amount;
    Integer term;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;
}
