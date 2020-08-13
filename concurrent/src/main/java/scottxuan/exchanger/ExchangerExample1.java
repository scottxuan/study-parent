package scottxuan.exchanger;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @date : 2020/7/20
 * Exchanger作用 ：用于两个线程之间交换信息
 * 当一个线程到达 exchange 调用点时，如果其他线程此前已经调用了此方法，则其他线程会被调度唤醒并与之进行对象交换，然后各自返回；
 * 如果其他线程还没到达交换点，则当前线程会被挂起，直至其他线程到达才会完成交换并正常返回，或者当前线程被中断或超时返回。
 */
@Slf4j
public class ExchangerExample1 {
    //初始化交换机
    public final static Exchanger<Integer> exchanger = new Exchanger<>();
    //定义线程数
    private final static int threadCount = 2;

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int num = i;
            final String threadName = "Thread" + num;
            service.execute(() -> {
                change(num,threadName);
            });
        }
        service.shutdown();
    }

    public static void change(int num,String threadName){
        try {
            //交换前各自信息输出
            log.info("交换前 {} : {}",threadName,num);
            //交换后信息输出
            log.warn("交换后 {} : {}",threadName,exchanger.exchange(num));
        } catch (InterruptedException e) {
            log.error("exception",e);
        }
    }
}
