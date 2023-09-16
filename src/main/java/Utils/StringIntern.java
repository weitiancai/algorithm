package Utils;

import java.util.concurrent.*;

public class StringIntern {

    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(6, 6, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());


    public static void main(String[] args) {
//        String str1 = "Runoob";
//        String str2 = new String("Runoob");
//        String str3 = str2.intern();
//
//        System.out.println(str1 == str2);  // false
//        System.out.println(str1 == str3);  // true

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleWithFixedDelay(StringIntern::doSthSubmitToThreadPoolExecutor, //一个方法就是个Runnable
                2,
                1,
                TimeUnit.SECONDS);

    }

    private static void doSthSubmitToThreadPoolExecutor() {
        while (threadPoolExecutor.getActiveCount() < 6) {
            threadPoolExecutor.submit(() -> System.out.println("do sth with 6 threads"));
        }
    }
}
