package scottxuan.lock;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : scottxuan
 * lock和lockInterruptibly的区别
 * lock:不考虑中断,除非获得锁否则将一直等待下去，也就是说这种无限等待获取锁的行为无法被中断
 * lockInterruptibly:先考虑中断,如果线程中断,不再去等待获取锁
 */
@Slf4j
public class ReentrantLockExample5 {
    private final static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            try {
                add(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                add(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        thread2.interrupt();
    }

    public static void add(int num) throws InterruptedException {
//        lock.lock();
        lock.lockInterruptibly();
        try {
            log.info("get lock {}",num);
            Thread.sleep(1000);
        } catch (Exception e) {
            log.error("{} exception",num);
        } finally {
            //finally中释放锁
            lock.unlock();
            log.info("lock {} over",num);
        }
    }
}


