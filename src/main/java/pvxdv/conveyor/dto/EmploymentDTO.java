package pvxdv.conveyor.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class EmploymentDTO {
     Enum employmentStatus;
     String employerINN;
     BigDecimal salary;
     Enum position;
     Integer workExperienceTotal;
     Integer workExperienceCurrent;
}
