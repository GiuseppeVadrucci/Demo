package com.example.demo.logic;

import com.example.demo.dao.BalanceRepository;
import com.example.demo.dto.BalanceDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.entity.Balance;
import com.example.demo.exceptions.DemoExceptionUnchecked;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
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
    private BalancesImpl balances;

    @Test
    void getBalance() {
        ResponseDto dto = new ResponseDto();
        dto.setPayload(new BalanceDto("test", "test", "test", "test"));
        ResponseEntity<ResponseDto> response = new ResponseEntity<>(dto, HttpStatusCode.valueOf(200));
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class), anyLong()))
                .thenReturn(response);
        ResponseEntity<ResponseDto> responseDto = balances.get(1234L);
        assertEquals(Objects.requireNonNull(responseDto.getBody()).getPayload(),
                Objects.requireNonNull(response.getBody()).getPayload());

    }

    @Test
    void getBalanceError() {
        ResponseDto dto = new ResponseDto();
        dto.setErrorCode("customErrorCode");
        ResponseEntity<ResponseDto> response = new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class), anyLong()))
                .thenThrow(new RuntimeException());
        ResponseEntity<ResponseDto> responseDto = balances.get(1234L);
        assertEquals(Objects.requireNonNull(responseDto.getBody()).getErrorCode(),
                Objects.requireNonNull(response.getBody()).getErrorCode());
        assertEquals(responseDto.getStatusCode(),
                response.getStatusCode());

    }

    void getBalanceEx() {
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class), anyLong()))
                .thenThrow(new RuntimeException());
        assertThrowsExactly(DemoExceptionUnchecked.class, () ->
                balances.get(1234L));
    }
}
