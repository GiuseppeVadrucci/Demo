package com.example.demo.service;

import com.example.demo.dto.ResponseTransferDto;
import com.example.demo.dto.TransferDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface TransfersService {
    ResponseEntity<ResponseTransferDto> post(Long accountId, TransferDto dto);
}
