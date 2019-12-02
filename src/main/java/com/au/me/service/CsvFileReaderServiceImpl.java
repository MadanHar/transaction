package com.au.me.service;

import com.au.me.model.Transaction;
import com.au.me.model.TransactionType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.au.me.util.Utils.DATE_TIME_FORMATTER;

public class CsvFileReaderServiceImpl implements CsvFileReaderService {

    public static final String TRANSACTIONS_CSV_FILE_PATH = "transaction.txt";
    private static final Logger LOGGER = Logger.getLogger(CsvFileReaderServiceImpl.class.getName());

    @Override
    public List<Transaction> processInput() {
        List<Transaction> transactions = new ArrayList<>();
        List<List<String>> records = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream(TRANSACTIONS_CSV_FILE_PATH)) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    records.add(Arrays.asList(values));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "File not available in resource path");
        }
        for (List<String> lineRecord : records) {
            transactions.add(convertStringToTransaction(lineRecord));
        }
        return transactions;
    }

    private Transaction convertStringToTransaction(final List<String> line) {
        return Transaction.builder()
            .transactionId(line.get(0).trim())
            .fromAccount(line.get(1).trim())
            .toAccount(line.get(2).trim())
            .createdAt(LocalDateTime.parse(line.get(3).trim(), DATE_TIME_FORMATTER))
            .amount(new BigDecimal(line.get(4).trim()))
            .transactionType(TransactionType.valueOf(line.get(5).trim())).build();
    }
}
