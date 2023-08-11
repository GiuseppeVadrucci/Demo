package com.example.demo.dto;

import com.example.demo.entity.Transfer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto extends Transfer {
    private CreditorDto creditorDto;
    private String description;
    private String amount;
    private String currency;
    private String executionDate;
}
