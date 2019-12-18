package com.xintanyun.service.impl;

import com.github.pagehelper.PageHelper;
import com.xintanyun.entity.Transaction;
import com.xintanyun.mapper.TransactionMapper;
import com.xintanyun.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public int insertBatch(List<Transaction> transactions) {
        return transactionMapper.insertList(transactions);
    }

    @Override
    public int insert(Transaction transaction) {
        return transactionMapper.insertSelective(transaction);
    }

    @Override
    public Transaction selectById(Long id, Long userId) {
        Example example = new Example(Transaction.class);
        example.createCriteria()
                .andEqualTo("id", id)
                .andEqualTo("userId", userId);
        return transactionMapper.selectOneByExample(example);
    }

    @Override
    public List<Transaction> selectByAll() {
        return transactionMapper.selectAll();
    }

    @Override
    public List<Transaction> selectByAllLimit(Long userId, int page, int size) {
        PageHelper.startPage(page, size, false);
        Example example = new Example(Transaction.class);
        example.setOrderByClause(" id desc");
        example.createCriteria()
                .andEqualTo("userId", userId);
        return transactionMapper.selectByExample(example);
    }

    @Override
    public int update(Transaction transaction) {
        Example example = new Example(Transaction.class);
        example.createCriteria()
                .andEqualTo("userId", transaction.getUserId())
                .andEqualTo("id", transaction.getId());
        return transactionMapper.updateByExampleSelective(transaction, example);
    }
}
