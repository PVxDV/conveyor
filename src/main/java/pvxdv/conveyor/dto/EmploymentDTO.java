package pvxdv.conveyor.dto;

import lombok.Value;
import pvxdv.conveyor.dto.enums.EmploymentStatus;
import pvxdv.conveyor.dto.enums.Position;

import java.math.BigDecimal;

@Value
public class EmploymentDTO {
     EmploymentStatus employmentStatus;
     String employerINN;
     BigDecimal salary;
     Position position;
     Integer workExperienceTotal;
     Integer workExperienceCurrent;
}
