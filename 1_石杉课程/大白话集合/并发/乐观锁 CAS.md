## 乐观锁

> 乐观的认为并发竞争不会发生。

### 实现 -> `CAS机制`

> Compare And Set，先比较再设置。
>
> 预期值A，内存值V，修改值B，当A和B相等时，认为没有线程修改过该值，将V替换为B。

### 应用

Atomic 原子类。

###### AtomicInteger 部分源码

```java
// 原子加1
public final int getAndIncrement() {
    return unsafe.getAndAddInt(this, valueOffset, 1);
}
```

###### Unsafe 部分源码

```java
public final int getAndAddInt(Object var1, long var2, int var4) {
    int var5;
    do {
        // 获取内存预期值 V
        var5 = this.getIntVolatile(var1, var2);
    } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4)); // CAS乐观锁机制
    return var5;
}
```

### `ABA问题`

> 变量值初始为A，
>
> t1线程读取变量为A，
>
> 后t2线程将变量修改为B，t3线程又将变量修改为A，
>
> t1线程修改变量为C，成功，但是实际逻辑可能出现了问题。

##### 解决

> AtomicStampReference，通过添加版本号控制。

###### 示例

```java
// 19是变量的数值，0是stamp
static AtomicStampedReference<Integer>  money =new AtomicStampedReference<Integer>(19,0);
Integer value = money.getReference()；// 19
int stamp = money.getStamp()；// 0
money.compareAndSet(value, value+2, stamp, stamp+1); //value+2，CAS操作，成功返回 true，stamp+1
```

### 缺点

不适合并发竞争高的场景，会造成大量线程空自旋，消耗系统资源，性能和效率都不高。

### Java8优化

分段 CAS 机制，LongAdder 底层实现：Cell 数组，线程对某个 Cell 元素的 Value 进行 CAS 累加操作，CAS 失败，自动找到下个 Cell 元素进行累加，避免空自旋出现无限循环的情况，分段也分摊了线程对数据累加的压力。

当要更新的cells数组元素为；CAS 操作失败的情况时，cells数组大小没有超过核数，会扩容。取值时会将Base和cell数组的所有元素进行求和返回。



