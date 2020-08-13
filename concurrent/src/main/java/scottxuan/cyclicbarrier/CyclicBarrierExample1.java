package scottxuan.cyclicbarrier;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author scottxuan
 */
@Slf4j
public class CyclicBarrierExample1 {
    //线程数
    private final static int threadNum = 4;

    //初始化线程同步数量
    final static CyclicBarrier barrier = new CyclicBarrier(2);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNum; i++) {
            final int num = i;
            service.execute(()->{
                update(num);
            });
            Thread.sleep(500);
        }
        service.shutdown();
    }

    public static void update(int num){
        try {
            log.info("thread ready {}",num);
            //线程阻塞
            barrier.await();
            log.info("thread continue {}",num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
