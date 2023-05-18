import org.junit.Test;

import java.util.Arrays;

public class testAlgorithm {
    @Test
    public void testArray(){
        ListNode head = new ListNode(0);
        ListNode head2 = new ListNode(1);
        ListNode head3 = new ListNode(2);
        ListNode head4 = new ListNode(3);
        head.next = head2;
        head2.next = head3;
        head3.next = head4;

        ListNode begin = reverse(head,1,4);
        do {
            System.out.println(begin);
            begin = begin.next;
        }while(begin != null);

        System.out.println(head);
    }

    private ListNode reverse(ListNode head, int left, int right) {
        ListNode dummyNode= new ListNode(-1);
        dummyNode.next = head;
        ListNode pre = dummyNode;
        for (int i = 1; i < left; i++) {
            pre = pre.next;
        }
        ListNode cur = pre.next;
        ListNode next;
        for (int i = 0; i < right - left; i++) {
            next = cur.next;
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
//        return head; 注意这个不对劲
        return dummyNode.next;
    }

    @Test
    public void testCode() {
        int[] nums = new int []{-3,4,3,8};
        int target = 0;
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }
    public int[] twoSum(int[] nums, int target) {
        for(int i = 0;i< nums.length; i++){
            for(int j = i+1; j<nums.length;j++ ){
                if(nums[i]+nums[j]==target){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }
}
