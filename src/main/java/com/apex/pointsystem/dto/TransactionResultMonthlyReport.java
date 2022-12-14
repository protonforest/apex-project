package com.apex.pointsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Report object to combine the results of each monthly transaction result.
 */
@Getter
public class TransactionResultMonthlyReport {

    private Map<String, TransactionResultMonthly> report = new HashMap<>();
}
