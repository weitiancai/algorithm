import Utils.MyStringUtils;
import org.junit.Test;

public class EverythingTest {
    @Test
    public void exceptionTest(){
        try {
            try {
                int i = 1 / 0;
            } catch (Throwable e) {
                e.printStackTrace();
                System.out.println("Exception throw");
            }
        }catch (Exception e){  //父类异常会被子类异常拦截，反之不会
            e.printStackTrace();
            System.out.println("throwable throw");
        }finally {
            // finally 也可以抛出异常
            try {
                int i = 1 / 0;
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception throw again");
            }
        }
    }

    @Test
    public void testNcpu(){
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void StringEqualsTest(){
        boolean equals = MyStringUtils.equals(null, null);
        boolean equals2 = MyStringUtils.equals("", null);
        boolean equals3 = MyStringUtils.equals("", "");
        System.out.println(equals + " "  + equals2 + " " + equals3);
    }
}
