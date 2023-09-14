package ProductConsumer;

import lombok.SneakyThrows;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class runPC {
    private static Boolean run = true;//控制是否生产和消费
    private static final Integer MAX_CAPACITY = 5;//缓冲区最大数量
    private static final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();


    public static class Producer extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            while(run){
                synchronized (queue){
                    while (queue.size() > MAX_CAPACITY) {
                        System.out.println("blocking队列满了");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    String uuid = UUID.randomUUID().toString();
                    queue.add(uuid);
                    System.out.println("produce : "+ uuid);
                    Thread.sleep(100);
                    queue.notifyAll();
                }
            }
        }
    }
    public static class Consumer extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            while(run) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        System.out.println("queue is empty(), stop consumer");
                        queue.wait();
                    }

                    String take = queue.take();
                    System.out.println("consumer : " + take);
                    Thread.sleep(100);
                    queue.notifyAll();
                }
            }
        }

    }

    public static void main(String[] args) {
        new Producer().start();
//        new Producer().run(); // 这种是方法调用，不会开启一个新线程
        new Consumer().start();
//        new Consumer().run();
    }
}
