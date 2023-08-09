package pvxdv.conveyor.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;
import pvxdv.conveyor.dto.enums.EmploymentStatus;
import pvxdv.conveyor.dto.enums.Position;

import java.math.BigDecimal;

@Value
public class EmploymentDTO {
     @NotNull
     EmploymentStatus employmentStatus;
     @NotNull
     String employerINN;
     @NotNull @Positive
     BigDecimal salary;
     @NotNull
     Position position;
     @NotNull @Positive
     Integer workExperienceTotal;
     @NotNull @Positive
     Integer workExperienceCurrent;
}
