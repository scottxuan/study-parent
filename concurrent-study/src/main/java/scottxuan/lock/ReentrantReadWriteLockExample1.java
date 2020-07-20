package scottxuan.lock;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author : pc
 * @date : 2020/7/20
 */
@Slf4j
public class ReentrantReadWriteLockExample1 {
    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final static Lock readLock = lock.readLock();
    private final static Lock writeLock = lock.writeLock();
    private final static Map<Integer, String> map = Maps.newHashMap();
    private final static int threadCount = 10;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int num = i;
            executor.execute(() -> {
                if (num!=4) {
                    read(num);
                }else{
                    write(num);
                }
            });
        }
        executor.shutdown();
    }

    public static void write(int num) {
        writeLock.lock();
        try {
            String name = "thread " + num;
            log.info("write lock num {} value {}", num,name);
            Thread.sleep(2000);
            map.put(num, name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("write unlock num {}", num);
            writeLock.unlock();
        }
    }

    public static void read(int num) {
        readLock.lock();
        try {
            log.info("read get lock num {} value {}", num, map.get(num));
        } finally {
            readLock.unlock();
        }
    }
}

