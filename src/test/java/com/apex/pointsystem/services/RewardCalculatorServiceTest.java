package com.apex.pointsystem.services;

import com.apex.pointsystem.config.PointsConfig;
import com.apex.pointsystem.dto.TransactionBatchRequest;
import com.apex.pointsystem.dto.TransactionResult;
import com.apex.pointsystem.dto.TransactionRequest;
import com.apex.pointsystem.dto.TransactionResultMonthlyReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RewardCalculatorServiceTest {

    private RewardCalculatorService rewardCalculatorService;
    String id = "SomeRandomId123";

    @Mock
    private PointsConfig pointsConfig;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        rewardCalculatorService = new RewardCalculatorService(pointsConfig);
        when(pointsConfig.getRewardPointsOverOneHundred()).thenReturn(2);
    }

    @Test
    public void whenCalcuatePointsRewarded_andTransactionAmountIsOver100_thenReturnCorrectResult() {

        //setup
        Integer transactionAmount = 250;
        TransactionRequest transactionRequest = new TransactionRequest(id, (long) transactionAmount);

        //execution
        TransactionResult transactionResult = rewardCalculatorService.calculatePointsRewarded(transactionRequest);

        //assertions
        assertThat(transactionResult.getId()).isEqualTo(transactionRequest.getId());
        assertThat(transactionResult.getPointsRewarded()).isEqualTo(350);
    }

    @Test
    public void whenCalcuatePointsRewarded_andTransactionAmountIsUnder50_thenReturnCorrectResult() {

        //setup
        Integer transactionAmount = 50;
        TransactionRequest transactionRequest = new TransactionRequest(id, (long) transactionAmount);

        //execution
        TransactionResult transactionResult = rewardCalculatorService.calculatePointsRewarded(transactionRequest);

        //assertions
        assertThat(transactionResult.getId()).isEqualTo(transactionRequest.getId());
        assertThat(transactionResult.getPointsRewarded()).isEqualTo(0);
    }

    @Test
    public void whenCalcuatePointsRewarded_andTransactionAmountIsBetween50And100_thenReturnCorrectResult() {

        //setup
        Integer transactionAmount = 75;
        TransactionRequest transactionRequest = new TransactionRequest(id, (long) transactionAmount);

        //execution
        TransactionResult transactionResult = rewardCalculatorService.calculatePointsRewarded(transactionRequest);

        //assertions
        assertThat(transactionResult.getId()).isEqualTo(transactionRequest.getId());
        assertThat(transactionResult.getPointsRewarded()).isEqualTo(25);
    }

    @Test
    public void whenCalculatePointsForMultipleMonths_thenReturnCorrectResults() {

        //setup
        TransactionBatchRequest transactionBatchRequest = new TransactionBatchRequest();
        transactionBatchRequest.setTransactions(new HashMap<>());
        TransactionRequest[] arrayOfTransactionRequests = new TransactionRequest[1];
        arrayOfTransactionRequests[0] = new TransactionRequest("someRandomId", 123L);
        transactionBatchRequest.getTransactions().put("January", arrayOfTransactionRequests);

        //execution
        TransactionResultMonthlyReport report = rewardCalculatorService.calculatePointsForMultipleMonths(transactionBatchRequest);

        //assertions
        assertThat(report.getReport().get("January").getTotalNumberOfRewardedPoints()).isEqualTo(96);
        assertThat(report.getReport().get("January").getNumberOfTransactions()).isEqualTo(1);
    }

}
