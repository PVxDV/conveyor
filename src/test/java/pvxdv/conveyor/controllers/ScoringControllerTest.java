package pvxdv.conveyor.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pvxdv.conveyor.dto.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pvxdv.conveyor.dto.enums.EmploymentStatus.SELF_EMPLOYED;
import static pvxdv.conveyor.dto.enums.Gender.MALE;
import static pvxdv.conveyor.dto.enums.MaritalStatus.SINGLE;
import static pvxdv.conveyor.dto.enums.Position.MIDDLE_MANAGER;

@SpringBootTest
@AutoConfigureMockMvc
class ScoringControllerTest {
    @Autowired
    MockMvc mockMvc;
    CreditDTO creditDTO;
    ScoringDataDTO scoringDataDTO;

    @Test
    void getScoringApproved() throws Exception {
        scoringDataDTO = new ScoringDataDTO(new BigDecimal("300000"), 6, "firstName", "lastName",
                "middleName", MALE, LocalDate.of(1993, 3, 18), "1234",
                "123456", LocalDate.of(2007, 3, 10), "12345678",
                SINGLE, 0, new EmploymentDTO(SELF_EMPLOYED, "12345",
                new BigDecimal("100000"), MIDDLE_MANAGER, 84, 48),
                "testAccount", false, false);

        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

        LocalDate today = LocalDate.now();

        LocalDate[] localDates = {today.plusMonths(1), today.plusMonths(2),
                today.plusMonths(3),today.plusMonths(4),
                today.plusMonths(5),today.plusMonths(6),};

        paymentSchedule.add(new PaymentScheduleElement(1, localDates[0], new BigDecimal("52359.09"),
                new BigDecimal("3945.21"), new BigDecimal("48413.89"), new BigDecimal("251586.12")));
        paymentSchedule.add(new PaymentScheduleElement(2, localDates[1], new BigDecimal("52359.09"),
                new BigDecimal("3418.82"), new BigDecimal("48940.28"), new BigDecimal("202645.84")));
        paymentSchedule.add(new PaymentScheduleElement(3, localDates[2], new BigDecimal("52359.09"),
                new BigDecimal("2664.94"), new BigDecimal("49694.16"), new BigDecimal("152951.69")));
        paymentSchedule.add(new PaymentScheduleElement(4, localDates[3], new BigDecimal("52359.09"),
                new BigDecimal("2078.47"), new BigDecimal("50280.63"), new BigDecimal("102671.06")));
        paymentSchedule.add(new PaymentScheduleElement(5, localDates[4], new BigDecimal("52359.09"),
                new BigDecimal("1391.39"), new BigDecimal("50967.71"), new BigDecimal("51703.36")));
        paymentSchedule.add(new PaymentScheduleElement(6, localDates[5], new BigDecimal("52358.84"),
                new BigDecimal("655.48"), new BigDecimal("51703.36"), new BigDecimal("0")));

        creditDTO = new CreditDTO(new BigDecimal("300000"), 6, new BigDecimal("52359.09"), new BigDecimal("16"),
                new BigDecimal("4.72"), false, false, paymentSchedule);

        MvcResult mvcResult = mockMvc.perform(convertToPostWithJson("/conveyor/calculation/", scoringDataDTO))
                .andExpect(status().is2xxSuccessful()).andReturn();

        assertEquals(convertToJson(creditDTO), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getScoringRejection() throws Exception {
        LocalDate minorBirthday = LocalDate.now().minusYears(16);

        scoringDataDTO = new ScoringDataDTO(new BigDecimal("300000"), 6, "firstName", "lastName",
                "middleName", MALE, LocalDate.of(1993, 3, 18), "1234",
                "123456", minorBirthday, "12345678",
                SINGLE, 0, new EmploymentDTO(SELF_EMPLOYED, "12345",
                new BigDecimal("100000"), MIDDLE_MANAGER, 84, 48),
                "testAccount", false, false);

        MvcResult mvcResult = mockMvc.perform(convertToPostWithJson("/conveyor/calculation/", scoringDataDTO))
                .andExpect(status().is2xxSuccessful()).andReturn();

        assertNull(null, mvcResult.getResponse().getContentAsString());
    }

    public static MockHttpServletRequestBuilder convertToPostWithJson(String uri, Object body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String json = objectMapper.writeValueAsString(body);
            return post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}