package com.apex.pointsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents all the transactions in a month.
 */
@Getter
@AllArgsConstructor
public class TransactionResultMonthly {

    private Integer numberOfTransactions;
    private Integer totalNumberOfRewardedPoints;
}
