package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface BalancesService {
    ResponseDto get(String accountId);
}
