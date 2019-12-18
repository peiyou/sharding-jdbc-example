create table if not exists table_id
(
  table_name   varchar(100)        not null
  constraint `PRIMARY`
  primary key,
  current_id   bigint default '1'  not null,
  step_value   int default '10000' not null,
  created_time datetime            not null,
  updated_time datetime            not null
);

create table if not exists trade_0
(
  id             bigint                  not null
  constraint `PRIMARY`
  primary key,
  user_id        bigint                  not null,
  amount         bigint                  not null,
  status         smallint(6) default '1' not null,
  transaction_id bigint                  not null,
  created_time   datetime                not null,
  updated_time   datetime                not null
);

create table if not exists trade_1
(
  id             bigint                  not null
  constraint `PRIMARY`
  primary key,
  user_id        bigint                  not null,
  amount         bigint                  not null,
  status         smallint(6) default '1' not null,
  transaction_id bigint                  not null,
  created_time   datetime                not null,
  updated_time   datetime                not null
);

create table if not exists trade_2
(
  id             bigint                  not null
  constraint `PRIMARY`
  primary key,
  user_id        bigint                  not null,
  amount         bigint                  not null,
  status         smallint(6) default '1' not null,
  transaction_id bigint                  not null,
  created_time   datetime                not null,
  updated_time   datetime                not null
);

create table if not exists trade_3
(
  id             bigint                  not null
  constraint `PRIMARY`
  primary key,
  user_id        bigint                  not null,
  amount         bigint                  not null,
  status         smallint(6) default '1' not null,
  transaction_id bigint                  not null,
  created_time   datetime                not null,
  updated_time   datetime                not null
);

create table if not exists transaction_0
(
  id           bigint       not null
  constraint `PRIMARY`
  primary key,
  user_id      int unsigned not null,
  amount       bigint       not null,
  created_time datetime     not null
);

create table if not exists transaction_1
(
  id           bigint       not null
  constraint `PRIMARY`
  primary key,
  user_id      int unsigned not null,
  amount       bigint       not null,
  created_time datetime     not null
);

create table if not exists transaction_2
(
  id           bigint       not null
  constraint `PRIMARY`
  primary key,
  user_id      int unsigned not null,
  amount       bigint       not null,
  created_time datetime     not null
);

create table if not exists transaction_3
(
  id           bigint       not null
  constraint `PRIMARY`
  primary key,
  user_id      int unsigned not null,
  amount       bigint       not null,
  created_time datetime     not null
);

create table if not exists user
(
  id           int unsigned auto_increment
  constraint `PRIMARY`
  primary key,
  asset        bigint unsigned default '0' not null,
  name         varchar(200)                not null,
  created_time datetime                    not null
);

