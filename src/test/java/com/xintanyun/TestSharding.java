package com.xintanyun;

import com.xintanyun.Application;
import com.xintanyun.entity.Transaction;
import com.xintanyun.service.TradeService;
import com.xintanyun.service.TransactionService;
import com.xintanyun.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestSharding {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Test
    public void testInsertBatch() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Date> dates = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();
        try {
            dates.add(simpleDateFormat.parse("2019-10-01 05:05:06"));
            dates.add(simpleDateFormat.parse("2019-11-01 05:05:06"));
            dates.add(simpleDateFormat.parse("2019-12-01 05:05:06"));
            dates.add(simpleDateFormat.parse("2020-01-01 05:05:06"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BigInteger amount = new BigInteger("0");
        for(int i = 0; i < 1000; i++) {
            Transaction transaction = new Transaction();
            transaction.setUserId(1L);
            transaction.setAmount(new BigInteger("500"));
            transaction.setCreatedTime(dates.get(i % 4));
            transactions.add(transaction);
            amount = amount.add(transaction.getAmount());
        }
        tradeService.tradeBatch(1L, amount, transactions);
    }

    @Test
    public void testTrade() {
        BigInteger amount = new BigInteger("100");
        Long userId = 1L;
        tradeService.trade(userId, amount);

    }

    @Test
    public void testTradeError() {
        BigInteger amount = new BigInteger("100");
        Long userId = 1L;
        tradeService.tradeError(userId, amount);
    }

    @Test
    public void testTransactionAll() {
        List<Transaction> list = transactionService.selectByAll();
        for (Transaction transaction : list) {
            System.out.println(transaction.getId() + ", " + transaction.getUserId() + ", " + transaction.getAmount() + ", " + transaction.getCreatedTime());
        }

        System.out.println("==================================");
        System.out.println("==================================");
        List<Transaction> list2 = transactionService.selectByAllLimit(5, 20);
        for (Transaction transaction : list2) {
            System.out.println(transaction.getId() + ", " + transaction.getUserId() + ", " + transaction.getAmount() + ", " + transaction.getCreatedTime());
        }
    }

}
