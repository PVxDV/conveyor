package pvxdv.conveyor.calculators;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pvxdv.conveyor.dto.*;
import pvxdv.conveyor.dto.enums.EmploymentStatus;
import pvxdv.conveyor.dto.enums.Gender;
import pvxdv.conveyor.dto.enums.MaritalStatus;
import pvxdv.conveyor.dto.enums.Position;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static pvxdv.conveyor.dto.enums.EmploymentStatus.SELF_EMPLOYED;
import static pvxdv.conveyor.dto.enums.Gender.OTHER;
import static pvxdv.conveyor.dto.enums.MaritalStatus.SINGLE;
import static pvxdv.conveyor.dto.enums.Position.MIDDLE_MANAGER;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RateCalculatorImplTest {

    @Autowired
    RateCalculatorImpl rateCalculator;
    ScoringClientDTO scoringClientDTO;

    @ParameterizedTest
    @MethodSource("underTwentyAndOlderThanSixtyBirthdayFactory")
    void calculateRateForScoringAgeVerificationRejection(LocalDate year) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, year,
                SINGLE, 0, SELF_EMPLOYED, new BigDecimal("100000"), MIDDLE_MANAGER, 84,
                48, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);
        assertEquals(new BigDecimal("-1"), rate);
    }

    @ParameterizedTest
    @MethodSource("overTwentySndUnderSixtyBirthdayFactory")
    void calculateRateForScoringAgeVerificationApproved(LocalDate year) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, year,
                SINGLE, 0, SELF_EMPLOYED, new BigDecimal("100000"), MIDDLE_MANAGER, 84,
                48, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);
        assertEquals(new BigDecimal("15"), rate);
    }

    @ParameterizedTest
    @ValueSource(ints = {15000, 50000, 100000})
    void calculateRateForScoringSalaryVerificationApproved(int salary) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, LocalDate.of(1993, 3, 18),
                SINGLE, 0, SELF_EMPLOYED, new BigDecimal(salary), MIDDLE_MANAGER, 84,
                48, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);
        assertEquals(new BigDecimal("15"), rate);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5000, 10000, 14999})
    void calculateRateForScoringSalaryVerificationRejection(int salary) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, LocalDate.of(1993, 3, 18),
                SINGLE, 0, SELF_EMPLOYED, new BigDecimal(salary), MIDDLE_MANAGER, 84,
                48, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);
        assertEquals(new BigDecimal("-1"), rate);
    }

    @ParameterizedTest
    @ValueSource(ints = {12, 20, 40})
    void calculateRateForScoringWorkExperienceTotalVerificationApproved(int workExperienceTotal) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, LocalDate.of(1993, 3, 18),
                SINGLE, 0, SELF_EMPLOYED, new BigDecimal("15000"), MIDDLE_MANAGER, workExperienceTotal,
                48, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);
        assertEquals(new BigDecimal("15"), rate);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 6, 11})
    void calculateRateForScoringWorkExperienceTotalVerificationRejection(int workExperienceTotal) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, LocalDate.of(1993, 3, 18),
                SINGLE, 0, SELF_EMPLOYED, new BigDecimal("15000"), MIDDLE_MANAGER, workExperienceTotal,
                48, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);
        assertEquals(new BigDecimal("-1"), rate);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 6, 12})
    void calculateRateForScoringWorkExperienceCurrentVerificationApproved(int workExperienceCurrent) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, LocalDate.of(1993, 3, 18),
                SINGLE, 0, SELF_EMPLOYED, new BigDecimal("15000"), MIDDLE_MANAGER, 40,
                workExperienceCurrent, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);
        assertEquals(new BigDecimal("15"), rate);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void calculateRateForScoringWorkExperienceCurrentVerificationRejection(int workExperienceCurrent) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, LocalDate.of(1993, 3, 18),
                SINGLE, 0, SELF_EMPLOYED, new BigDecimal("15000"), MIDDLE_MANAGER, 40,
                workExperienceCurrent, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);
        assertEquals(new BigDecimal("-1"), rate);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void calculateRateForScoringDependentAmountVerificationOneAndLess(int dependentAmount) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, LocalDate.of(1993, 3, 18),
                SINGLE, dependentAmount, SELF_EMPLOYED, new BigDecimal("15000"), MIDDLE_MANAGER, 40,
                6, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);
        assertEquals(new BigDecimal("15"), rate);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 10})
    void calculateRateForScoringDependentAmountVerificationMoreThanOne(int dependentAmount) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, LocalDate.of(1993, 3, 18),
                SINGLE, dependentAmount, SELF_EMPLOYED, new BigDecimal("15000"), MIDDLE_MANAGER, 40,
                6, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);
        assertEquals(new BigDecimal("17"), rate);
    }

    @ParameterizedTest
    @EnumSource(EmploymentStatus.class)
    void calculateRateForScoringEmploymentStatusVerification(EmploymentStatus employmentStatus) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, LocalDate.of(1993, 3, 18),
                SINGLE, 1, employmentStatus, new BigDecimal("15000"), MIDDLE_MANAGER, 40,
                6, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);

        switch (scoringClientDTO.getEmploymentStatus()) {
            case UNEMPLOYED -> assertEquals(new BigDecimal("-1"), rate);
            case SELF_EMPLOYED -> assertEquals(new BigDecimal("15"), rate);
            case BUSINESS_OWNER -> assertEquals(new BigDecimal("17"), rate);
        }
    }

    @ParameterizedTest
    @EnumSource(Position.class)
    void calculateRateForScoringEmploymentPositionVerification(Position position) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, LocalDate.of(1993, 3, 18),
                SINGLE, 1, SELF_EMPLOYED, new BigDecimal("15000"), position, 40,
                6, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);

        switch (scoringClientDTO.getPosition()) {
            case MIDDLE_MANAGER -> assertEquals(new BigDecimal("15"), rate);
            case TOP_MANAGER -> assertEquals(new BigDecimal("13"), rate);
        }
    }

    @ParameterizedTest
    @EnumSource(MaritalStatus.class)
    void calculateRateForScoringMaritalStatusVerification(MaritalStatus maritalStatus) {
        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), OTHER, LocalDate.of(1993, 3, 18),
                maritalStatus, 1, SELF_EMPLOYED, new BigDecimal("15000"), MIDDLE_MANAGER, 40,
                6, true, true);
        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);

        switch (scoringClientDTO.getMaritalStatus()) {
            case MARRIED -> assertEquals(new BigDecimal("10"), rate);
            case DIVORCED -> assertEquals(new BigDecimal("14"), rate);
            case SINGLE -> assertEquals(new BigDecimal("15"), rate);
        }
    }

    @ParameterizedTest
    @EnumSource(Gender.class)
    void calculateRateForScoringGenderVerificationForTheAgeOfThirtyFive(Gender gender) {
        LocalDate years = LocalDate.now().minusYears(35);

        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), gender, years,
                SINGLE, 1, SELF_EMPLOYED, new BigDecimal("15000"), MIDDLE_MANAGER, 40,
                6, true, true);

        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);

        switch (scoringClientDTO.getGender()) {
            case FEMALE, MALE -> assertEquals(new BigDecimal("9"), rate);
            case OTHER -> assertEquals(new BigDecimal("15"), rate);
        }
    }

    @ParameterizedTest
    @EnumSource(Gender.class)
    void calculateRateForScoringGenderVerificationForTheAgeOfTwentyNine(Gender gender) {
        LocalDate years = LocalDate.now().minusYears(23);

        scoringClientDTO = new ScoringClientDTO(new BigDecimal("300000"), gender, years,
                SINGLE, 1, SELF_EMPLOYED, new BigDecimal("15000"), MIDDLE_MANAGER, 40,
                6, true, true);

        BigDecimal rate = rateCalculator.calculateRateForScoring(scoringClientDTO);
        switch (scoringClientDTO.getGender()) {
            case FEMALE, MALE -> assertEquals(new BigDecimal("12"), rate);
            case OTHER -> assertEquals(new BigDecimal("15"), rate);
        }
    }

    @Test
    void calculateRateForPreScoringWithInsuranceToClient() {
        BigDecimal rate = rateCalculator.calculateRateForPreScoring(true, true);
        assertEquals(new BigDecimal("11"), rate);
    }

    @Test
    void calculateRateForPreScoring() {
        BigDecimal rate = rateCalculator.calculateRateForPreScoring(true, false);
        assertEquals(new BigDecimal("12"), rate);
    }

    @Test
    void calculateRateForPreScoringWithOutInsuranceToClient() {
        BigDecimal rate = rateCalculator.calculateRateForPreScoring(false, true);
        assertEquals(new BigDecimal("15"), rate);
    }

    @Test
    void calculateRateForPreScoringWithOutInsuranceToNonClient() {
        BigDecimal rate = rateCalculator.calculateRateForPreScoring(false, false);
        assertEquals(new BigDecimal("18"), rate);
    }


    static Stream<LocalDate> underTwentyAndOlderThanSixtyBirthdayFactory() {
        List<LocalDate> years = new ArrayList<>();

        for (int i = 1; i < 20; i++) {
            years.add(LocalDate.now().minusYears(i));
        }

        for (int i = 61; i < 100; i++) {
            years.add(LocalDate.now().minusYears(i));
        }
        return years.stream();
    }

    static Stream<LocalDate> overTwentySndUnderSixtyBirthdayFactory() {
        List<LocalDate> years = new ArrayList<>();

        for (int i = 20; i < 60; i++) {
            years.add(LocalDate.now().minusYears(i));
        }
        return years.stream();
    }


}