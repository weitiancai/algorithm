package Utils;

//    静态内部类单例模式利用了类加载的特性和静态内部类的延迟加载机制，实现了懒加载和线程安全。
public class SingletonStatic {
    private SingletonStatic() {
        // 私有构造函数
    }

    //    static{
    //
    //    }
    private static class SingletonHolder {
        private static final SingletonStatic instance = new SingletonStatic();
    }

    public static SingletonStatic getInstance() {
        return SingletonHolder.instance;
    }
}