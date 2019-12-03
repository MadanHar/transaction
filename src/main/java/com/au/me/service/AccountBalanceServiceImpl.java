package com.au.me.service;

import com.au.me.model.Transaction;
import com.au.me.model.TransactionType;
import org.javatuples.Pair;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountBalanceServiceImpl implements AccountBalanceService {



    @Override
    public Pair<BigDecimal, Integer> getAccountBalance( final List<Transaction> transactions, final String accountId, final LocalDateTime fromDate, final LocalDateTime toDate) {
        transactions.sort((t1, t2) -> {
            if (t1.getCreatedAt().isAfter(t2.getCreatedAt())) {
                return 1;
            }
            //two transactions for an account can never have same createdAt ignoring equals
            return -1;
        });

        final List<Transaction> transactionsInBetween = new ArrayList<>();
        transactions.stream().forEach(transaction -> {
            if (transaction.getFromAccount().contentEquals(accountId) || transaction.getToAccount().contentEquals(accountId)) {
                if ((transaction.getCreatedAt().isAfter(fromDate) || transaction.getCreatedAt().isEqual(fromDate)) && (transaction.getCreatedAt().isBefore(toDate) || transaction.getCreatedAt().isEqual(toDate))) {
                    transactionsInBetween.add(transaction);
                }
            }
        });

        Pair<BigDecimal, Integer> balanceTuple = Pair.with(getBalance(transactionsInBetween, accountId), transactionsInBetween.size());
        return balanceTuple;
    }

    private BigDecimal getBalance(List<Transaction> transactions, final String accountId) {

         BigDecimal balance = new BigDecimal("0");
        for(Transaction transaction : transactions) {
            if (transaction.getFromAccount().contentEquals(accountId)) {
                if (transaction.getTransactionType() == TransactionType.PAYMENT) {
                    balance = balance.add(transaction.getAmount().negate());
                } else {
                    balance = balance.add(transaction.getAmount());
                }
            } else {
                if (transaction.getTransactionType() == TransactionType.REVERSAL) {
                    balance = balance.add(transaction.getAmount());
                } else {
                    balance = balance.add(transaction.getAmount().negate());
                }
            }
        }
        return balance;
    }
}
