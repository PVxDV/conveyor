package pvxdv.conveyor.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import pvxdv.conveyor.services.PreScoringServiceImpl;

@ExtendWith(MockitoExtension.class)
class PreScoringControllerTest {
    @Mock
    PreScoringServiceImpl preScoringServiceImpl;
    @InjectMocks
    PreScoringController preScoringController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getOffers() {
    }
}