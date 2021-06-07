# CopyOnWriteArrayList

> 运用不可变对象模式，使集合遍历操作时，不加锁也可以保证线程安全。

## 等效不可变对象

> 从CopyOnWriteArrayList源码看等效不可变对象：
>
> 1. array只能通过 getArray()/setArray() 访问；
> 2. Iterator()、add()都是通过getArray()获取到对象数组；
> 3. add() 生成新数组`复制并替换`老数组，老数组未变化；也就是array对象被创建后，内容（数组长度和每个元素）不再被改变，在增/删操作时，只是array引用了新数组对象；
> 4. array本质上是一个对象数组，当其他地方改变了数组元素的状态（属性或内存地址），array对象的元素状态也会变化。
>
> 所以array不是一个不可变对象，但一个线程在遍历这个列表时，另外的线程修改列表元素时不会对原列表元素进行改动，避免了多线程下共享数据错乱的问题。

### CopyOnWriteArrayList 部分源码

```java
public class CopyOnWriteArrayList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
    
    final transient ReentrantLock lock = new ReentrantLock();
    private transient volatile Object[] array;
    
    final Object[] getArray() { return array; }
    final void setArray(Object[] a) { array = a; }
    
    public Iterator<E> iterator() {return new COWIterator<E>(getArray(), 0);}
    
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
       } finally {
            ock.unlock();
       }
    }
    
    public E remove(int index) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            E oldValue = get(elements, index);
            int numMoved = len - index - 1;
            if (numMoved == 0)
                setArray(Arrays.copyOf(elements, len - 1));
            else {
                Object[] newElements = new Object[len - 1];
                System.arraycopy(elements, 0, newElements, 0, index);
                System.arraycopy(elements, index + 1, newElements, index,
                                 numMoved);
                setArray(newElements);
            }
            return oldValue;
        } finally {
            lock.unlock();
        }
    }
}
```

### CopyOnWrite（写时复制机制）

> 对集合进行**迭代**的情况，本质上是一个**读操作**；
>
> 而往集合中**新增/删除**一个元素本质上是一个**写操作**。
>
> 写时复制针对在进行写操作时，从原数据复制出新数据，在新数据上进行操作的方式，避免影响读操作。

### 适用场景

> `读多写少`的场景，通过写时复制机制，让大量读请求在无需加锁影响性能的情况下，保证多线程并发读写的线程安全。

<img src="https://i.loli.net/2021/06/07/6PGOe8lLCVjKzU9.png" alt="image-20210607223011594" style="zoom: 150%;" />

1. 线程1先遍历读取数据；
2. 线程2在线程1读取元素2前添加元素3；
3. 线程1无法读取元素3，因为俩个线程操作的数组不是同一个数据，线程2操作的是新数组；这也是 CopyOnWriteArrayList 的一个特点，`弱一致性`。

`弱一致性：线程1看到的是某一时刻的一份“快照数据”，无法保证能读取到最新的数据。`

### 实际场景

> Java 连接数据库一般是通过JDBC实现的，JDBC 连接数据库的第一步操作是加载对应的驱动，

#### Mysql 驱动源码

```java
public class Driver extends NonRegisteringDriver implements java.sql.Driver {
    
    static {
        try {
            java.sql.DriverManager.registerDriver(new Driver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }
    
    public Driver() throws SQLException {
        // Required for Class.forName().newInstance()
    }
}
```

#### 驱动管理器部分源码

```java
public class DriverManager {

    // List of registered JDBC drivers
    private final static CopyOnWriteArrayList<DriverInfo> registeredDrivers = new CopyOnWriteArrayList<>();
    
    public static synchronized void registerDriver(java.sql.Driver driver, DriverAction da) throws SQLException {
        if(driver != null) {
            registeredDrivers.addIfAbsent(new DriverInfo(driver, da));
        } else {
            // This is for compatibility with the original DriverManager
            throw new NullPointerException();
        }
        println("registerDriver: " + driver);
    }
}
```

`registerDriver()方法，实际上是往registerDrivers中添加了一个DriverInfo对象，而registerDrivers就是一个CopyOnWriteArrayList。`

数据库的驱动程序一般都是在程序启动的时候加载的，也就是说registerDriver()方法一般来说都是在程序启动的时候进行调用的，在后续程序运行过程中一般不会再调用这个方法，这种场景完美符合`写少`的定义，基本上在程序运行过程中，不会再进行写操作(也就是add/remove等操作)。

程序中通过JDBC获取数据库连接（getConnection()）的时候，会去遍历所有的driver，然后找到一个driver，然后通过那个特定的driver来获取连接；所以每次和数据库交互前都会遍历一次registerDrivers。

JDBC驱动程序列表这种数据，因为驱动程序变更的情况比较少，但是遍历这个驱动程序列表的情况比较多，所以是符合读多写少的特性，因此这个使用CopyOnWriteArrayList来维护比较合适。