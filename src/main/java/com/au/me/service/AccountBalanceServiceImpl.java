package com.au.me.service;

import com.au.me.model.Transaction;
import com.au.me.model.TransactionType;
import org.javatuples.Pair;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountBalanceServiceImpl implements AccountBalanceService {

    CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();

    @Override
    public Pair<BigDecimal, Integer> getAccountBalance(String accountId, LocalDateTime fromDate, LocalDateTime toDate) {
        List<Transaction> transactions = csvFileReaderService.processInput();
        transactions.sort((t1, t2) -> {
            if (t1.getCreatedAt().isAfter(t2.getCreatedAt())) {
                return 1;
            }
            //two transactions for an account can never have same createdAt ignoring equals
            return -1;
        });

        final List<Transaction> transactionsInBetween = new ArrayList<>();
        transactions.stream().forEach(transaction -> {
            if (transaction.getFromAccount().equalsIgnoreCase(accountId) || transaction.getToAccount().equalsIgnoreCase(accountId)) {
                if ((transaction.getCreatedAt().isAfter(fromDate) || transaction.getCreatedAt().isEqual(fromDate)) && (transaction.getCreatedAt().isBefore(toDate) || transaction.getCreatedAt().isEqual(toDate))) {
                    transactionsInBetween.add(transaction);
                }
            }
        });

        Pair<BigDecimal, Integer> balanceTuple = Pair.with(getBalance(transactions, accountId), transactions.size());
        return balanceTuple;
    }

    private BigDecimal getBalance(List<Transaction> transactions, final String accountId) {

        final BigDecimal balance = BigDecimal.ZERO;
        transactions.forEach(transaction -> {
            if (transaction.getToAccount().equalsIgnoreCase(accountId)) {
                if (transaction.getTransactionType() == TransactionType.PAYMENT) {
                    balance.add(transaction.getAmount().negate());
                } else {
                    balance.add(transaction.getAmount());
                }
            } else {
                if (transaction.getTransactionType() == TransactionType.REVERSAL) {
                    balance.add(transaction.getAmount());
                } else {
                    balance.add(transaction.getAmount().negate());
                }
            }
        });
        return balance;
    }
}
