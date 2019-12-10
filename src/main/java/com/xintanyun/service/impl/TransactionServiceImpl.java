package com.xintanyun.service.impl;

import com.github.pagehelper.PageHelper;
import com.xintanyun.entity.Transaction;
import com.xintanyun.mapper.TransactionMapper;
import com.xintanyun.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public int insertBatch(List<Transaction> transactions) {
        int num = 0;
        for(Transaction transaction: transactions) {
            num += transactionMapper.insertSelective(transaction);
        }
        return num;
    }

    @Override
    public int insert(Transaction transaction) {
        return transactionMapper.insertSelective(transaction);
    }

    @Override
    public Transaction selectById(Long id, Date createdTime) {
        Example example = new Example(Transaction.class);
        example.createCriteria()
                .andEqualTo("id", id)
                .andEqualTo("createdTime", createdTime);
        return transactionMapper.selectOneByExample(example);
    }

    @Override
    public List<Transaction> selectByAll() {
        return transactionMapper.selectAll();
    }

    @Override
    public List<Transaction> selectByAllLimit(int page, int size) {
        PageHelper.startPage(page, size, false);
        Example example = new Example(Transaction.class);
        example.setOrderByClause(" id desc");
        return transactionMapper.selectByExample(example);
    }

    @Override
    public int update(Transaction transaction) {
        return transactionMapper.updateByPrimaryKeySelective(transaction);
    }
}
