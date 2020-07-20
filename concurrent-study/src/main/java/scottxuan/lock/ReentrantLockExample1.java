package scottxuan.lock;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : pc
 * @date : 2020/7/20
 */
@Slf4j
public class ReentrantLockExample1 {
    private final static List<Integer> list = Lists.newArrayList();
    private final static int threadCount = 5000;
    /**
     * 非公平锁(线程随机获得锁)
     */
    private final static ReentrantLock lock = new ReentrantLock();

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
        log.info("list size {}",list.size());
        service.shutdown();
    }

    public static void add(int num){
        lock.lock();
        try {
            log.info("get lock num {}",num);
            //获取锁
            list.add(num);
        } finally {
            //finally中释放锁
            lock.unlock();
        }
    }
}


