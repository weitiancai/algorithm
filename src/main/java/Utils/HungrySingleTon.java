package Utils;

public class HungrySingleTon {
    private static final HungrySingleTon hungrySingleTon = new HungrySingleTon();

    private HungrySingleTon(){

    }

    public static HungrySingleTon getHungrySingleTon(){
        return getHungrySingleTon();
    }

}
