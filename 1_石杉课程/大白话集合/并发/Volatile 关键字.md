# Volatile 关键字

## 语义

> 用于修饰变量，可以保证变量的内存可见性，防止指令重排序。

## 工作原理

![image-20210630215506995](https://i.loli.net/2021/06/30/SlJeRThOar7D4Gz.png)![image-20210630215542293](https://i.loli.net/2021/06/30/JLgcVKA23RsdbkG.png)

### 可见性

> 1. 当线程修改了被 volatile 修饰的变量时，立刻强制刷新到主存；
> 2. 若其他线程的工作内存中有包含变量的副本时，强制过期失效变量副本，不允许读和使用；
> 3. 其他线程在运行过程中需要读取变量时，需要从主存中重新读取。

`JMM 和 happens-before 原则保证可见性`

#### happens before 八大原则`Java编译器层面禁止指令重排序`

1. 程序顺序规则：一个线程内，按代码书写顺序执行。
2. 监视器锁规则：一个锁的 unLock 先行于后续对同一个锁的 Lock；
3. volatile 规则：对 Volatile 变量的写先行于后面对这个变量的读；
4. 传递规则：操作A 先于 操作B，操作B 先于 操作C，那么 操作A 先于 操作C；
5. 线程启动规则：线程的 start() 先于该线程的任意操作；
6. 线程中断规则：线程的 interrput() 先于 被中断线程被检测到中断；
7. 线程终结规则：线程的终结检测 先于 线程的其他任意操作；
8. 对象终结规则：一个对象的初始化完成 先于 该对象的 finalize()。

### 有序性

> 代码被编译时，在保证单线程语义的情况下，编译器可能会对指令进行重排序；
>
> 但在多线程执行的情况下可能会造成线程安全的问题。

`Volatile 通过内存屏障和 happens-before 原则防止指令重拍`

#### 内存屏障（内存栅栏）`CPU层面禁止指令重排序`

> 一组处理器指令，禁止屏障前后的指令顺序互换。

###### 四类内存屏障

1. **LoadLoad**：Load1 | Barrier | Load2
2. **StoreStore**：Store1 | Barrier | Store2
3. **LoadStore**：Load1 | Barrier | Store2
4. **StoreLoad**：Store1 | Barrier | Load2

###### 内存屏障指令插入规则

| 第一次操作（行）\ 第二次操作（列） |     普通读/写      | volatile 读 |     volatile 写      |
| :--------------------------------: | :----------------: | :---------: | :------------------: |
|             普通读/写              |                    |             | LoadSotre/StoreStore |
|            volatile 读             | LoadLoad/LoadStore |  LoadLoad   |      LoadStore       |
|            volatile 写             |                    |  StoreLoad  |      StoreStore      |

- 第一个 Volatile 读操作不允许放在第二操作之后
- 第二个 Volatile 写操作不允许放在第一操作之前
- 两个Volatile 读写操作不允许重排序
