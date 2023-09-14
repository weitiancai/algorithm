package Utils;

public class MultiThreadSingleton {

//    饿汉式单例模式 :  在类加载时就创建了实例，因此不存在线程安全问题。但是它的缺点是无论是否使用该实例，都会提前进行创建，可能会造成资源浪费。
    private static MultiThreadSingleton instance = new MultiThreadSingleton();
    // 私有化构造器
    private MultiThreadSingleton(){};

    public static MultiThreadSingleton getInstance() {
        return instance;
    }

//    懒汉式单例模式（双重检查锁）：  懒汉式单例模式在首次调用 `getInstance()` 方法时才创建实例，避免了资源的浪费。使用双重检查锁机制和 volatile 关键字可以保证线程安全。
    public static class Singleton {
        private volatile static Singleton instance;

        private Singleton() {
            // 私有构造函数
        }

//        public static Singleton getInstance() {
//            if (instance == null) {
//                synchronized (instance) {
//                    instance =  new Singleton();
//                }
//            }
//            return instance;
//        }
        public static Singleton getInstance() {
            if (instance == null) {
                synchronized (Singleton.class){
                    if (instance == null) {
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }

    }






}
