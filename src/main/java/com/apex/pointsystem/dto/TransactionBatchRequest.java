package com.apex.pointsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TransactionBatchRequest {

    private Map<String, TransactionRequest[]> transactions;

}
