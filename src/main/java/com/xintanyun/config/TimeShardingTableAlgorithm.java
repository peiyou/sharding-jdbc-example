package com.xintanyun.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class TimeShardingTableAlgorithm implements PreciseShardingAlgorithm<Date> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String tablePrefix = preciseShardingValue.getLogicTableName();
        sb.append(tablePrefix).append("_").append(format.format(preciseShardingValue.getValue()));
        return sb.toString();
    }
}
