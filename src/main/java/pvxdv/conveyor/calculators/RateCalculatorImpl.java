package pvxdv.conveyor.calculators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pvxdv.conveyor.dto.ScoringClientDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;


@Component
@Slf4j
public class RateCalculatorImpl implements RateCalculator {
    @Value("${baseRate}")
    private BigDecimal baseRate;
    private final BigDecimal rejection = new BigDecimal("-1");

    @Override
    public BigDecimal calculateRateForScoring(ScoringClientDTO scoringClientDTO) {
        log.info("Client verification begins");
        BigDecimal currentRate = insuranceAndClientChecker(baseRate, scoringClientDTO.getIsInsuranceEnabled(), scoringClientDTO.getIsSalaryClient());
        int age = ageCalculator(scoringClientDTO.getBirthdate());

        log.info("age verification");
        if (age < 20 || age > 60) {
            log.info("age verification failed - REJECTION");
            return rejection;
        }

        log.info("salary verification");
        if (scoringClientDTO.getAmount().compareTo(scoringClientDTO.getSalary().multiply(new BigDecimal("20"))) > 0) {
            log.info("salary verification failed - REJECTION");
            return rejection;
        }

        log.info("workExperience verification");
        if (scoringClientDTO.getWorkExperienceTotal() < 12 || scoringClientDTO.getWorkExperienceCurrent() < 3) {
            log.info("workExperience verification failed - REJECTION");
            return rejection;
        }

        log.info("dependentAmount verification");
        if (scoringClientDTO.getDependentAmount() > 1) {
            currentRate = currentRate.add(new BigDecimal("2"));
        }

        log.info("EmploymentStatus verification");
        switch (scoringClientDTO.getEmploymentStatus()) {
            case UNEMPLOYED -> {
                log.info("EmploymentStatus verification failed - REJECTION");
                return rejection;
            }
            case SELF_EMPLOYED -> currentRate = currentRate.add(new BigDecimal("1"));
            case BUSINESS_OWNER -> currentRate = currentRate.add(new BigDecimal("3"));
        }

        log.info("EmploymentPosition verification");
        switch (scoringClientDTO.getPosition()) {
            case MIDDLE_MANAGER -> currentRate = currentRate.subtract(new BigDecimal("2"));
            case TOP_MANAGER -> currentRate = currentRate.subtract(new BigDecimal("4"));
        }

        log.info("MaritalStatus verification");
        switch (scoringClientDTO.getMaritalStatus()) {
            case MARRIED -> currentRate = currentRate.subtract(new BigDecimal("3"));
            case DIVORCED -> currentRate = currentRate.add(new BigDecimal("1"));
            case SINGLE -> currentRate = currentRate.add(new BigDecimal("2"));
        }

        log.info("Gender verification");
        switch (scoringClientDTO.getGender()) {
            case FEMALE -> {
                if (age >= 35) {
                    currentRate = currentRate.subtract(new BigDecimal("3"));
                }
            }
            case MALE -> {
                if (age >= 30 && age <= 55) {
                    currentRate = currentRate.subtract(new BigDecimal("3"));
                }
            }
            default -> currentRate = currentRate.add(new BigDecimal("3"));
        }
        log.info("client verification is completed");
        return currentRate;
    }

    @Override
    public BigDecimal calculateRateForPreScoring( Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        return insuranceAndClientChecker(baseRate, isInsuranceEnabled, isSalaryClient);
    }

    private BigDecimal insuranceAndClientChecker(BigDecimal rate, Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        BigDecimal creditRate = rate;

        if (isInsuranceEnabled) {
            if (isSalaryClient) {
                creditRate = creditRate.subtract(new BigDecimal("4"));
            } else {
                creditRate = creditRate.subtract(new BigDecimal("3"));
            }
        } else {
            if (!isSalaryClient) {
                creditRate = creditRate.add(new BigDecimal("3"));
            }
        }
        return creditRate;
    }

    private int ageCalculator(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
}