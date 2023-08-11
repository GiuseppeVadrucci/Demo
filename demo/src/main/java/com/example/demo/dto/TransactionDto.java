package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String transactionId;
    private String operationId;
    private String accountingDate;
    private String valueDate;
    private TypeDto type;
    private String amount;
    private String currency;
    private String description;
}
