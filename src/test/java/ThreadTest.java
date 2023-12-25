import org.junit.Assert;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import javax.validation.constraints.AssertTrue;
import java.sql.SQLOutput;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
    @Test
    public void testNcpu() throws InterruptedException {
        Thread thread = new Thread(()->{
            while(!Thread.currentThread().isInterrupted()){
                System.out.println("running");
            }
            System.out.println("线程被终止");
        });
        thread.start();

        Thread.sleep(1000L);
        thread.interrupt();

    }


}
