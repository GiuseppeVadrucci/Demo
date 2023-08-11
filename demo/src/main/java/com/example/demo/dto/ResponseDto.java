package com.example.demo.dto;

import com.example.demo.dao.TransactionsRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private String status;
    private List<String> error;
    private BalanceDto payload;
}

