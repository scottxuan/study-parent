package scottxuan.lock;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : pc
 * @date : 2020/7/20
 */
@Slf4j
public class ReentrantLockExample4 {
    private final static List<Integer> list = Lists.newArrayList();
    private final static int threadCount = 5000;
    private final static AtomicInteger atInt = new AtomicInteger(0);
    private final static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch downLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int num = i;
            service.execute(()->{
                try {
                    add(num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                downLatch.countDown();
            });
        }
        downLatch.await();
        //输出获取到锁的线程数
        log.info("list size {}", list.size());
        //输出未获取到锁的线程数
        log.info("AtomicInteger num {}", atInt.get());
        log.info("all thread {}", atInt.get() + list.size());
        service.shutdown();
    }

    public static void add(int num) throws InterruptedException {
        //尝试获取锁并设置等待时间,如果在等待时间内该锁未被其他线程持有,则获取到,如果超过等待时间为获取到锁,直接返回false,不再阻塞
        if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
            try {
                Thread.sleep(2000);
                list.add(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }else{
            //对未获取到锁的线程进行计数
            atInt.addAndGet(1);
        }
    }
}

//输出结果 获取锁的线程数+未获取到锁的线程数 = 总线程数
//12:28:28.343 [main] INFO scottxuan.lock.ReentrantLockExample3 - list size 3554
//12:28:28.348 [main] INFO scottxuan.lock.ReentrantLockExample3 - AtomicInteger num 1446
//12:28:28.348 [main] INFO scottxuan.lock.ReentrantLockExample3 - all thread 5000


