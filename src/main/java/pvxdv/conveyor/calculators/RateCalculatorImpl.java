package pvxdv.conveyor.calculators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pvxdv.conveyor.dto.ScoringClientDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Slf4j
public class RateCalculatorImpl implements RateCalculator {

    private final BigDecimal baseRate = BigDecimal.valueOf(15);

    @Override
    public BigDecimal calculateRateForScoring(ScoringClientDTO clientDTO) {
        log.info("Client verification begins");
        BigDecimal currentRate = insuranceAndClientChecker(baseRate, clientDTO.getIsInsuranceEnabled(), clientDTO.getIsSalaryClient());
        BigDecimal rejection = BigDecimal.valueOf(-1);
        int age = LocalDate.now().getYear() - clientDTO.getBirthdate().getYear();

        log.info("age verification");
        if (age < 20 || age > 60) {
            return rejection;
        }

        log.info("salary verification");
        if (clientDTO.getAmount().compareTo(clientDTO.getSalary().multiply(BigDecimal.valueOf(20))) > 0) {
            return rejection;
        }

        log.info("workExperience verification");
        if (clientDTO.getWorkExperienceTotal() < 12 || clientDTO.getWorkExperienceCurrent() < 3) {
            return rejection;
        }

        log.info("dependentAmount verification");
        if (clientDTO.getDependentAmount() > 1) {
            currentRate = currentRate.add(BigDecimal.valueOf(1));
        }

        log.info("EmploymentStatus verification");
        switch (clientDTO.getEmploymentStatus()) {
            case UNEMPLOYED -> {
                return rejection;
            }
            case SELF_EMPLOYED -> currentRate = currentRate.add(BigDecimal.valueOf(1));
            case BUSINESS_OWNER -> currentRate = currentRate.add(BigDecimal.valueOf(3));
        }

        log.info("EmploymentPosition verification");
        switch (clientDTO.getPosition()) {
            case MIDDLE_MANAGER -> currentRate = currentRate.subtract(BigDecimal.valueOf(2));
            case TOP_MANAGER -> currentRate = currentRate.subtract(BigDecimal.valueOf(4));
        }

        log.info("MaritalStatus verification");
        switch (clientDTO.getMaritalStatus()) {
            case MARRIED -> currentRate = currentRate.subtract(BigDecimal.valueOf(3));
            case DIVORCED -> currentRate = currentRate.add(BigDecimal.valueOf(1));
            case SINGLE -> currentRate = currentRate.add(BigDecimal.valueOf(2));
        }

        log.info("Gender verification");
        switch (clientDTO.getGender()) {
            case FEMALE -> {
                if (age >= 35) {
                    currentRate = currentRate.subtract(BigDecimal.valueOf(3));
                }
            }
            case MALE -> {
                if (age >= 30 && age <= 55) {
                    currentRate = currentRate.subtract(BigDecimal.valueOf(3));
                }
            }
            default -> currentRate = currentRate.add(BigDecimal.valueOf(3));
        }
        log.info("client verification is completed");
        return currentRate;
    }

    @Override
    public BigDecimal calculateRateForPreScoring(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        return insuranceAndClientChecker(baseRate, isInsuranceEnabled, isSalaryClient);
    }

    private BigDecimal insuranceAndClientChecker(BigDecimal rate, Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        BigDecimal creditRate = rate;

        if (isInsuranceEnabled) {
            if (isSalaryClient) {
                creditRate = creditRate.subtract(BigDecimal.valueOf(4));
            } else {
                creditRate = creditRate.subtract(BigDecimal.valueOf(3));
            }
        } else {
            if (!isSalaryClient) {
                creditRate = creditRate.add(BigDecimal.valueOf(3));
            }
        }
        return creditRate;
    }
}