package com.example.demo.logic;

import com.example.demo.dao.TransferRepository;
import com.example.demo.dto.*;
import com.example.demo.entity.Transfer;
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
    void getBalance() {
        ResponseTransferDto dto = new ResponseTransferDto();
        dto.setPayload(new TransferDto(new CreditorDto("name", new AccountDto()),
                "test", "test", "test", "test"));
        ResponseEntity<ResponseTransferDto> response = new ResponseEntity<>(dto, HttpStatusCode.valueOf(200));
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class), anyString()))
                .thenReturn(response);
        ResponseTransferDto responseTransferDto = transfers.post("test", dto.getPayload());
        assertEquals(responseTransferDto.getPayload(),
                Objects.requireNonNull(response.getBody()).getPayload());
    }
}
