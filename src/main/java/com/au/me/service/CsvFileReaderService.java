package com.au.me.service;

import com.au.me.model.Transaction;

import java.util.List;

public interface CsvFileReaderService {

    List<Transaction> processInput();
}
