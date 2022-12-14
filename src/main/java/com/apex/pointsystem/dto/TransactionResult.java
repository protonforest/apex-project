package com.apex.pointsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TransactionResult {
    String id;
    Integer pointsRewarded;
}
