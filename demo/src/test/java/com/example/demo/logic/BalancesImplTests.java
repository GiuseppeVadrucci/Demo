package com.example.demo.logic;

import com.example.demo.dao.BalanceRepository;
import com.example.demo.dto.BalanceDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.entity.Balance;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BalancesImplTests {

    @Mock
    private BalanceRepository balanceRepository;
    @Mock
    private Balance balance;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private BalancesImpl balances = new BalancesImpl();

    @Test
    void getBalance() {
        ResponseDto dto = new ResponseDto();
        dto.setPayload(new BalanceDto("test", "test", "test", "test"));
        ResponseEntity<ResponseDto> response = new ResponseEntity<>(dto, HttpStatusCode.valueOf(200));
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class), anyString()))
                .thenReturn(response);
        ResponseDto responseDto = balances.get("accountId");
        assertEquals(responseDto.getPayload(),
                Objects.requireNonNull(response.getBody()).getPayload());
    }
}
