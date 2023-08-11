package com.example.demo.dao;

import com.example.demo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

    @Override
    <S extends Transaction> S save(S entity);

    @Override
    <S extends Transaction> S saveAndFlush(S entity);
}

