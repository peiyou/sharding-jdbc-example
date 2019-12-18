package com.xintanyun.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class SingleKeyUserIdTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        // 选择group
        for (SplitTableRegulationConfig.Group group : SplitTableRegulationConfig.regulation) {
            if(group.getStart() <= shardingValue.getValue() && shardingValue.getValue() < group.getEnd()) {
                for (SplitTableRegulationConfig.DB db : group.getDbs()) {
                    if (db.getHashValues().contains(shardingValue.getValue() % group.getTableNum())) {
                        for (SplitTableRegulationConfig.Table table : db.getTables()) {
                            if(table.getStart() <= shardingValue.getValue() && shardingValue.getValue() < table.getEnd()) {
                                return shardingValue.getLogicTableName() + "_" + table.getTableNo();
                            }
                        }
                    }
                }
            }
        }

        throw new UnsupportedOperationException();
    }
}
