package pvxdv.conveyor.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class LoanOfferDTO {
    Long applicationId;
    BigDecimal requestedAmount;
    BigDecimal totalAmount;
    Integer term;
    BigDecimal monthlyPayment;
    BigDecimal rate;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;
}


