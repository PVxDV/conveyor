package pvxdv.conveyor.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;
import pvxdv.conveyor.services.PreScoringServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



@ExtendWith(MockitoExtension.class)
class PreScoringControllerTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    PreScoringServiceImpl preScoringServiceImpl;
    @InjectMocks
    PreScoringController preScoringController;
    MockMvc mockMvc;
    List<LoanOfferDTO> result;
    LoanApplicationRequestDTO loanApplicationRequestDTO;

    @BeforeEach
    void setUp() {

        result = new ArrayList<>();
        result.add(new LoanOfferDTO(10l, BigDecimal.ONE, BigDecimal.ONE, 60,
                BigDecimal.TEN, BigDecimal.ONE, true, false));
        result.add(new LoanOfferDTO(10l, BigDecimal.ONE, BigDecimal.ONE, 60,
                BigDecimal.TEN, BigDecimal.ONE, true, false));
        result.add(new LoanOfferDTO(10l, BigDecimal.ONE, BigDecimal.ONE, 60,
                BigDecimal.TEN, BigDecimal.ONE, true, false));

        mockMvc = MockMvcBuilders.standaloneSetup(preScoringController).build();
    }

    @Test
    void getOffers() {
//        todo
    }

}