package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface BalancesService {
    ResponseEntity<ResponseDto> get(Long accountId);
}
