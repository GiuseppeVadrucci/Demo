package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTransactionDto extends ErrorDto {
    private String status;
    private List<String> error;
    private List<TransactionDto> payload;
}


