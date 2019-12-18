package com.xintanyun.service.impl;

import com.xintanyun.entity.TableId;
import com.xintanyun.mapper.TableIdMapper;
import com.xintanyun.service.TableIdService;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TableIdServiceImpl implements TableIdService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private TableIdMapper tableIdMapper;

    private Logger logger = LoggerFactory.getLogger(TableIdService.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long getTransactionNextId() {

        return getNextId("transaction");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long getTradeNextId() {
        return getNextId("trade");
    }

    private synchronized Long getNextId(String tableName) {
        RLock rLock = redissonClient.getLock(tableName + "_next_id_locked");
        boolean holdLock = false;
        try {
            holdLock = rLock.tryLock(10, 3 * 60, TimeUnit.SECONDS);
            if(holdLock) {
                RBucket<TableId> rBucket = redissonClient.getBucket(tableName + "_next_step");
                TableId tableId = rBucket.get();
                RAtomicLong currentValue = redissonClient.getAtomicLong(tableName + "_next_id");
                if(tableId == null || currentValue.get() == 0) {
                    TableId db = this.getTableIdByPrimaryKeyForLocked(tableName);
                    rBucket.set(db);
                    currentValue.set(db.getCurrentId());
                    db.setUpdatedTime(new Date());
                    db.setCurrentId(db.getCurrentId() + db.getStepValue());
                    this.update(db);
                    return currentValue.getAndIncrement();

                } else if(tableId.getCurrentId() - 1 == currentValue.get()) {
                    Long v = currentValue.getAndIncrement();
                    // 应该查询数据库放下一个id了
                    TableId db = this.getTableIdByPrimaryKeyForLocked(tableName);
                    db.setUpdatedTime(new Date());
                    db.setCurrentId(db.getCurrentId() + db.getStepValue());
                    this.update(db);
                    rBucket.set(db);
                    currentValue.set(tableId.getCurrentId());
                    // 返回当前ID，并开始执行子线程
                    return v;
                } else {
                    return currentValue.getAndIncrement();
                }
            }
        } catch (InterruptedException e) {
            logger.error("获取" + tableName + "的下一个ID异常", e);
        } finally {
            if (holdLock) {
                rLock.unlock();
            }
        }
        throw new RuntimeException("获取id失败。");
    }

    @Override
    public TableId getTableIdByPrimaryKeyForLocked(String tableName) {
        Example example = new Example(TableId.class);
        example.setForUpdate(true);
        example.createCriteria()
                .andEqualTo("tableName", tableName);
        return tableIdMapper.selectOneByExample(example);
    }

    @Override
    public int update(TableId tableId) {
        return tableIdMapper.updateByPrimaryKeySelective(tableId);
    }

}
