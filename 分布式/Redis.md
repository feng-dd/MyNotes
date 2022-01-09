# Redis

## 一、NoSQL

> NoSQL：Not Only SQL，不仅仅是数据库；

### 1、数据模型

**聚合模型**

1. KV键值对：Key-Value 形式得到数据。
2. Bson：二进制Json数据格式。
3. 列族：数据纵向排列，一个field对应一个值。
4. 图形：类似于人际关系图。

### 2、NoSQL数据库类型

> 数据库类型对应聚合模型

1. KV键值：==Redis==。
2. 文档型数据库(Bson)：MongoDB。
3. 列存储数据库：
4. 图关系数据库：

### 3、数据库事务

#### 传统关系型数据库保证事务的四大特性

1. 原子性(Atomicity)：同一事务的多个操作是原子的，要么全部成功、要么全部失败。
2. 一致性(Consistency)：同一事务操作前后保证数据在一定形态下的一致；如A向B转100元，执行转账的一系列操作后，账户A和B的资产总和保持不变。
3. 隔离性(Isolation)：不同事务之间是相互隔离，互不影响。
4. 持久性(Durability)：事务提交后，对数据的改变是持久的，不会受其他因素的影响。

#### NoSQL通过CAP理论和BASE思想保证分布式事务

##### CAP理论

1. 强一致性(Consistency)
2. 可用性(Availability)
3. 分区容错性(Partition tolerance)

> CAP理论在分布式存储系统中，无法同时遵循这三点，只能从中选择两点。
>
> 由于当下硬件问题，一定会存在网络丢包的情况，所以分布式系统必须实现**分区容错**，因此需要从**一致性**和**可用性**中权衡 。
>
> CA：关系型数据库，如传统得到Oracle数据库。
>
> AP：高并发网站大多数选择，如淘宝、京东、微博等。在高并发下，可以适当的降低一致性，如双11的商品详情页中的浏览数|收藏数，当前系统的稳定性相比这些数据的准确性来讲，无疑更为重要。
>
> CP：Redis、MongoDB。Redis等缓存的引入一开始是为了减轻高并发下MySQL这种关系型数据库的压力，必须保证数据的一致性。

![image-20210425223023007](https://i.loli.net/2021/04/26/y1INlVb8QkojHsC.png)

##### BASE

> BASE方案：为了解决关系数据库保证强一致性而导致可用性降低的问题。
>
> 通过让系统放松对某一时刻数据的一致性，换取系统整体的伸缩性和性能。

1. 基本可用(Basically Availability)
2. 软状态(Soft state)
3. 最终一致(Eventually Consistency)

### 分布式和集群的异同

> 分布式：多台服务器上部署不同的服务模块，纵向拆分。
>
> 集群：多台服务器上部署相同的服务模块，横向扩展。

## 二、Redis特性

### 1、单线程

> Redis是单线程模型，一个线程处理所有请求，其他模块使用多线程。
>
> 其执行效率极高，原因是：
>
> 1. Redis是基于内存操作的；数据在内存中，运算在内存中。
> 2. Redis的数据结构简单，查找和操作的时间复杂度是O(1)。
> 3. Redis内部基于epoll实现的多路复用(select/poll)；Redis使用I/O多路复用功能来监听多个socket连接客户端，可以使用一个线程来处理多个请求，避免了线程切换时带来的开销，且不会产生死锁。
> 4. 非阻塞I/O，避免了I/O阻塞操作。

### 2、I/O多路复用

套接字的读写方法默认是阻塞的，例如当调用读取操作read方法时，缓冲区没有任何数据，那么这个线程会卡在这里，直到缓冲区中有数据或者连接被关闭时，read方法才会返回，该线程才能继续处理其它业务。

但这样显然就降低了程序的整体执行效率，而Redis使用的是非阻塞的I/O，这就意味着I/O的读写流程不再是阻塞的，读写方法都是`瞬间完成并且返回`的，也就是它会采用能读多少就读多少、能写多少就写多少的策略来执行I/O操作，这显然更符合我们对性能的追求。

但是这种非阻塞的I/O也面临一个问题，那就是当我们执行读取操作时，有可能只读取了一部分数据；同理写数据也是这种情况，当缓冲区满了，而我们的数据还没有写完，那么剩下的数据何时可写就成了一个问题。

而I/O的多路复用就是解决上面的这个问题的，使用I/O多路复用的方式（1、使用select函数；2、使用epoll函数），监控多个文件描述符的可读和可写情况的，当监控到相应的事件之后就会通知线程处理相应的业务了，这样就保证了Redis读写功能的正常执行。

## 三、Redis的数据结构

### 1、字符串

> redis的字符串为动态字符串，初始化分配内存时，会冗余空间用于减少内存频繁重分配，即 实际分配空间 > 字符串长度。
>
> 字符串由多个字节组成，每个字节为8bit组成，即一个字符串由多个bit组成（`bitmap` *位图*）。

#### 命令

```bash
# 添加key
set key value
#获取key的value
get key
# 添加带有过期时间(second)的key，当已设置过期时间的key，value被修改，改key过期时间会消失
set test_ex_key "10s后消失" ex 10
setex test_ex_key "10s后消失"  10
# 获取key的剩余有效时间(second)，> 0 是剩余时间，-1 无过期时间， -2 已过期
ttl test_ex_key
# 添加带有过期时间(ms)的key
set test_px_key "10s后消失" px 10000
# 获取key的剩余有效时间(ms)，> 0 是剩余时间， -2 已过期
pttl test_px_key
# 删除key
del key
# 当key不存在时，添加成功
set test:nx:key:1 "value1" nx
# 当key存在时，设置成功
set test:xx:key:1 "value1" xx

#  获取key对应的value长度，不存在返回0
strlen key

# 将str追加到key的value后面，value为空的=set key str
append key str

# 获取key的某一部分
getrange key start end
set key "hellow world!"
# 所有
getrange key 0,-1  
> "hellow world!"
# 正向[0,1]
getrange key 0,1
> "he"
# 负向[-3,-1]
getrange key -3,-1 
> "rld"

# 递增1，原子操作，范围是Long.min~Long.max(2^64 - 1 = 9223372036854775807)，值超过会报overflow
set key 1
incr key
get key 
>"2"
# 递增指定量
incrby key 10
get key
> "12"
# 递增浮点数
incrbyfloat key 1.2
# 递减1，原子
decr key value
# 递减指定量
decrby key 10

# 批量添加，原子操作
mset key1 value1 key2 value2
# 批量获取
mget key1 key2

# set nx 批量操作
msetnx rmdbs "MySQL" nosql "MongoDB" key-value-store "redis"
# key存在，操作失败
msetnx rmdbs "Sqlite" language "python" 
> (integer) 0
# key不存在，操作成功
msetnx rmdbs:new "Sqlite" language1 "python"
> (integer) 1
```

### 2、哈希表

#### 数据结构

> 基本与Java中的HashMap结构是一致的，`数组+链表`，Reids的Hash结构中的值只能存字符串。
>
> 区别在于`扩容方式`：
>
> - Java：当HashMap中的元素特别大时，会一次性rehash，非常耗时。
>
> - Redis：为保证服务性能，使用渐进式rehash；同时存在 new、old 两个hash结构，后续定时任务&hash操作指令中，逐步迁移到new结构中，直到old全部迁移到new中，new完成替代，在此期间会同时查询new、old中的数据。

`当hash中最后一个元素移除时，改数据结构会自动被删除，内存被回收。`

#### 命令

```bash
# 创建hash表及字段
hset hashtable field "value" 
# 获取hash表的字段
hget hashtable field
# 当hash表不存在时，创建hash表及字段，1-成功、0-失败
hsetnx hashtable field "value2"
> (integer) 0
# hash表的字段是否存在，1-成功、0-失败
hexists hashtable field1
>  (integer) 0

hset hashtale field1 "value1"
# 返回hash表的所有字段及对应的值
hgetall hashtable 
>  (1)"field" 
    (2)"value" 
    (3)"field1" 
    (4)"value1" 

# 删除hash表的字段
hdel hashtable field1 [field2...] 
> 删除的field个数

# 获取hash表的file个数
hlen hashtable

redis> HMSET myhash f1 "HelloWorld" f2 "99" f3 "-256"
OK
# 获取哈希表中field对应的value长度，注意:redis版本>=3.2.0
redis> HSTRLEN myhash f1
(integer) 10
redis> HSTRLEN myhash f3
(integer) 4

# 批量获取哈希表指定field对应的value
> HMGET myhash f1 f2
1) "HelloWorld"
2) "99" 
# 获取哈希表所有的 field
> HKEYS myhash 
1) "f1"
2) "f2" 
3) "f3"  
> HKEYS myhash2
(empty list or set) 
# 获取哈希表所有的 value
> HVALS myhash 
1) "HelloWorld"
2) "99" 
3) "-256"

# value增量
hincrby hashtable field4 100 
>"100"
hincrby hashtable field4 -10
> "90"
hincrbyfloat hashtable field4 0.5 
> "90.5"
hincrbyfloat hashtable field4 -0.5 
> "90.0"
```

### 3、列表

![image-20210425235912111](https://i.loli.net/2021/04/25/iO8S4q3EfgkQcwo.png)

#### 数据结构

> 类似于Java中的LinkedList，便于插入和删除，时间复杂度`O(1)`，而索引定位很慢，为`O(N)`。
>
> **ziplist**：当列表元素较少时，元素会连续的存放在一块连续的内存中。
>
> **quicklist**：当列表元素增多后，会存在多个ziplist，通过双向链表的形式组合；在减少内存碎片的情况下，也能保证一定的插入或删除的性能。

#### 场景

> 队列：右进左出。RPUSH、LPOP。
>
> 栈：右进右出。RPUSH、RPOP。

`当列表中最后一个元素弹出时，改数据结构会自动被删除，内存被回收。`

#### 命令

```bash
> lpush key value value
(integer) 2

> lrange key 0 -1
1) "value"
2) "value" 
 
> lpush newkey value1 value2
(integer) 2 

> lrange newkey 0 -1
1) "value2"
2) "value1" 

> rpush key value value
(integer) 2

> lrange key 0 -1
1) "value"
2) "value" 
 
> rpush newkey a b c
(integer) 3 

> lrange newkey 0 -1
1) "a"
2) "b"  
2) "c"

# 对空列表执行 LPUSHX
redis> LLEN greet                  # greet 是一个空列表
(integer) 0

redis> LPUSHX greet "hello"        # 尝试 LPUSHX，失败，因为列表为空
(integer) 0

# 对非空列表执行 LPUSHX/RPUSH
redis> LPUSH greet "hello"        # 先用 LPUSH 创建一个有一个元素的列表
(integer) 1

redis> LPUSHX greet "good morning"   # 这次 LPUSHX 执行成功
(integer) 2

redis> LRANGE greet 0 -1
1) "good morning"
2) "hello" 

> rpush list a b c
(integer) 3

> lrange list 0 -1
1) "a"
2) "b"
3) "c" 

> lpop list
"a" 

> lrange list1 0 -1
1) "c"
2) "b"
3) "a"
> lrange list2 0 -1
1) "f"
2) "e"
3) "d"
> rpoplpush list1 list2
"a" 

> lrange list1 0 -1
1) "c"
2) "b" 
> lrange list2 0 -1
1) "a"
2) "f"
3) "e"
4) "d"

# 列表尾移动到列表头
> rpoplpush list1 list1
"b"
> lrange list1 0 -1
1) "b"
2) "c"

> LRANGE key 0 -1
1) "morning"
2) "hello"
3) "morning"
4) "hello"
5) "morning"

# 移除列表中最后一个moning
> LREM key -1 moning
# 移除列表中前两个moning
> LREM key 2 moning
# 移除列表中所有hello
> LREM key 0 hello

# 获取列表的元素个数，空key=0，key不是列表报错
> LLEN key

# 获取列表下标位置的元素
> lrange greet 0 -1
1) "hello"
2) "morning"
3) "every"
> lindex greet -1
"every"
> lindex greet 0
"hello"
> lindex greet 1
"morning"

# 截取列表
# start = 1, stop = -1, 删除index=1之前的元素
> ltrim greet 1 -1 
> ok
> lrange greet 0 -1
1) "morning"
2) "every" 

# start = 1, stop = 10086, stop > 列表的长度，等价于-1
> ltrim greet 1 10086 

# start = 10010 , stop = 10086, start & stop > 列表的长度，清空列表
> ltrim greet 10010 10086 

# 将一个或多个值 value 插入到列表 key 的表头
LPUSH key1 value [value …]

# 阻塞式等待，将列表 key1 中的最后一个元素 (尾元素) 弹出，并返回给客户端。
# 将 key1 弹出的元素插入到列表 key2 ，作为 key2 列表的的头元素。
# 超时参数 timeout 接受一个以秒为单位的数字作为值。
# 超时参数设为 0 表示阻塞时间可以无限期延长 (block indefinitely) 。
BRPOPLPUSH key1 key2 timeout

# 根据参数 count 的值，移除列表中与参数 value 相等的元素。
LREM key2 count value
```

> 基于 Redis 的 list 实现队列：
>
> 第一种（不推荐）：即使用`LPUSH`生产消息，然后 while(true) 中通过`RPOP`消费消息，这种方式的确可以实现，但是不断代码不断的轮询，势必会消耗一些系统的资源。
>
> 第二种（不推荐）：也是通过 `LPUSH`生产消息，然后通过 `BRPOP` 进行**阻塞地**等待并消费消息，这种方式较第一种方式减少了无用的轮询，降低系统资源的消耗，但是可能会存在队列消息丢失的情况，如果取出了消息然后处理失败，这个被取出的消息就将丢失。
>
> 第三种（推荐）：首先也是通过 `LPUSH` 生产消息，然后通过 `BRPOPLPUSH`**阻塞**地等待 list 新消息到来，有了新消息才开始消费，同时将消息备份到另外一个 list 当中，这种方式具备了第二种方式的优点，即减少了无用的轮询，同时也对消息进行了备份不会丢失数据，如果处理成功，可以通过 `LREM` 对备份的 list 中当前的这条消息进行删除处理。这种方式实现方式可以参考 [模式： 安全的队列](http://redisdoc.com/list/rpoplpush.html#id3)

### 4、集合

#### 数据结构

> 类似于Java中的HashSet，元素无序且唯一。
>
> 内部实现：特殊的Hash，所有的值都为Null。

#### 场景

> 过滤已参加过活动的用户

`当集合中最后一个元素移除之后，数据结构自动删除，内存被回收。`

#### 命令

```bash
# 添加集合元素，重复项只添加一次
SADD key value
redis> SADD bbs "discuz.net" "groups.google.com"
(integer) 2
> SADD bbs "discuz.net"
> (integer) 0 
# 获取集合元素
> smembers bbs
1) "groups.google.com"
2) "discuz.net" 
# 判断集合存在某元素
> sismember bbs groups.google.com
(integer) 1 
# 随机移除一个元素返回
> spop bbs
> "groups.google.com"
# 随机返回一个元素
> srandmember bbs
"discuz.net"
> smembers bbs
1) "groups.google.com"
2) "discuz.net" 
# 随机返回N个元素
> srandmember bbs 2
1) "groups.google.com"
2) "discuz.net"
# 随机返回3个元素，如果没有三个，就返回所有，不可重复
> srandmember bbs 3
1) "groups.google.com"
2) "discuz.net"
# 随机返回3个元素，可以重复
> srandmember bbs -3
1) "discuz.net"
2) "discuz.net"
3) "groups.google.com"
# 移除指定的集合元素
> srem bbs discuz.net
1
# 指定集合中的某个元素移动到新集合中
> smove bbs newbbs groups.google.com
(integer) 1
> smembers bbs
(empty list or set)
> smembers newbbs
1) "groups.google.com"
# 扫描集合内的元素个数
> SCARD newbbs
1
> smembers group1
1) "b"
2) "c"
3) "a"
> smembers group2
1) "d"
2) "c" 
# 获取交集元素
> sinter group1 group2
1) "c" 
# 交集元素放入新的集合
> sinterstore group group1 group2
1
> smembers group
1) "c" 
# 返回并集元素
> sunion group1 group2
1) "d"
2) "b"
3) "a"
4) "c" 
# 并集元素放入新的集合
> sunionstore newgroup group1 group2
4
> smembers newgroup
1) "d"
2) "b"
3) "a"
4) "c" 
# 差集：返回group2中没有的元素
> sdiff group1 group2
1) "b"
2) "a" 
# 差集元素放入新的集合
> sdiffstore diffgroup group1 group2
2
> smembers diffgroup
1) "b"
2) "a" 
```

### 5、有序集合

#### 数据结构

> 类似于 Java 的 SortedSet 和 HashMap 的结合体:
>
> - 一方面它是一个 set，保证了内部 value 的唯一性
> - 另一方面它可以给每个 value 赋予一个 score，代表这个 value 的排序权重。
>
> **「跳跃列表」**：内部实现。

#### 场景

> - 存粉丝列表，value 值是粉丝的用户 ID，score 是关注时间。我们可以对粉丝列表按关注时间进行排序。
> - 存储学生的成绩，value 值是学生的 ID，score 是他的考试成绩。我们可以对成绩按分数进行排序就可以得到他的名次。

`zset 中最后一个 value 被移除后，数据结构自动删除，内存被回收。`

#### 命令

```Bash
# 有序集合rank 中新增元素和对应的分数
> zadd rank 1 uzi 2 deft
(integer) 2
# 获取有序集合的元素，按分数升序排序
> zrange rank 0 -1
1) "uzi"
2) "deft"
# 获取有序集合的元素，包括分数,按分数升序排序
> zrange rank 0 -1 withscores
1) "uzi"
2) 1.0
3) "deft"
4) 2.0 
# 获取集合元素的评分
> zscore rank uzi
1.0
> zscore rank imp
(nil) 
# 对集合元素的分数做增量
> zincrby rank 10 uzi
11.0 
# 返回有序集合的元素个数
> zcard rank
2 
> zrange rank 0 -1 withscores
1) "deft"
2) 2.0
3) "uzi"
4) 11.0
# 获取分数在此区间的元素个数
> zcount rank 0 20
2
> zcount rank 10 11
1 
# 获取有序集合的元素，按分数降序排序
> zrevrange rank 0 -1 withscores
1) "uzi"
2) "deft"
# 获取有序集合的元素，包括分数,按分数降序排序
> zrevrange rank 0 -1 withscores
1) "uzi"
2) 11.0
3) "deft"
4) 2.0 
# 返回分数范围内的元素,升序
> zrangbyscore rank -inf +inf 
1) "deft"
2) "uzi"
> zrangbyscore rank -inf 2 
1) "deft"
> zrangbyscore rank -inf (2 
(empty list or set)
> zrangbyscore rank 2 (11 withscores
1) "deft"
2) 2.0 
> zrangbyscore rank (2 (11 withscores
(empty list or set)
# 返回分数范围内的元素,降序
> zrevrangbyscore rank +inf -inf 
1) "uzi"
2) "deft"
> zrevrangbyscore rank 11 2 
1) "uzi"
2) "deft"
# uzi排名第二(升序)
> zrank rank uzi
1
# uzi排名第一(降序)
> zrevrank rank uzi
0
# 移除集合指定的一个或多个元素
> zrem rank deft
1 
# 移除下标[0,1]范围内的元素
> zremrangebyrank rank 0 1
1  
# 移除分数[1,10]范围内的元素
> zremrangebyscore rank 1 10
1   
```

### 容器型数据结构共享两条通用规则

list/set/hash/zset 是容器型数据结构

1. create if not exists

   如果容器不存在，那就创建一个，再进行操作。比如 rpush 操作刚开始是没有列表的，Redis 就会自动创建一个，然后再 rpush 进去新元素。

2. drop if no elements

   如果容器里元素没有了，那么立即删除元素，释放内存。这意味着 lpop 操作到最后一个元素，列表就消失了。

### BitMap（位图）

#### 使用场景

可以用于存储用户的签到记录

> - u:sign:1000:201902表示ID=1000的用户在2019年2月的签到记录。 
> - 用户2月17号签到 `SETBIT u:sign:1000:201902 16 1` # 偏移量是从0开始，所以要把17减1 
> - 检查2月17号是否签到 `GETBIT u:sign:1000:201902 16` # 偏移量是从0开始，所以要把17减1 
> - 统计2月份的签到次数 `BITCOUNT u:sign:1000:201902` 
> - 获取2月份前28天的签到数据 `BITFIELD u:sign:1000:201902 get u28 0`
> - 获取2月份首次签到的日期 `BITPOS u:sign:1000:201902 0` # 返回的首次签到的偏移量，加上1即为当月的某一天

### 布隆过滤器

> 布隆过滤器的使用目的不是为了存储对象，而是标记对象，以在后续能判断此对象是否被标记过。如果判断为此对象未被标记，则此对象肯定未被标记；若判断为此对象已经标记，则大概率上此对象已经被标记。
>
> 
>
> 每个布隆过滤器对应到 Redis 的数据结构里面就是一个大型的位数组和几个不一样的无偏 hash 函数。所谓无偏就是能够把元素的 hash 值算得比较均匀。
>
> 向布隆过滤器中添加 key 时，会使用多个 hash 函数对 key 进行 hash 算得一个整数索引值然后对位数组长度进行取模运算得到一个位置，每个 hash 函数都会算得一个不同的位置。再把位数组的这几个位置都置为 1 就完成了 add 操作。
>
> 向布隆过滤器询问 key 是否存在时，跟 add 一样，也会把 hash 的几个位置都算出来，看看位数组中这几个位置是否都为 1，只要有一个位为 0，那么说明布隆过滤器中这个 key 不存在。如果都是 1，这并不能说明这个 key 就一定存在，只是极有可能存在，因为这些位被置为 1 可能是因为其它的 key 存在所致。如果这个位数组比较稀疏，判断正确的概率就会很大，如果这个位数组比较拥挤，判断正确的概率就会降低。

#### 使用场景

> 邮箱系统的垃圾邮件过滤功能也普遍用到了布隆过滤器，因为用了这个过滤器，所以平时也会遇到某些正常的邮件被放进了垃圾邮件目录中，这个就是误判所致，概率很低。

#### 命令

```
127.0.0.1:6379> bf.add codehole user1
(integer) 1
127.0.0.1:6379> bf.add codehole user2
(integer) 1

127.0.0.1:6379> bf.exists codehole user1
(integer) 1
127.0.0.1:6379> bf.exists codehole user4
(integer) 0

127.0.0.1:6379> bf.madd codehole user4 user5 user6
1) (integer) 1
2) (integer) 1
3) (integer) 1
S
127.0.0.1:6379> bf.mexists codehole user4 user5 user6 user7
1) (integer) 1
2) (integer) 1
3) (integer) 1
4) (integer) 0
```

