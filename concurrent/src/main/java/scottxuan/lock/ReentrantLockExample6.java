package scottxuan.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : pc
 * @date : 2020/7/20
 */
@Slf4j
public class ReentrantLockExample6 {
    private final static int threadCount = 4;

    private final static ReentrantLock lock = new ReentrantLock();
    private final static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch downLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int num = i;
            service.execute(()->{
                add(num);
                downLatch.countDown();
            });
        }
        downLatch.await();
        service.shutdown();
    }

    public static void add(int num){
        lock.lock();
        try {
            if (num!=3){
                log.info("await num {}",num);
                condition.await();
            }else{
//                condition.signal();
                condition.signalAll();
            }
            log.info("num continue {}",num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}


