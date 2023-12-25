package Utils;

public class LazySingleTon {
    private static LazySingleTon instance = null;

    private LazySingleTon(){

    }

    public static LazySingleTon getLazySingleTon(){
        if(instance == null){
            synchronized (LazySingleTon.class){
                if (instance == null) {
                    instance = new LazySingleTon();
                }
            }
        }
        return instance;
    }
}
