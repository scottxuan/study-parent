package scottxuan.countdownlatch;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author scottxuan
 */
@Slf4j
public class CountDownLatchExample2 {

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
        //主线程阻塞指定的时间,改时间超过后继续执行后续的代码,无论所有线程是否执行完毕
        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        log.info("await time out");
        executor.shutdown();
    }

    private static void count(int num){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("current num {}",num);
    }
}
