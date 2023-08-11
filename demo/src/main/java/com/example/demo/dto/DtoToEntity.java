package com.example.demo.dto;

import com.example.demo.entity.*;

import java.util.Optional;

public class DtoToEntity {
    public static Transfer transformTransfer(TransferDto dto) {
        Transfer entity = new Transfer();
        entity.setCreditor(transformCreditor(dto.getCreditorDto()));
        entity.setAmount(dto.getAmount());
        entity.setCurrency(dto.getCurrency());
        entity.setExecutionDate(dto.getExecutionDate());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public static Creditor transformCreditor(CreditorDto dto) {
        Creditor entity = new Creditor();
        entity.setName(dto.getName());
        entity.setAccount(transformAccount(dto.getAccountDto()));
        return entity;
    }

    public static Account transformAccount(AccountDto dto) {
        Account entity = new Account();
        entity.setAccountCode(dto.getAccountCode());
        return entity;
    }

    public static Balance transformBalance(BalanceDto dto) {
        Balance entity = new Balance();
        entity.setBalance(dto.getBalance());
        entity.setAvailableBalance(dto.getAvailableBalance());
        entity.setCurrency(dto.getCurrency());
        entity.setDate(dto.getDate());
        return entity;
    }

    public static Transaction transformTransaction(TransactionDto dto) {
        Transaction entity = new Transaction();
        entity.setTransactionId(dto.getTransactionId());
        entity.setAmount(dto.getAmount());
        entity.setCurrency(dto.getCurrency());
        entity.setDescription(dto.getDescription());
        entity.setAccountingDate(dto.getAccountingDate());
        entity.setOperationId(dto.getOperationId());
        entity.setType(transformType(Optional.ofNullable(dto.getType()).orElse(new TypeDto())));
        entity.setValueDate(dto.getValueDate());
        return entity;
    }

    public static Type transformType(TypeDto dto) {
        Type entity = new Type();
        entity.setEnumeration(dto.getEnumeration());
        entity.setValue(dto.getValue());
        return entity;
    }

    private DtoToEntity() {
        throw new IllegalStateException("Utility class");
    }
}
