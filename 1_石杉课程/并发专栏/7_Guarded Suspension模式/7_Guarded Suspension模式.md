# 高并发场景下，优化轮询获取锁的方式

> 在多线程开发中，为了提高应用程序的并发性，会将一个任务分解为多个子任务交给多个线程并行执行，而多个线程之间相互协作时，仍然会存在一个线程需要等待另外的线程完成后继续下一步操作。
>
> 工作中使用`轮询while(true)`的方式来等待某个状态，在高并发的场景中，获取锁冲突的频率大大增加，可以用`等待-通知机制(Guarded Suspension模式)`来优化。

## Guarded Suspension 模式

> 保护性暂挂模式。当线程无法申请到所有的锁，线程自己`挂起等待且会释放锁`，当线程满足所有的要求后，`通知提醒`挂起等待的线程重新执行。
>
> Guarded：保护；
>
> Suspension：暂时挂起。

### 类比

![image-20210610152436803](D:\develop_data\feng\MyNotes\1_石杉课程\并发专栏\7_Guarded Suspension模式\Guarded Suspension模式.assets\image-20210610152436803.png)

> 1. 客户排队点单；**线程等待获取锁**
> 2. 点单；**线程申请到锁**
> 3. 收款的店员通知客户扫二维码付款，就去服务下一个客户了；**线程需要的资源不全，线程挂起释放锁**
> 4. 客户付款结束，等待取餐；**线程需要准备好了资源**
> 5. 备餐的店员通知客户取餐；**其他线程通知该线程可以执行了**
> 6. 客户取餐；**该线程执行**

### 代码

```java
// 通知机制实现类
public class GuardedQueue {

    private final Queue<Object> sourceList;

    public GuardedQueue() {
        this.sourceList = new LinkedBlockingQueue<>();
    }

    public synchronized Object get() {

        while (sourceList.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sourceList.peek();
    }

    public synchronized void put(Object obj) {
        sourceList.add(obj);
        notifyAll();
    }
}

// 测试类
public class App {
    public static void main(String[] args) {
        GuardedQueue guardedQueue = new GuardedQueue();

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        try {
            executorService.execute(() -> {
                guardedQueue.get();
            });
            Thread.sleep(2000);
            executorService.execute(() -> {
                guardedQueue.put(new Object());
            });
            // 暂停接受任务
            executorService.shutdown();
            // 终止线程池，30s任务完成后终止 或 超时终止
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

#### 为什么不用notify() 来实现通知机制呢？

> notify() & notifyAll() 的区别：
>
> notify()：会`随机通知`等待队列中的`任意一个`线程；
>
> notifyAll()：会``通知``等待队列中的`所有`线程。

觉得 notify() 可能认为即便通知所有线程，也只有一个线程能够进入临界区。

但是实际上使用 notify() 也很有风险，因为随机通知等待的线程，`可能会导致某些线程永远不会被通知到`。