package com.xintanyun;

import com.xintanyun.entity.Transaction;
import com.xintanyun.entity.User;
import com.xintanyun.service.TradeService;
import com.xintanyun.service.TransactionService;
import com.xintanyun.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    public void testInsertUser() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            User user = new User();
            user.setName("张三：" + i);
            user.setAsset(new BigInteger("0"));
            user.setCreatedTime(new Date());
            users.add(user);
        }
        userService.insertBatch(users);
    }

    @Test
    public void testInsertBatch() {
        Random random = new Random();
        for (int i=0; i<300; i++) {
            int id = random.nextInt(1000) + 3;
            tradeService.trade(Long.valueOf(id), new BigInteger(String.valueOf(30 * random.nextInt(100))));
        }
    }

    @Test
    public void testTrade() {
        BigInteger amount = new BigInteger("100");
        Long userId = 3L;
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
        List<Transaction> list2 = transactionService.selectByAllLimit(20L ,5, 20);
        for (Transaction transaction : list2) {
            System.out.println(transaction.getId() + ", " + transaction.getUserId() + ", " + transaction.getAmount() + ", " + transaction.getCreatedTime());
        }
    }


}
