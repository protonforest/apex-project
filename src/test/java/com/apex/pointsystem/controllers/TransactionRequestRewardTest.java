package com.apex.pointsystem.controllers;

import com.apex.pointsystem.dto.TransactionResult;
import com.apex.pointsystem.dto.TransactionRequest;
import com.apex.pointsystem.services.RewardCalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TransactionRequestRewardTest {

    private MockMvc mockMvc;

    @Mock
    private RewardCalculatorService rewardCalculatorService;

    @InjectMocks
    private TransactionRewardController transactionRewardController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionRewardController)
                //.setControllerAdvice()
                //.addFilters()
                .build();
    }

    private final Integer SUCCESSFUL_POINT_RESULT = 100;


    /**
     * Things we do want to test:
     * 1. Controller's HTTP REQUEST interface
     * 2. Controller's HTTP RESPONSE interface
     * 3. Controller's deligation of work to the right service.
     *
     * Things we DONT want to test:
     * 1. If it did the calculation correcrtly.
     */
    @Test
    public void whenCalculateRewards_thenReturn200() throws Exception{

        //setup
        TransactionRequest testTransactionRequest = new TransactionRequest();

        //mock behavior
        when(rewardCalculatorService.calculatePointsRewarded(any())).thenReturn(new TransactionResult("SomeId", 123));

        //execute
        MockHttpServletResponse response = mockMvc.perform(
                        post("/calculateRewards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(buildJsonBodyFromTransaction(testTransactionRequest))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        TransactionResult transactionResult = buildTransactionResultFromJsonBody(response.getContentAsString());

        //assertions
        verify(rewardCalculatorService, times(1)).calculatePointsRewarded(any());
        assertThat(transactionResult.getPointsRewarded()).isEqualTo(123);
        assertThat(transactionResult.getId()).isEqualTo("SomeId");
    }

    private String buildJsonBodyFromTransaction(TransactionRequest transactionRequest) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(transactionRequest);
    }

    private TransactionResult buildTransactionResultFromJsonBody(String transactionJson) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(transactionJson, TransactionResult.class);
    }
}
