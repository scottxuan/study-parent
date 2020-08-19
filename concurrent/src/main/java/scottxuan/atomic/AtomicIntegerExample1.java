package scottxuan.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author scottxuan
 */
@Slf4j
public class AtomicIntegerExample1 {

    private final static int threadCount = 5000;
    private  static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        //初始化计数器,一般和线程数目相同
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int num = i;
            executor.execute(()->{
                add();
                //多线程每执行一次,计数器减1
                countDownLatch.countDown();
            });
        }
        //主线程阻塞,等待所有线程执行完毕,才会执行后续的代码
        countDownLatch.await();
        log.info("all thread finish,count:"+count.get());
        executor.shutdown();
    }

    private static void add(){
        count.getAndIncrement();
    }
}
