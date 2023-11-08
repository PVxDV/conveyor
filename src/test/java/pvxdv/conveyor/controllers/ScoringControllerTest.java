package pvxdv.conveyor.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pvxdv.conveyor.dto.CreditDTO;
import pvxdv.conveyor.dto.EmploymentDTO;
import pvxdv.conveyor.dto.PaymentScheduleElement;
import pvxdv.conveyor.dto.ScoringDataDTO;
import pvxdv.conveyor.services.ScoringService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pvxdv.conveyor.controllers.Convectors.convertToPostWithJson;
import static pvxdv.conveyor.dto.enums.EmploymentStatus.SELF_EMPLOYED;
import static pvxdv.conveyor.dto.enums.Gender.MALE;
import static pvxdv.conveyor.dto.enums.MaritalStatus.SINGLE;
import static pvxdv.conveyor.dto.enums.Position.MIDDLE_MANAGER;

@WebMvcTest(ScoringController.class)
class ScoringControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    ScoringService scoringService;

    @Test
    void getScoringApproved() throws Exception {
        ScoringDataDTO scoringDataDTO = new ScoringDataDTO(new BigDecimal("300000"), 6, "firstName", "lastName",
                "middleName", MALE, LocalDate.of(1993, 3, 18), "1234",
                "123456", LocalDate.of(2007, 3, 10), "12345678",
                SINGLE, 0, new EmploymentDTO(SELF_EMPLOYED, "12345",
                new BigDecimal("100000"), MIDDLE_MANAGER, 84, 48),
                "testAccount", false, false);

        LocalDate today = LocalDate.now();
        LocalDate[] localDates = {today.plusMonths(1), today.plusMonths(2),
                today.plusMonths(3), today.plusMonths(4),
                today.plusMonths(5), today.plusMonths(6),};

        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>(List.of(
                new PaymentScheduleElement(1, localDates[0], new BigDecimal("52359.09"),
                        new BigDecimal("3945.21"), new BigDecimal("48413.89"), new BigDecimal("251586.12")),
                new PaymentScheduleElement(2, localDates[1], new BigDecimal("52359.09"),
                        new BigDecimal("3418.82"), new BigDecimal("48940.28"), new BigDecimal("202645.84")),
                new PaymentScheduleElement(3, localDates[2], new BigDecimal("52359.09"),
                        new BigDecimal("2664.94"), new BigDecimal("49694.16"), new BigDecimal("152951.69")),
                new PaymentScheduleElement(4, localDates[3], new BigDecimal("52359.09"),
                        new BigDecimal("2078.47"), new BigDecimal("50280.63"), new BigDecimal("102671.06")),
                new PaymentScheduleElement(5, localDates[4], new BigDecimal("52359.09"),
                        new BigDecimal("1391.39"), new BigDecimal("50967.71"), new BigDecimal("51703.36")),
                new PaymentScheduleElement(6, localDates[5], new BigDecimal("52358.84"),
                        new BigDecimal("655.48"), new BigDecimal("51703.36"), new BigDecimal("0"))
        ));

        CreditDTO creditDTO = new CreditDTO(new BigDecimal("300000"), 6, new BigDecimal("52359.09"), new BigDecimal("16"),
                new BigDecimal("4.72"), false, false, paymentSchedule);

        Mockito.when(this.scoringService.calculateScoring(scoringDataDTO)).thenReturn(creditDTO);

        this.mvc.perform(convertToPostWithJson("/conveyor/calculation/", scoringDataDTO))
                .andExpect(status().isOk()).andExpect( content().contentType("application/json"));
    }

    @Test
    void getScoringRejection() throws Exception {
        LocalDate minorBirthday = LocalDate.now().minusYears(16);

        ScoringDataDTO scoringDataDTO;

        scoringDataDTO = new ScoringDataDTO(new BigDecimal("300000"), 6, "firstName", "lastName",
                "middleName", MALE, LocalDate.of(1993, 3, 18), "1234",
                "123456", minorBirthday, "12345678",
                SINGLE, 0, new EmploymentDTO(SELF_EMPLOYED, "12345",
                new BigDecimal("100000"), MIDDLE_MANAGER, 84, 48),
                "testAccount", false, false);

        MvcResult mvcResult = mvc.perform(convertToPostWithJson("/conveyor/calculation/", scoringDataDTO))
                .andExpect(status().is2xxSuccessful()).andReturn();

        assertNull(null, mvcResult.getResponse().getContentAsString());
    }
}
