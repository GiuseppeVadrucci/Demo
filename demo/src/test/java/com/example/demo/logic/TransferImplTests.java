package com.example.demo.logic;

import com.example.demo.dto.AccountDto;
import com.example.demo.dto.CreditorDto;
import com.example.demo.dto.ResponseTransferDto;
import com.example.demo.dto.TransferDto;
import com.example.demo.entity.Transfer;
import com.example.demo.repository.TransferRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TransferImplTests {

    @Mock
    private TransferRepository transferRepository;
    @Mock
    private Transfer transfer;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private TransfersImpl transfers = new TransfersImpl();


    @Test
    void getTransfer() {
        ResponseTransferDto dto = new ResponseTransferDto();
        dto.setPayload(new TransferDto(new CreditorDto("name", new AccountDto()),
                "test", "test", "test", "test"));
        ResponseEntity<ResponseTransferDto> mResponse = new ResponseEntity<>(dto, HttpStatusCode.valueOf(200));
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class), anyLong()))
                .thenReturn(mResponse);
        ResponseEntity<ResponseTransferDto> response = transfers.post(1234L, dto.getPayload());
        assertEquals(Objects.requireNonNull(response.getBody()).getPayload(),
                Objects.requireNonNull(mResponse.getBody()).getPayload());
    }

    @Test
    void getTransferError() {
        ResponseTransferDto dto = new ResponseTransferDto();
        dto.setErrorCode("customErrorCode");
        ResponseEntity<ResponseTransferDto> mResponse = new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class), anyLong()))
                .thenThrow(new RuntimeException());
        ResponseEntity<ResponseTransferDto> response = transfers.post(1234L, dto.getPayload());
        assertEquals(Objects.requireNonNull(mResponse.getBody()).getErrorCode(),
                Objects.requireNonNull(response.getBody()).getErrorCode());
        assertEquals(mResponse.getStatusCode(),
                response.getStatusCode());
    }

}
