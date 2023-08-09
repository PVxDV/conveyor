package pvxdv.conveyor.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import pvxdv.conveyor.dto.enums.Gender;
import pvxdv.conveyor.dto.enums.MaritalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class ScoringDataDTO {
    @NotNull
    @Min(10000)
    BigDecimal amount;
    @NotNull @Min(6)
    Integer term;
    @NotNull @Length(min = 2, max = 30, message = "\"firstName\" should be from 2 to 30 latin letters")
    String firstName;
    @NotNull @Length(min = 2, max = 30, message = "\"lastName\" should be from 2 to 30 latin letters")
    String lastName;
    @Length(min = 2, max = 30, message = "\"middleName\" should be from 2 to 30 latin letters or null")
    String middleName;
    @NotNull
    Gender gender;
    @NotNull  @Past
    LocalDate birthdate;
    @NotNull @Length(min = 4, max = 4, message = "\"passportSeries\" should be 4 digits")
    String passportSeries;
    @NotNull @Length(min = 6, max = 6, message = "\"passportSeries\" should be 6 digits")
    String passportNumber;
    @NotNull  @Past
    LocalDate passportIssueDate;
    @NotNull
    String passportIssueBranch;
    @NotNull
    MaritalStatus maritalStatus;
    @NotNull @Min(0)
    Integer dependentAmount;
    @NotNull
    EmploymentDTO employment;
    @NotNull
    String account;
    @NotNull
    Boolean isInsuranceEnabled;
    @NotNull
    Boolean isSalaryClient;
}

