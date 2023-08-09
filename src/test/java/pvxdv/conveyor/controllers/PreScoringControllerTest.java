package pvxdv.conveyor.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pvxdv.conveyor.calculators.MonthlyPaymentCalculator;
import pvxdv.conveyor.calculators.PskCalculator;
import pvxdv.conveyor.calculators.RateCalculator;
import pvxdv.conveyor.dto.*;
import pvxdv.conveyor.dto.enums.EmploymentStatus;
import pvxdv.conveyor.dto.enums.Gender;
import pvxdv.conveyor.dto.enums.MaritalStatus;
import pvxdv.conveyor.dto.enums.Position;
import pvxdv.conveyor.mapper.Mapper;
import pvxdv.conveyor.services.PreScoringServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PreScoringControllerTest {
    @Mock
    PreScoringServiceImpl preScoringServiceImpl;
    @InjectMocks
    PreScoringController preScoringController;

    List<LoanOfferDTO> loanOfferDTOList;
    LoanApplicationRequestDTO loanApplicationRequestDTO;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        loanOfferDTOList = new ArrayList<>();
        loanOfferDTOList.add(new LoanOfferDTO(1L, new BigDecimal("300000"), new BigDecimal("344551.32"),
                18, new BigDecimal("19141.74"), new BigDecimal("18"), false, false));
        loanOfferDTOList.add(new LoanOfferDTO(1L, new BigDecimal("300000"), new BigDecimal("336877.92"),
                18, new BigDecimal("18715.44"), new BigDecimal("15"), false, false));
        loanOfferDTOList.add(new LoanOfferDTO(1L, new BigDecimal("300000"), new BigDecimal("334791.54"),
                18, new BigDecimal("18599.53"), new BigDecimal("12"), false, false));
        loanOfferDTOList.add(new LoanOfferDTO(1L, new BigDecimal("300000"), new BigDecimal("332246.88"),
                18, new BigDecimal("18458.16"), new BigDecimal("11"), false, false));

        loanApplicationRequestDTO = new LoanApplicationRequestDTO(new BigDecimal("300000"), 18, "Pavel","Ryzhikh",
                "Dmitrievich", "testEmail@test", LocalDate.of(1993, 3, 18),
                "1234", "123456");

        mockMvc = MockMvcBuilders.standaloneSetup(preScoringController).build();


    }

//    @Test
//    void getOffers() throws Exception {
//        when(preScoringServiceImpl.generateAvailableOffers(any(), any())).thenReturn(loanOfferDTOList);
//
//        mockMvc.perform(putJson("/conveyor/offers/1", loanApplicationRequestDTO)
//               // .header("test")
//                )
//                .andExpect(status().is2xxSuccessful());



//                        (post("/conveyor/offers/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
//                .content(
//                .header("requestForAnOffer"))
//                .andExpect(status().is2xxSuccessful());




//                       """
//                                [
//                                  {
//                                    "applicationId": 1,
//                                    "requestedAmount": 300000,
//                                    "totalAmount": 344551.32,
//                                    "term": 18,
//                                    "monthlyPayment": 19141.74,
//                                    "rate": 18,
//                                    "isInsuranceEnabled": false,
//                                    "isSalaryClient": false
//                                  },
//                                  {
//                                    "applicationId": 1,
//                                    "requestedAmount": 300000,
//                                    "totalAmount": 336877.92,
//                                    "term": 18,
//                                    "monthlyPayment": 18715.44,
//                                    "rate": 15,
//                                    "isInsuranceEnabled": false,
//                                    "isSalaryClient": true
//                                  },
//                                  {
//                                    "applicationId": 1,
//                                    "requestedAmount": 300000,
//                                    "totalAmount": 334791.54,
//                                    "term": 18,
//                                    "monthlyPayment": 18599.53,
//                                    "rate": 12,
//                                    "isInsuranceEnabled": true,
//                                    "isSalaryClient": false
//                                  },
//                                  {
//                                    "applicationId": 1,
//                                    "requestedAmount": 300000,
//                                    "totalAmount": 332246.88,
//                                    "term": 18,
//                                    "monthlyPayment": 18458.16,
//                                    "rate": 11,
//                                    "isInsuranceEnabled": true,
//                                    "isSalaryClient": true
//                                  }
//                                ]
//                                """




    public static MockHttpServletRequestBuilder putJson(String uri, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return put(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}