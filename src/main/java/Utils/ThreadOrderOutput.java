package Utils;

import lombok.SneakyThrows;

public class ThreadOrderOutput {


    public static class Printer implements Runnable {
        private static final Object lock = new Object();
        private static int currentNumber = 1;
        public int threadNumber;
        public int totalThreads;
        public int maxNumber;

        public Printer(int threadNumber, int totalThreads, int maxNumber) {
            this.threadNumber = threadNumber;
            this.totalThreads = totalThreads;
            this.maxNumber = maxNumber;
        }

        @SneakyThrows
        @Override
        public void run() {
            while (currentNumber <= maxNumber) {
                synchronized (lock) {
                    while (currentNumber % totalThreads != threadNumber) { //随着 currentNumber增加，这个不同线程按顺序满足
                        lock.wait();
                    }
                    System.out.println("Thread " + threadNumber + ": " + currentNumber);
                    currentNumber++;
                    lock.notifyAll();
                }
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
//        notifyOrderPrint();
        int totalThreads = 10;
        for (int i = 0; i < totalThreads; i++) {
            Thread thread = new Thread(new PrinterJoin(i));
            thread.start();
            thread.join();
        }
    }

    private static void notifyOrderPrint() {
        int totalThreads = 3;
        for (int i = 0; i < totalThreads; i++) {
            new Thread(new Printer(i, 3, 10)).start();
        }
    }


    public static class PrinterJoin implements Runnable {
        private int number;

        public PrinterJoin(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            System.out.println("Thread " + number + ":" + number);
        }


    }
}
