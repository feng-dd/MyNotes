---
title: Mysql-1-入门
copyright: true
date: 2019-04-27 21:18:18
tags: MySQL
categories: 数据库
---
## 基本信息
默认端口 *3306*		超级用户 *root*
## 数据类型
- 整型：INT、SMALLINT、BIGINT等
- 浮点型：FLOAT(M,D)、DOUBLE(M,D)；M 总位数、D小数位数
- 日期类型：DATE、DATETIME等；较少使用，一般情况时间使用整型存储
- 字符型：CHAR(M)定长字符串、VARCHAR(M)变长字符串、ENUM('value1','value2',...)枚举
## 基本操作
- mysql -uroot -p		登录
- net start mysql		开启 mysql 服务
- net stop mysql		关闭 mysql 服务	
## 登录后修改提示符
- prompt \h		服务器名称
- prompt \u		用户名称
- prompt \d		DateBase 数据库名称
- prompt \D		Data 日期
- prompt \u@\h\d>		root@localhost(database)>
## SQL语句类型
> DDL:数据定义语言	creat | alter </br>
> TPL:事务处理语言	commit | rollback </br>
> DCL:数据控制语言	great </br>
> DML:数据操作语言	curd </br>
## MySQL 常用命令
- SELECT VERSION();		当前服务器版本
- SELECT USER();		当前用户
- SELECT NOW();			当前时间
- SELECT DATABASE();	当前数据库
## MySQL 语句规范
1. 关键字与函数名称全部大写
2. 数据库名称、表名称、字段名称全部小写
3. SQL语句必须以分号结尾

## MySQL 数据库操作
- SHOW DATABASES;								查看数据库
- CREATE DATABASE [IF NOT EXISTS] dbname;		创建数据库
- SHOW CREATE DATABASE dbname;					查看数据库的建库语句
- ALTER DATABASE dbname CHARACTER SET utf8;		修改数据库的编码为utf8
- DROP DATABASE [IF EXISTS] dbname;				删除数据库
- SHOW WARNINGS;								查看警告信息
- USE dbname;									使用数据库

## MySQL 数据表操作
- SHOW TABLES;						查看当前数据库中的所有表
- SHOW TABLES FROM mysql;				查看 mysql 中的所有表
- SHOW COLUMNS FROM tbname;			查看表结构
- DESC tbname;						查看表结构
- SHOW INDEXS FROM tbname[\G];		查看表的索引,\G网格形式
- SHOW CREATE TABLE tbname;			查看表信息

创建表

```
CREATE TABLE [IF NOT EXISTS] tbname(
id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
columns1 DATATYPE NOT NULL,
columns2 DATATYPE UNIQUE KEY,
pid DATATYPE,
...,
FOREIGN KEY (pid) REFERENCES provinces (id)
);
```
UNSIGNED	非负 	必须在前面写
AUTO_INCREMENT	自增整型：从1开始+1，只能在主键上使用
NOT NULL	非空
PRIMARY KEY	主键约束：每张表只有一个主键，默认非空，唯一性
UNIQUE KEY	唯一约束：唯一性，可为空
FOREIGN KEY	外键约束：数据一致性，完整性，实现“一对多”或“多对一”
新增
新增一个列，可设置在某个列之前或之后，默认在最后，col_name 不填则为最前或最后
ALTER TABLE tbname ADD columns1 DATATYPE ... [FIRST|AFTER col_name];
新增一个或多个列在最后
ALTER TABLE tbname ADD (columns1 DATATYPE ...，columns2 DATATYPE...);
删除
ALTER TABLE tbname DROP columns1，DROP columns2;
(Ps：删除列的同时，新增列；用逗号隔开)
修改
修改列定义(数据类型(大->小，存在数据丢失的情况)、列位置)
ALTER TABLE tbname MODIFY columns1 DATATYPE ... [FIRST|AFTER col_name];
修改列定义(列名称，数据类型(大->小，存在数据丢失的情况)、列位置)
ALTER TABLE tbname CHANGE old_col_name new_col_name DATATYPE ... [FIRST|AFTER col_name];
修改表名称[俩种]
ALTER TABLE tbname RENAME TO new_tbname;
RENAME TABLE tbname TO new_tbname[,tbname2 TO new_tbname2]；
(Ps：表名、列名尽量不要修改，可能会导致引用的存储过程和视图出现错误)

约束
约束类型：非空约束(NOT NULL)，主键约束（PRIMARY KEY），唯一约束（UNIQUE KEY）,默认约束(DEFAULT)，外键约束（FOREIGN KEY）
列级约束：只针对一个列添加约束
表级约束：针对多个列添加约束，不常用，NOT NULL、UNSIGEND 没有表级约束
--添加约束 ftbname:父表
ALTER TABLE tbname ADD 约束 (col_name);
ALTER TABLE tbname ADD PRIMARY KEY (col_name);
ALTER TABLE tbname ADD FOREIGN KEY (col_name) REFERENCES ftbname (id);
ALTER TABLE tbname ADD UNIQUE [INDEX|KEY] (col_name1,col_name2);
--删除约束
ALTER TABLE tbname DROP PRIMARY KEY;
ALTER TABLE tbname DROP {INDEX|KEY} col_name;
ALTER TABLE tbname DROP FOREIGN KEY key_name;
--添加或删除默认约束 literal:默认值
ALTER TABLE tbname ALTER clo_name {SET DEFAULT literal|DROP DEFAULT};

外键约束（FOREIGN KEY）
父表：外键列参照的表
子表：外键列存在的表
1.父表和子表存储引擎必须为InnoDB
2.外键列和参考列必须具有相似的数据类型。数字类型，长度和符号相同；字符型，长度可不同
3.外键列和参照列必须创建索引，外键列会自动创建索引
外键约束的参照操作
CASCADE		随父表删除或更新，自动删除更新子表中匹配的记录
SET NULL	从父表删除或更新行，设置子表的外键列为 null(子表列没有非空约束)
RESTRICT	拒绝对父表的删除和更新操作
NO ACTION	标准 sql 的关键字，mysql 中与 RESTRICT 相同
--添加外键约束的参照，在CREATE TABLE语句中操作
FOREIGN KEY (pid) REFERENCES provinces (id) ON {DELETE|UPDATE} CASCADE

应用：企业开发当中一般不会使用外键，而是使用逻辑外键
物理外键指的是使用foreign key 作为外键关联另一张的字段的连接方法，而且限定了引擎为InnoDB,而逻辑外键，又叫做事实外键，是因为存在语法上的逻辑关联而产生的外键，需要有连接关键词inner join 或者left join 等等和连接部分，也就是on后面的部分,如果需要对应的设置，也可以加上set等语句