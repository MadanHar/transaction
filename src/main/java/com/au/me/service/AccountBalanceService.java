package com.au.me.service;

import org.javatuples.Pair;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface AccountBalanceService {

    Pair<BigDecimal, Integer> getAccountBalance(final String accountId, LocalDateTime fromDate, LocalDateTime toDate);
}
