package pvxdv.conveyor.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import pvxdv.conveyor.calculators.MonthlyPaymentCalculator;
import pvxdv.conveyor.calculators.RateCalculator;
import pvxdv.conveyor.dto.LoanApplicationRequestDTO;
import pvxdv.conveyor.dto.LoanOfferDTO;
import pvxdv.conveyor.mapper.Mapper;
import pvxdv.conveyor.services.PreScoringService;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;



@ExtendWith(MockitoExtension.class)
class PreScoringControllerTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    PreScoringService preScoringService;
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