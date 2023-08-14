package com.example.demo.repository;

import com.example.demo.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Override
    <S extends Balance> S save(S entity);
}

