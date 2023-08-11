package com.example.demo.service;

import com.example.demo.dto.ResponseTransferDto;
import com.example.demo.dto.TransferDto;
import org.springframework.stereotype.Component;

@Component
public interface TransfersService {
    ResponseTransferDto post(String accountId, TransferDto dto);
}
