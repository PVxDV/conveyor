package pvxdv.conveyor.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pvxdv.conveyor.controllers.Convectors.convertToJson;
import static pvxdv.conveyor.controllers.Convectors.convertToPostWithJson;

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

}