package com.apex.pointsystem.controllers;

import com.apex.pointsystem.dto.TransactionBatchRequest;
import com.apex.pointsystem.dto.TransactionResult;
import com.apex.pointsystem.dto.TransactionRequest;
import com.apex.pointsystem.dto.TransactionResultMonthlyReport;
import com.apex.pointsystem.services.RewardCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TransactionRewardController {

    private final RewardCalculatorService rewardCalculatorService;

    @PostMapping("/calculateRewards")
    public TransactionResult calculateRewards(@RequestBody TransactionRequest transactionRequest) {
        return rewardCalculatorService.calculatePointsRewarded(transactionRequest);
    }


    @PostMapping("/calculateRewardsForMonths")
    public TransactionResultMonthlyReport calculateRewardsForMultipleTransactions(@RequestBody TransactionBatchRequest transactionBatchRequest) {
        return rewardCalculatorService.calculatePointsForMultipleMonths(transactionBatchRequest);
    }

}
