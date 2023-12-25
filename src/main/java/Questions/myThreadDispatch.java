package Questions;
//多线程到达一个位置后再继续执行，不用java并发包怎么实现？
public class myThreadDispatch implements Runnable{
    private volatile boolean arrive = false;

    private void doSthifArrive(){
        synchronized (this) {
            while(!arrive){
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void arriveHere(){
        synchronized (this) {
            arrive = true;
            notify();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Runnable myThreadDispatch = new myThreadDispatch();
            Thread y = new Thread(myThreadDispatch);
            y.start();
        }
        Thread.sleep(10000);
    }

    @Override
    public void run() {
        arriveHere();
        doSthifArrive();
        String name = Thread.currentThread().getName();
        System.out.println(name + "is executing");
    }
}
