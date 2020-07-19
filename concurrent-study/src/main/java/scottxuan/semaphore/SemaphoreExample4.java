package scottxuan.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author scottxuan
 */
@Slf4j
public class SemaphoreExample4 {
    private final static int threadCount = 12;
    public static void main(String[] args) {
        //控制并发量为3(许可总数)
        Semaphore semaphore = new Semaphore(3);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int num = i;
            executor.execute(()->{
                try {
                    //尝试获取3s内的许可,获取不到则返回false
                    if (semaphore.tryAcquire(3, TimeUnit.SECONDS)) {
                        update(num);
                        //释放一个许可
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }

    private static void update(int num) throws InterruptedException {
        log.info("current num {}",num);
        Thread.sleep(1000);
    }
}
