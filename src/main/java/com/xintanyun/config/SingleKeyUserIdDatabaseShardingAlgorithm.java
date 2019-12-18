package com.xintanyun.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class SingleKeyUserIdDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> databaseNames, PreciseShardingValue<Long> shardingValue) {
        // 选择group
        for (SplitTableRegulationConfig.Group group : SplitTableRegulationConfig.regulation) {
            if(group.getStart() <= shardingValue.getValue() && shardingValue.getValue() < group.getEnd()) {
                // 选择DB
                for (SplitTableRegulationConfig.DB db : group.getDbs()) {
                    if (db.getHashValues().contains(shardingValue.getValue() % group.getTableNum())) {
                        return db.getDbName();
                    }
                }
            }
        }
        throw new UnsupportedOperationException();
    }
}
