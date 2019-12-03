package com.au.me;

import com.au.me.model.Transaction;
import com.au.me.service.AccountBalanceService;
import com.au.me.service.AccountBalanceServiceImpl;
import com.au.me.service.CsvFileReaderService;
import com.au.me.service.CsvFileReaderServiceImpl;
import org.javatuples.Pair;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static com.au.me.util.Utils.DATE_TIME_FORMATTER;

public class Main {

    public static void main(String[] args) {
        CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();
        AccountBalanceService accountBalanceService = new AccountBalanceServiceImpl();
        List<Transaction> transactions = csvFileReaderService.processInput();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Account");
            String accountId = scanner.nextLine();

            System.out.println("Enter From Date:");
            String fromDate = scanner.nextLine();

            System.out.println("Enter To Date: ");
            String toDate = scanner.nextLine();


            Pair<BigDecimal, Integer> balanceTuple = accountBalanceService.getAccountBalance(transactions, accountId, LocalDateTime.parse(fromDate,DATE_TIME_FORMATTER), LocalDateTime.parse(toDate,DATE_TIME_FORMATTER));
            System.out.println("Relative Balance for the Period is :"+ balanceTuple.getValue0());
            System.out.println("Number of transactions included is :" + balanceTuple.getValue1());
        }
    }
}
