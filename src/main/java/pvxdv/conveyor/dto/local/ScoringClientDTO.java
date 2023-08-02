package pvxdv.conveyor.dto.local;

import lombok.Value;
import pvxdv.conveyor.dto.enums.EmploymentStatus;
import pvxdv.conveyor.dto.enums.Gender;
import pvxdv.conveyor.dto.enums.MaritalStatus;
import pvxdv.conveyor.dto.enums.Position;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class ScoringClientDTO {
    BigDecimal amount;
    Gender gender;
    LocalDate birthdate;
    MaritalStatus maritalStatus;
    Integer dependentAmount;
    EmploymentStatus employmentStatus;
    BigDecimal salary;
    Position position;
    Integer workExperienceTotal;
    Integer workExperienceCurrent;

    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;
}
