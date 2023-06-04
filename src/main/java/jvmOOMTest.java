import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class jvmOOMTest {
    public static void main(String[] args) {
        List<ListNode> listNodes = new ArrayList();
        while (true) {
            listNodes.add(new ListNode(1));
        }
    }
}
