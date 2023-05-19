import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class testAlgorithm {
    @Test
    public void testArray() {
        ListNode head = new ListNode(0);
        ListNode head2 = new ListNode(1);
        ListNode head3 = new ListNode(2);
        ListNode head4 = new ListNode(3);
        head.next = head2;
        head2.next = head3;
        head3.next = head4;

        ListNode begin = reverse(head, 1, 4);
        showListNode(begin);
    }

    private void showListNode(ListNode begin) {
        do {
            System.out.println(begin);
            begin = begin.next;
        } while (begin != null);
    }

    private ListNode reverse(ListNode head, int left, int right) {
        ListNode dummyNode = new ListNode(-1);
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
        int[] nums = new int[]{-3, 4, 3, 8};
        int target = 0;
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }

    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public ListNode genListNode(int[] arr) {
        ListNode begin = new ListNode(arr[0]);
        ListNode dummyNode = begin;
        if (arr.length > 1) {
            for (int i = 1; i < arr.length; i++) {
                begin.next = new ListNode(arr[i]);
                begin = begin.next;
            }
        }
        return dummyNode;
    }


    @Test
    public void testAddValue() {
        ListNode l1 = new ListNode(2);
        ListNode l2 = genListNode(new int[]{5, 6, 4, 5, 5, 5, 5, 5, 5, 5, 5});
        showListNode(addTwoNumbers(l1, l2));
    }


//    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//        int left =0, right = 0, times = 1;
//        while(l1 != null){
//            left += l1.val * times;
//            l1 = l1.next;
//            times *= 10;
//        }
//        times = 1;
//        while(l2 != null){
//            right += l2.val * times;
//            l2 = l2.next;
//            times *= 10;
//        }
//        int sum = left + right;
//        int num;
//        ListNode res = new ListNode(1);
//        ListNode head = res;
//        do {
//            num = sum % 10;
//            ListNode next = new ListNode(num);
//            res.next = next;
//            res = next;
//            sum/=10;
//        }while (sum > 0);
//        return head.next;
//    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int left = 0, right = 0;
        ListNode dummyNode = new ListNode(0);
        ListNode head = dummyNode;
        int redundance = 0;
        while (l1 != null || l2 != null || redundance > 0) {
            left = l1 != null ? l1.val : 0;
            right = l2 != null ? l2.val : 0;
            ListNode sumNode = new ListNode(((left + right) % 10 + redundance) % 10);
            redundance = (left + right + redundance) / 10;
            head.next = sumNode;
            head = sumNode;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        return dummyNode.next;
    }

    //3. 无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        int i = 0;
        int flag = 0;
        int length = 0;
        int result = 0;
        while (i < s.length()) {
            int pos = s.indexOf(s.charAt(i), flag);
            if (pos < i) {
                if (length > result) {
                    result = length;
                }
                if (result >= s.length() - pos - 1) {
                    return result;
                }
                length = i - pos - 1;
                flag = pos + 1;
            }
            length++;
            i++;
        }
        return length;
    }

    @Test
    public void testLongestSubstr() {
        System.out.println(lengthOfLongestSubstring05("acbba"));
    }

    public int lengthOfLongestSubstring04(String s) {
        // 单个字符作为key，字符在字符串中的下标作为value
        Map<Character, Integer> map = new HashMap<>();
        // 最大值
        int maxLen = 0;
        // 左边界
        int left = 0;
        // 这个循环的一些想法，之前老喜欢把字符串转字符数组，然后用数组去操作，其实直接遍历索引也是可以的
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            /**
             * 要理解下面的代码首先要想明白获得最长字串的逻辑
             * 这里一共存在两个变量：
             *  - left：表示字串最左边字符的索引位置
             *  - i：遍历的每个字符索引位置，我们全靠这个字符来完成这道题，i这个变量存在三种情况需要考虑
             *      1） s.charAt(i)在[left, i)之间不存在重复字符
             *          - 就把s.charAt(i)放到map中，最长长度同时也加一
             *      2） s.charAt(i)在[left, i)之间存在重复字符
             *          - 获得[left, i)范围内重复字符的下标h，让left = h + 1，此时新的字串开始匹配，新的长度变成了i - left + 1 = 1
             *          - 更新map中重复字符的索引位置为i
             *      3） s.charAt(i)在[0, left)之间存在重复字符
             *          - 我们在发现重复字符后都要更新该字符在map中的索引位置，就是为了避免之前的重复元素对当前判断造成影响
             *          - 但是譬如acbba这样一个字符：当遍历到第二个b时，可知h = map.get('b')，所以h=2，那么newLeft=h+1=3;
             *              之后遍历到字符a，h = map.get('a')，发现此时h=0，newLeft=h+1=1；这种情况明显不合理，所以要求left=Math.max(left, newLeft)
             *          - 更新map中重复字符的索引位置为i，最长长度同时也加一
             *
             */
            if (map.containsKey(c)) {
                left = Math.max(left, map.get(c) + 1);
            }
            map.put(c, i);
            maxLen = Math.max(maxLen, i - left + 1);
        }
        return maxLen;
    }


    public int lengthOfLongestSubstring05(String s) {
        Map<Character,Integer> map = new HashMap();
        int maxlen = 0;
        int left =0;
        int len = s.length();

        for(int i = 0 ;i< len ;i++){
            Character c = s.charAt(i);

            if(map.containsKey(c)){
                left = Math.max(left,map.get(c)+1);
            }

            map.put(c,i);
            maxlen = Math.max(maxlen, i-left+1);
        }
        return maxlen;
    }
}