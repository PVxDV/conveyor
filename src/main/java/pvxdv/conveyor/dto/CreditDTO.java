package pvxdv.conveyor.dto;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class CreditDTO {
    BigDecimal amount;
    Integer term;
    BigDecimal monthlyPayment;
    BigDecimal rate;
    BigDecimal psk;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;
    List<PaymentScheduleElement> paymentSchedule;
}

