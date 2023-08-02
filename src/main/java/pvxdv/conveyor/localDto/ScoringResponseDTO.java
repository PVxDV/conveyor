package pvxdv.conveyor.localDto;

import lombok.Value;
import pvxdv.conveyor.dto.PaymentScheduleElement;

import java.math.BigDecimal;
import java.util.List;

@Value
public class ScoringResponseDTO {
    BigDecimal amount;
    Integer term;
    BigDecimal monthlyPayment;
    BigDecimal rate;
    BigDecimal psk;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;
    List<PaymentScheduleElement> paymentSchedule;


}
