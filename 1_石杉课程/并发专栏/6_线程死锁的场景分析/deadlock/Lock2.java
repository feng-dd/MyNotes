package deadlock;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/9 16:32
 * @Desc
 * @Since 1.0
 */
public class Lock2 implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("deadlock.Lock2 running");
            while (true) {
                synchronized (DeadLock.obj2) {
                    System.out.println("deadlock.Lock2 lock obj2");
                    Thread.sleep(3000);
                    synchronized (DeadLock.obj1) {
                        System.out.println("deadlock.Lock2 lock obj1");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
