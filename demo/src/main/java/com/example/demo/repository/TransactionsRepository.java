package com.example.demo.repository;

import com.example.demo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

    @Override
    <S extends Transaction> S save(S entity);

    @Override
    <S extends Transaction> S saveAndFlush(S entity);

    @Override
    <S extends Transaction> List<S> saveAll(Iterable<S> entities);
}

