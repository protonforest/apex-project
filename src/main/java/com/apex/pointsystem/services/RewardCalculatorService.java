package com.apex.pointsystem.services;

import com.apex.pointsystem.config.PointsConfig;
import com.apex.pointsystem.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RewardCalculatorService {

    private final PointsConfig pointsConfig;

    public TransactionResult calculatePointsRewarded(TransactionRequest transactionRequest) {
        log.info("Calculating points for customer id: " + transactionRequest.getId());
        return calculatePoints(transactionRequest);
    }

    public TransactionResultMonthlyReport calculatePointsForMultipleMonths(TransactionBatchRequest transactionBatchRequest) {
        log.info("Calculating points for multiple months...");
        TransactionResultMonthlyReport transactionResultMonthlyReport = new TransactionResultMonthlyReport();
        transactionBatchRequest.getTransactions().forEach((month, transactions) -> {
            log.info("Calculating points for month: " + month);
            Integer totalRewards = 0;
            for(TransactionRequest transactionRequest : transactions) {
                totalRewards = totalRewards + calculatePoints(transactionRequest).getPointsRewarded();
            }
            TransactionResultMonthly transactionResultMonthly = new TransactionResultMonthly(transactions.length, totalRewards);
            transactionResultMonthlyReport.getReport().put(month, transactionResultMonthly);
        });
        return transactionResultMonthlyReport;
    }


    private TransactionResult calculatePoints(TransactionRequest transactionRequest) {
        Integer totalPointsRewarded = 0;
        Integer transactionAmount = (int) Math.floor(transactionRequest.getTransactionAmount());
        if(transactionRequest.getTransactionAmount() >= 100) {
            totalPointsRewarded = ((transactionAmount - 100) * pointsConfig.getRewardPointsOverOneHundred()) + 50;
        } else {
            if(transactionRequest.getTransactionAmount() > 50) {
                totalPointsRewarded = transactionAmount - 50;
            } else {
                //no points rewarded for transactions less than $50
            }
        }
        log.info("Points rewarded: " + totalPointsRewarded);
        return new TransactionResult(transactionRequest.getId(), totalPointsRewarded);
    }
}
