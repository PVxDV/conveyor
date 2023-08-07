package pvxdv.conveyor.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ScoringDTO {
    BigDecimal amount;
    Integer term;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;
}
