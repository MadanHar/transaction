package com.au.me.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Transaction {

    private String transactionId;

    private String fromAccount;

    private String toAccount;

    private LocalDateTime createdAt;

    private TransactionType transactionType;

    private BigDecimal amount;

}
