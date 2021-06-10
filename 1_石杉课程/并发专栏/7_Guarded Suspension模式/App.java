import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author Murphy
 * @Version 1.0
 * @Date 2021/6/10 11:52
 * @Desc
 * @Since 1.0
 */
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
