package com.example.demo.logic;

import com.example.demo.dao.TransactionsRepository;
import com.example.demo.dto.ResponseTransactionDto;
import com.example.demo.dto.TransactionDto;
import com.example.demo.dto.TypeDto;
import com.example.demo.entity.Transaction;
import com.example.demo.exceptions.DemoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TransactionsImplTests {

    @Mock
    private TransactionsRepository transactionsRepository;
    @Mock
    private Transaction transaction;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private TransactionsImpl transactionImpl = new TransactionsImpl();

    @Test
    void getBalance() throws DemoException {
        ResponseTransactionDto dto = new ResponseTransactionDto();
        dto.setPayload(new TransactionDto("test", "test", "test", "test",
                new TypeDto(), "test", "test", "test"));
        ResponseEntity<ResponseTransactionDto> mResponse = new ResponseEntity<>(dto, HttpStatusCode.valueOf(200));
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class)))
                .thenReturn(mResponse);
        ResponseEntity<ResponseTransactionDto> response = transactionImpl.getTransactions(1234L);
        assertEquals(Objects.requireNonNull(response.getBody()).getPayload(),
                Objects.requireNonNull(response.getBody()).getPayload());
    }
}
