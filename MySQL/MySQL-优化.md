# `SQL`

1.创建员工信息表

```sql
CREATE TABLE `staff` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `age` int(3) DEFAULT '0',
  `phone` varchar(11) DEFAULT NULL,
  `salary` double(8,2) DEFAULT '0.00',
  `entryTm` date DEFAULT NULL,
  `headId` int(11) DEFAULT '0',
  `updateTm` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO staff (id, name, age, phone, salary, entryTm, headId, updateTm) VALUES ('7', '冯大大', '24', '12345678901', '7999.00', '2018-12-17', '0', NULL);

ALTER TABLE staff ADD INDEX index_name(col_name[,col_name2,...]);
ALTER TABLE staff DORP INDEX index_name;

ALTER TABLE staff modify col_name datatype 约束
```

2.查询18年1月1日到18年12月31日中，每个月入职的总人数，并按月份排序

```sql
select month(entryTm) as '月份',count(*) as '人数' from staff 
where entryTm > '2018-01-01' and entryTm < '2018-12-31' 
group by month(entryTm) 
order by month(entryTm);
```



3.创建有映射关系的员工实体类
省略...

4.从数据库中查询出入职时间前100名的员工的数据，通过Java按照工资的降序排序。

方式1. 创建员工实体类，继承 Comparable 接口，实现 compareTo() 方法。使用 TreeMap 遍历自排序；

```java
class Staff implements Comparable<Staff>{
	private String name;
	private int age;
	private String phone;
	private double salary;
	private Date entryTm;
	// get、set 方法省略

    // 重写 compareTo()
    @Override
    public int compareTo(Staff s){
        //升序
        return this.salary > s.getSalary() ? 1 : this.salary = s.getSalary() ? 0 : -1;
        //降序
        return this.salary > s.getSalary() ? -1 : this.salary = s.getSalary() ? 0 : 1;
    }
}
```


```java
public static void main(String[] args){
	// JDBC
	Class.forName("mysql驱动");
	Connection conn = DriverManager.getConnect(url,user,password);
	// PreparedStatement ps = conn.preparedStatement(sql);
	Statement stat = conn.createStatement();
	String sql = "select * from staff order by entryTm limit 100"
	Result rs = stat.excuteQuery(sql);
	// 遍历，存放到 Staff 对象
	TreeMap<Staff,String> ss = new TreeMap<Staff,String>();
	while(rs.next()){
		Staff s = new Staff();
		s.setXX(rs.getString("XX"));
		...
		ss.put(s,s.getName());
	}
	// 获取key值
	Set<Person> keys = ss.keySet();
	// 获取key值对应的值
	for(Staff key : keys){
		System.out.print(key.getName() + "的工资为" + key.getSalary());
	}
}
```



方式2. 创建一个比较器类，实现 `Comparator` 接口，重写 `compare()`，然后对象放入集合遍历时，可以使用比较器对象，对对象进行排序(`collection.sort(ss,myComparator)`)

```java
public static void main(String[] args){
	// JDBC
	Class.forName("mysql驱动");
	Connection conn = DriverManager.getConnect(url,user,password);
	// PreparedStatement ps = conn.preparedStatement(sql);
	Statement stat = conn.createStatement();
	String sql = "select * from staff order by entryTm limit 100"
	Result rs = stat.excuteQuery(sql);
	// 遍历，存放到 Staff 对象
	List<Staff> ss = new ArrayList<Staff>();
	while(rs.next()){
		Staff s = new Staff();
		s.setXX(rs.getString("XX"));
		...
		ss.add(s);
	}
	Collections.sort(ss,new Comparator<Staff>(){
		@Override
		public int compare(Staff s1,Staff s2){
			Decimal salary1 = s1.salary;
			Decimal salary2 = s2.salary;
			return salary1 > salary2 ? 1 : (salary1==salary2) ? 0:-1;		
		}	
	});
}
```



# 优化

1.查看慢查询相关参数

`show variables like '%query%'`

2.开启慢查询日志

`set global slow_query_log = 'ON'`

3.设置进入慢查询日志的查询时间（秒）

`set global long_query_time = 1`

4.日志的存储方式:`FILE TABLE` ,推荐 `FILE`(文件)，`TABLE(mysql.slow_log表)`更耗费系统资源。可以同时开启，设置为 `FILE,TABLE`

`show variables like 'log_output'`

5.开启后,未使用索引的查询也被记录到慢查询日志中

show variables like 'log_queries_not_using_indexes';

6.分析 `sql` 执行计划

`explain select * from staff`;

> type 扫描类型  从好到差

- `const`:是一个常数查找，一般是主键和唯一索引查找		`where id = 1`
- `eq_reg`：主键和唯一索引的范围查找	
- `ref`：连接的查找，一般一个表是基于某一个索引的查找		`where index_col = "7999"`
- `range`：基于索引的范围查找							`where id > 1;`
- `index`：基于索引的扫描								`where index_col > "7999"` 或 `select index_col`
- `all`：基于表扫描	

> Extra 出现前俩种情况，需进行优化

- `Using filesort` (文件排序，对结果排序)
- `Using temporary` (使用临时表)
- `Using where` (使用到`where`来过虑数据,除对索引进行`=`访问 `where id = 1`)
- `Using index` (只用到索引,避免了表扫描)

7.`SQL` 优化建议

- 使用分页查询。减少读取得数据，提高查询速度。
- 大`SQL`尽量拆分为小`SQL`，减少锁时间。
- 避免`select *`，指定需要的列名。减少读取得数据，提高查询速度。
- 使用连接(join)代替子查询。避免创建临时表。
- 列尽量加上非空约束 `NOT NULL`。
- `where` 多条件时，将数据少的条件放前面。减少后一个条件的查询时间。
- `where` 子句中，使用`!=、<>、内置函数、算数运算、like '%xxx%'`时，索引无效，进行全表扫描。`like 'xxx%'`有效。

# 索引

聚集索引：叶子节点的数据域存放的是完整的数据记录；换句话说,数据文件和索引是绑定。如 `Innodb` 存储引擎的主键索引。
稀疏索引：叶子节点的数据域存放的是数据文件的指针；换句话说,索引文件存放了指向数据文件的地址。如 `InnoDB` 存储引擎的辅助索引、`MyISAM` 存储引擎的主键索引和辅助索引。

主键索引：在生成主键时就有的索引。
辅助索引：人为新建的索引。

`InnoDB` 存储引擎：主键索引为聚集索引，叶子节点存储了数据；辅助索引为稀疏索引，叶子节点存放对应主键；当查询时，通过查询到的辅助索引的主键，查询主键对应的主键索引的数据。
`MyISAM` 存储引擎：主键索引和辅助索引的叶子节点存储的是数据行的物理地址。

联合索引：`ALTER TABLE tb_name ADD INDEX index_name(col_name1,col_name2,...);` 一个索引包含多个列。相较于维护多个单个索引的开销低
最左匹配原则：自 `col_name1 ~ col_namen` 依次查找，当左边的字段未作为判断条件时，就不会再去执行接下来的索引。

建立合理的索引：
- 数据类型要小：占用空间少，`I/O`效率高
- 数据类型简单：整型比字符型简单，开销小；时间最好使用 `Mysql`内置的日期/时间类型，而不是字符串；`IP` 使用整型存储。
- 避免 `NULL`：含空值的列很难进行查询优化。尽量使用`DEFAULT`给字段赋予0、空串、或特殊值。
- 尽量少使用二进制类型(`bolg`、`test`)。


# `Mysql` 存储引擎的区别

1.`innoDB`支持事务，且默认是`Autocommit`，所以每一条`SQL`语句都会封装成一个事务。如果执行多条事务，最好加上`begin`和`commit`；
  `MyISAM`不支持事务，也就无法回滚。

2.`InnoDB`支持行级锁；当没有用到索引时，会使用表级锁；当用到索引时，用到行级锁和`Gap`锁，使用普通非唯一索引时，使用`GAP`锁
  `MyISAM`进行写操作会全表上锁，所以`MyISAM`的写操作性能会差些。

3.在查询较多的表中，使用 `MyISAM` 较优；
  写比较多的表，使用 `InnoDB`；

# `Mysql `优化

`mysql`配置文件：

- `windows`: `/安装目录/my.ini`
- `linux`: `/etc/mysql/my.cnf`

1.连接请求
- `max_connections`			`MySQL`的最大连接数				并发连接请求量比较大,适当调高此值,增加并行连接数量
- `back_log`					`MySQL`能暂存堆栈中的连接数量	默认数值是50，可调优为128，对于Linux系统设置范围为小于512的整数
- `interactive_timeout`		连接关闭后休息时间			默认数值是28800，可调优为7200

2.配置`InnoDB`

- `innodb_buffer_pool_size`				缓冲池的大小	最大物理内存的80%，推荐75%
- `innodb_flush_log_at_trx_commit`		`innodb`将`log buffer`中的数据写入日志文件并flush磁盘的时间点	取值分别为0、1、2
- `innodb_file_per_table`					控制每个表使用独立的表空间		默认`OFF`，改为`ON`

# 事务

四大特性` ACID`
- 原子性：一个事务内的操作是一个整体，要么全部成功，要么全部回退。
- 一致性：一个事务执行前后，数据库从一个一致性状态到另一个一致性状态。如，账户A少了100，账户B多了100。
- 隔离性：数据库并发时，各事务之间互不影响，独立运行。
- 持久性：事务提交后，对数据的变动是持久的。

事务隔离级别

- `Read_Uncommitted` 读未提交：可以读到其他事务未提交的数据，可能发生脏读、不可重复读、幻读。
- `Read_Committed` 读已提交：可以读到其他事务已提交的数据，可以防止发生脏读，可能发生不可重复读、幻读。（`Oracle`默认级别）
- `Repeatable_Read` 可重复读：对同一记录多次读取结果相同，可以防止脏读、不可重复读，可能发生幻读。（`MySQL` 默认级别）
- `Serializable` 串行化：所有事务依次逐个执行，完全遵循 `ACID` ，可以防止脏读、不可重复读、幻读。

- 脏读：`A`事务对某条数据改动，但未提交；`B`事务查询修改后的该条数据，`A`事务回滚或继续改变，B事务就发生了脏读。
- 不可重复读：`B`事务查询某条数据，`A`事务对该条数据修改并提交，`B`事务再次查询时，发现俩次结果不同，`B`事务发生了不可重复读。
- 幻读：`A`事务查询某几条数据，`B`事务删除了这几条或新增了类似的几条数据并提交，`A`事务再次查询时，结果数量与之前不同，`A`事务发生了幻读。

事务隔离是通过锁机制和并发调度实现的。其中并发调度使用的是 `MCVV` (多版本并发控制)，通过行的创建时间和过期时间来支持并发一致性读和回滚等特性。

# `Mysql` 锁机制

1.按锁粒度
- 表级锁：对当前操作的整张表加锁。
	优：开销少，加锁快，实现简单，不会出现死锁。
	缺：并发低。
	用：事务更新大表的大部分数据时，效率更高；事务比较复杂时，使用表级锁，行级锁容易引起死锁而回滚。
- 行级锁：对当前操作的数据行加锁。
	优：减少数据库操作的冲突，并发高。
	缺：加锁慢，可能产生死锁，开销大。
	`InnoDB` 的行级锁基于索引实现，查询语句未命中索引，`InnoDB` 会使用表级锁；不同行的记录，相同的索引仍然会出现锁冲突。
	`Record Lock`：对索引项加锁，锁定符合条件的行。其他事务不能修改和删除锁定行。
	`Gap Lock`：对索引项之间的"间隙"加锁，锁定第一条记录之前的和最后一条记录之后的间隙加锁，不包含索引项本身。其他事务不能在锁定范围内新增数据，防止出现幻影行。
	`Next-key Lock`：对索引项和"间隙"加锁，`Next-key Lock = Record Lock + Gap Lock`。解决幻读。

`InnoDB` 支持行级锁和表级锁
`MyISAM` 只支持表级锁

2.按操作类型
- 读锁(共享锁)：`Share Lock` ，简称 `S`锁，可以并发读取数据。事务`T1`对数据`A`加上`S`锁，则事务`T1`只能读`A`；事务`T2`只能对数据`A`加`S`锁，不能加`X`锁，直到事务`T1`释放了`A`上的`S`锁。
- 写锁(排它锁)：`Exclusive Lock`，简称 `X`锁，独占数据。事务`T1`对数据`A`加上`X`锁，事务`T1`可以读、写`A`；事务`T2`不能对数据`A`加人和所，直到事务`T1`释放了`A`上的`X`锁。

# 数据库优化
1.限定查询条件的范围，减少查询的数据数量。

2.读/写分离：主库负责写入，从库负责读取。

3.垂直拆分：表宽度拆分，一张表的列，拆到几张表中。如：商品表拆分为商品表、商品详情表。
  优：简化表结构，易于维护；减少`I/O`次数。
  缺：主键冗余，引起 join 操作；事务更复杂。

4.水平拆分：表行数拆分，表结构不变，将数据分片，做集群，分库分表。如：商品表拆分为商品表1、2、3。
  数据库分片方案
  优：减少单库的数据量，提高并发；提高稳定性和负载能力。
  缺：事务一致性，维护量大。

> 客户端代理：分片逻辑在应用端，封装在`jar`包中，配置多数据源，通过修改或者封装`JDBC`层来实现。`Sharding-JDBC`	成本低但扩展性差
> 中间件代理：在应用和数据中间加了一个代理层，分片逻辑统一维护在中间件服务中。`Mycat`扩展强，支持复杂需求，但成本高