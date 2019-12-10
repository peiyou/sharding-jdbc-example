package com.xintanyun.service.impl;

import com.xintanyun.entity.Transaction;
import com.xintanyun.entity.User;
import com.xintanyun.service.TradeService;
import com.xintanyun.service.TransactionService;
import com.xintanyun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void trade(Long userId, BigInteger amount) {
        // 查询用户
        User user = userService.selectByUserIdForLock(userId);
        // 更新金额
        user.setAsset(user.getAsset().add(amount));
        userService.updateById(user);
        // 创建交易
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(amount);
        transaction.setCreatedTime(new Date());

        transactionService.insert(transaction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void tradeError(Long userId, BigInteger amount) {
        // 查询用户
        User user = userService.selectByUserIdForLock(userId);
        // 更新金额
        user.setAsset(user.getAsset().add(amount));
        userService.updateById(user);
        // 创建交易
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(amount);
        transaction.setCreatedTime(new Date());

        transactionService.insert(transaction);
        throw new RuntimeException("交易出现异常时.");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void tradeBatch(Long userId, BigInteger amount, List<Transaction> transactions) {
        User user = userService.selectByUserIdForLock(userId);
        user.setAsset(user.getAsset().add(amount));
        userService.updateById(user);
        transactionService.insertBatch(transactions);
    }
}
