package MySngleTon;

public class MySingleTon {
        private static MySingleTon instance;

        public MySingleTon() {
            // 私有的构造函数，防止外部直接实例化
        }
        public static MySingleTon getInstance() {
            //双重判定 null 避免线程多线程竞争
            if(instance == null)
                synchronized (instance) {
                    if (instance == null) {
                        instance = new MySingleTon();
                    }
                }
            return instance;
        }

//        懒汉模式就是static 块中先创建一个，
//    饿汉模式就是 要的时候再给
    public static void main(String[] args) {
        MySingleTon a = new MySingleTon();
        MySingleTon b = new MySingleTon();

    }
}
