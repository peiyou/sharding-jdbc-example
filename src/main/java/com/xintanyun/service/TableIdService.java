package com.xintanyun.service;

import com.xintanyun.entity.TableId;

public interface TableIdService {

    Long getTransactionNextId();

    Long getTradeNextId();

    TableId getTableIdByPrimaryKeyForLocked(String tableName);

    int update(TableId tableId);
}
