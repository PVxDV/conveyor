package pvxdv.conveyor.localDto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class ClientDTO {
    BigDecimal amount;
    public Enum gender;
    LocalDate birthdate;
    Enum maritalStatus;
    Integer dependentAmount;
    Enum employmentStatus;
    BigDecimal salary;
    Enum position;
    Integer workExperienceTotal;
    Integer workExperienceCurrent;
}
