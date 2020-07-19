package scottxuan.cyclicbarrier;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author scottxuan
 */
@Slf4j
public class CyclicBarrierExample2 {
    //线程数
    private final static int threadNum = 2;

    //初始化线程同步数量
    final static CyclicBarrier barrier = new CyclicBarrier(1);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNum; i++) {
            final int num = i;
            service.execute(()->{
                update(num);
            });
            Thread.sleep(2000);
        }
        service.shutdown();
    }

    public static void update(int num){
        try {
            log.info("thread ready {}",num);
            //线程阻塞
            barrier.await(1000, TimeUnit.MILLISECONDS);
            log.info("thread continue {}",num);
        } catch (Exception e) {
            log.error("barrier await error");
        }
    }
}
