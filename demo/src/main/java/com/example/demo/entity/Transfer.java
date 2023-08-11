package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transfer")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creditor_id")
    private Creditor creditor;
    private String description;
    private String amount;
    private String currency;
    private String executionDate;
    private String statusCode;

}
