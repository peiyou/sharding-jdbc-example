package com.xintanyun.service;

import com.xintanyun.entity.Transaction;

import java.util.List;

public interface TransactionService {

    int insertBatch(List<Transaction> transactions);

    int insert(Transaction transaction);

    Transaction selectById(Long id, Long userId);

    List<Transaction> selectByAll();

    List<Transaction> selectByAllLimit(Long userId, int page, int size);

    int update(Transaction transaction);
}
