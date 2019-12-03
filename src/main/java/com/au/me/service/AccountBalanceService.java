package com.au.me.service;

import com.au.me.model.Transaction;
import org.javatuples.Pair;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface AccountBalanceService {

    Pair<BigDecimal, Integer> getAccountBalance(final List<Transaction> transactions, final String accountId, final LocalDateTime fromDate, final LocalDateTime toDate);
}
