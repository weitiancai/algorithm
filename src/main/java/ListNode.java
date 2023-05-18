import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ListNode {
    public ListNode next;
    int val;
    public ListNode(int val) {
        this.val =val;
    }
    @Override
    public String toString() {
        return "listNode:" + val;
    }
}
