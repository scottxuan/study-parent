package scottxuan.exchanger;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @date : 2020/7/20
 * Exchanger作用 ：用于两个线程之间交换信息
 * 当一个线程到达 exchange 调用点时，如果其他线程此前已经调用了此方法，则其他线程会被调度唤醒并与之进行对象交换，然后各自返回；
 * 如果其他线程还没到达交换点，则当前线程会被挂起，直至其他线程到达才会完成交换并正常返回，或者当前线程被中断或超时返回。
 */
@Slf4j
public class ExchangerExample2 {
    //初始化交换机
    public final static Exchanger<Integer> exchanger = new Exchanger<>();
    //定义线程数
    private final static int threadCount = 2;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int num = i;
            final String threadName = "Thread" + num;
            service.execute(() -> {
                change(num,threadName);
            });
            Thread.sleep(1000);
        }
        service.shutdown();
    }

    public static void change(int num,String threadName){
        try {
            //交换前各自信息输出
            log.info("交换前 {} : {}",threadName,num);
            //交换后信息输出
            //exchange(V x, long timeout, TimeUnit unit)方法后在指定的时间内没有其他线程获取数据，则出现超时异常。
            Integer exchange = exchanger.exchange(num,500, TimeUnit.MILLISECONDS);
            log.warn("交换后 {} : {}",threadName,exchange);
        } catch (Exception e) {
            log.error("exception",e);
        }
    }
}
//输出结果
//10:10:37.973 [pool-1-thread-1] INFO scottxuan.exchange.ExchangeExample2 - 交换前 Thread0 : 0
//10:10:38.881 [pool-1-thread-1] ERROR scottxuan.exchange.ExchangeExample2 - exception
//    java.util.concurrent.TimeoutException: null
//    at java.util.concurrent.Exchanger.exchange(Exchanger.java:626)
//    at scottxuan.exchange.ExchangeExample2.change(ExchangeExample2.java:42)
//    at scottxuan.exchange.ExchangeExample2.lambda$main$0(ExchangeExample2.java:29)
//    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
//    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
//    at java.lang.Thread.run(Thread.java:748)
//10:10:38.972 [pool-1-thread-1] INFO scottxuan.exchange.ExchangeExample2 - 交换前 Thread1 : 1
//10:10:39.874 [pool-1-thread-1] ERROR scottxuan.exchange.ExchangeExample2 - exception
//    java.util.concurrent.TimeoutException: null
//    at java.util.concurrent.Exchanger.exchange(Exchanger.java:626)
//    at scottxuan.exchange.ExchangeExample2.change(ExchangeExample2.java:42)
//    at scottxuan.exchange.ExchangeExample2.lambda$main$0(ExchangeExample2.java:29)
//    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
//    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
//    at java.lang.Thread.run(Thread.java:748)


