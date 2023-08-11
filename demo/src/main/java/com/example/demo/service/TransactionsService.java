package com.example.demo.service;

import com.example.demo.dto.ResponseTransactionDto;
import org.springframework.stereotype.Component;

@Component
public interface TransactionsService {
    ResponseTransactionDto getTransactions(String accountId);
}
