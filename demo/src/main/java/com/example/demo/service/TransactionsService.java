package com.example.demo.service;

import com.example.demo.dto.ResponseTransactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface TransactionsService {
    ResponseEntity<ResponseTransactionDto> getTransactions(Long accountId, String fromAccountingDate,
                                                           String toAccountingDate);
}
