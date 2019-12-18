package com.xintanyun.config;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public final class SplitTableRegulationConfig {

    /**
     * 现在有group0组，里面包含db0数据库，db0数据库有4个表(xxxx_0, xxx_1, xxx_2, xxx_3)
     * 各表按user_id分表分库。
     * 表0的user_id范围：0 - 250
     * 表1的user_id范围：250 - 500
     * 表2的user_id范围：500 - 750
     * 表3的user_id范围：750 - 无限（暂时如此）
     *
     * 当数据量再增大时方案：
     * 方案1： 如果只是表3过大，可以再加表，先查找当前表3的最大的user_id（假设当前user_id为2000）。然后在group0组中的db0中加入多张表。如表4（2000 - 3000）、表5 （3000-无限），表3修改范围750 - 2000
     *
     * 方案2：当预计库过大时
     * 新建库，并新建一个group1组。里面可以包含多个db, 假设每个DB中有5张表。
     * 那此时，先查询group0中最大的user_id（假设当前为10w用户），  然后将group0 的范围修改为（1 - 10w）
     * 将group1的范围设置成（10w - 无限）
     * db中的表0（以下称t0）范围 10w - 11w (如果是多个库需要hash的话， 也是如此设置，只是在db的hashvalues字段中，设置好对应的值就可以)
     * t1 范围： 11w-12w ,  t2 范围： 12w - 13w， t3 范围： 13w-14w ...
     * 那么就会有在group0中只有单个库，  然而在group1中有多个库，group1中的db中的t0的范围都是一样的， 只是用 (user_id % group中的所有表数量) 得到db。
     * 这样在group0 / group1组中就变成range + hash的方式。
     *
     * 具体思路可以看：https://blog.csdn.net/weixin_40205234/article/details/89466924
     *
     */
    public static final List<Group> regulation = new ArrayList<>();

    static {
        // 初始为group
        Group group = new Group();
        group.setGroupName("g0");
        group.setStart(0L);
        group.setEnd(Long.MAX_VALUE);
        group.setTableNum(4L); // 四张表

        // 初始化DB
        DB db = new DB();
        db.setDbName("db0");
        Long[] hashValues = {0L, 1L, 2L, 3L};
        db.setHashValues(Lists.newArrayList(hashValues));
        List<Table> tables = new ArrayList<>();
        Table table0 = new Table(0, "table0", 0L, 250L);
        Table table1 = new Table(1, "table1", 250L, 500L);
        Table table2 = new Table(2, "table2", 500L, 750L);
        Table table3 = new Table(3, "table3", 750L, Long.MAX_VALUE);
        tables.add(table0);
        tables.add(table1);
        tables.add(table2);
        tables.add(table3);
        // 设置DB中的table
        db.setTables(tables);

        List<DB> dbs = new ArrayList<>();
        dbs.add(db);
        // 设置db
        group.setDbs(dbs);
        regulation.add(group);
    }

    @Getter
    @Setter
    public static class Group {

        private String groupName;

        private Long start;

        private Long end;

        private Long tableNum;

        private List<DB> dbs;
    }

    @Getter
    @Setter
    public static class DB {

        private String dbName;

        private List<Long> hashValues;

        private List<Table> tables;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Table {

        private Integer tableNo;

        private String tableName;

        private Long start;

        private Long end;

    }

}
