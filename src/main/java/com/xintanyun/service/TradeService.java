package com.xintanyun.service;

import com.xintanyun.entity.Trade;
import com.xintanyun.entity.Transaction;

import java.math.BigInteger;
import java.util.List;

public interface TradeService {

    void trade(Long userId, BigInteger amount);

    void tradeError(Long userId, BigInteger amount);

    void tradeBatch(Long userId, BigInteger amount, List<Transaction> transactions, List<Trade> trades);

    void tradeBatch2(List<Long> userIds, List<BigInteger> amounts, List<Transaction> transactions, List<Trade> trades);
}
