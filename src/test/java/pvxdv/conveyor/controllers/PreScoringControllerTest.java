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

@SpringBootTest
@AutoConfigureMockMvc
class PreScoringControllerTest {
    @Autowired
    MockMvc mockMvc;
    List<LoanOfferDTO> loanOfferDTOList;
    LoanApplicationRequestDTO loanApplicationRequestDTO;

    @Test
    void getOffersApproved() throws Exception {
        loanOfferDTOList = new ArrayList<>();
        loanOfferDTOList.add(new LoanOfferDTO(1L, new BigDecimal("300000"), new BigDecimal("344551.32"),
                18, new BigDecimal("19141.74"), new BigDecimal("18"), false, false));
        loanOfferDTOList.add(new LoanOfferDTO(1L, new BigDecimal("300000"), new BigDecimal("336877.92"),
                18, new BigDecimal("18715.44"), new BigDecimal("15"), false, true));
        loanOfferDTOList.add(new LoanOfferDTO(1L, new BigDecimal("300000"), new BigDecimal("362233.44"),
                18, new BigDecimal("20124.08"), new BigDecimal("12"), true, false));
        loanOfferDTOList.add(new LoanOfferDTO(1L, new BigDecimal("300000"), new BigDecimal("359480.16"),
                18, new BigDecimal("19971.12"), new BigDecimal("11"), true, true));

        loanApplicationRequestDTO = new LoanApplicationRequestDTO(new BigDecimal("300000"), 18, "Pavel", "Ryzhikh",
                "Dmitrievich", "testEmail@test", LocalDate.of(1993, 3, 18),
                "1234", "123456");

        MvcResult mvcResult = mockMvc.perform(convertToPostWithJson("/conveyor/offers/1", loanApplicationRequestDTO))
                .andExpect(status().is2xxSuccessful()).andReturn();

        assertEquals(convertToJson(loanOfferDTOList), mvcResult.getResponse().getContentAsString());
    }
    @Test
    void getOffersRejection() throws Exception {
        LocalDate minorBirthday = LocalDate.now().minusYears(16);

        loanApplicationRequestDTO = new LoanApplicationRequestDTO(new BigDecimal("300000"), 18, "Pavel", "Ryzhikh",
                "Dmitrievich", "testEmail@test", minorBirthday,
                "1234", "123456");

        MvcResult mvcResult = mockMvc.perform(convertToPostWithJson("/conveyor/offers/1", loanApplicationRequestDTO))
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