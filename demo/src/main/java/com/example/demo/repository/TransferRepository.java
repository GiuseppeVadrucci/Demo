package com.example.demo.repository;

import com.example.demo.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Override
    <S extends Transfer> S save(S entity);
}

