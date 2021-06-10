import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/10 11:44
 * @Desc
 * @Since 1.0
 */
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
