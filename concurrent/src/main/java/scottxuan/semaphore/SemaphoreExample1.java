package scottxuan.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author scottxuan
 */
@Slf4j
public class SemaphoreExample1 {
    private final static int threadCount = 12;
    public static void main(String[] args) {
        //控制并发量为3
        Semaphore semaphore = new Semaphore(3);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int num = i;
            executor.execute(()->{
                try {
                    //获取1个许可
                    semaphore.acquire();
                    update(num);
                    //释放一个许可
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }

    private static void update(int num) throws InterruptedException {
        log.info("current num {}",num);
        Thread.sleep(3000);
    }
}
