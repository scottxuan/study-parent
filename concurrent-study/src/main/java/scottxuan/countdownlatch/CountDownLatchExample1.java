package scottxuan.countdownlatch;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author scottxuan
 */
@Slf4j
public class CountDownLatchExample1 {

    private final static int threadCount = 10;

    public static void main(String[] args) throws InterruptedException {
        //初始化计数器,一般和线程数目相同
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int num = i;
            executor.execute(()->{
                count(num);
                //多线程每执行一次,计数器减1
                countDownLatch.countDown();
            });
        }
        //主线程阻塞,等待所有线程执行完毕,才会执行后续的代码
        countDownLatch.await();
        log.info("all thread finish");
        executor.shutdown();
    }

    private static void count(int num){
        log.info("current num {}",num);
    }
}
