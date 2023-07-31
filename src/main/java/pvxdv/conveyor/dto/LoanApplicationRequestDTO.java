package pvxdv.conveyor.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.Value;


import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class LoanApplicationRequestDTO {

    @NonNull
    BigDecimal amount;
    @NonNull
    Integer term;
    @NotBlank(message = "the field \"firstName\" should not be empty")
    String firstName;
    @NotBlank(message = "the field \"lastName\" should not be empty")
    String lastName;
    @NotBlank(message = "не должно быть пустым")
    String middleName;
    // @Pattern(regexp = "[\\w\\.]{2,50}@[\\w\\.]{2,20}")
    String email;
    @NonNull
    LocalDate birthdate;
    @NotBlank(message = "the field \"firstName\" should not be empty")
    String passportSeries;
    @NotBlank(message = "не должно быть пустым")
    String passportNumber;
}

