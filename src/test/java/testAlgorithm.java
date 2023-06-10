import org.junit.Test;

import java.util.*;

public class testAlgorithm {
    @Test
    public void testArray() {
        ListNode head = new ListNode(1);
        ListNode head2 = new ListNode(1);
        ListNode head3 = new ListNode(1);
        ListNode head4 = new ListNode(1);
        head.next = head2;
        head2.next = head3;
        head3.next = head4;

//        ListNode begin = reverse(head, 1, 4);
        deleteDuplicates(head);
        showListNode(head);
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode dommyNode;
        dommyNode = pre;
        ListNode tmp;
        while (head != null) {
            tmp = pre;
            pre = head;
            head = head.next;
            if (head != null && pre.val == head.val) {
                pre.next = head.next;
            }
            if (head == null) {
                if (tmp.val == pre.val) {
                    tmp.next = null;
                } else {
                    pre.next = null;
                }
            }
        }
        return dommyNode.next;
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
        Map<Character, Integer> map = new HashMap();
        int maxlen = 0;
        int left = 0;
        int len = s.length();

        for (int i = 0; i < len; i++) {
            Character c = s.charAt(i);

            if (map.containsKey(c)) {
                left = Math.max(left, map.get(c) + 1);
            }

            map.put(c, i);
            maxlen = Math.max(maxlen, i - left + 1);
        }
        return maxlen;
    }

    // 最长回文子串 dp 动态规划
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        int strLen = s.length();
        int maxStart = 0; //最长回文串的起点
        int maxEnd = 0; // 最长回文串的终点
        int maxLen = 1; //最长回文串的长度

        boolean[][] dp = new boolean[strLen][strLen];
        for (int r = 1; r < strLen; r++) {
            for (int l = 0; l < r; l++) {
                if (s.charAt(l) == s.charAt(r) && (r - l <= 2 || dp[l + 1][r - 1])) {
                    dp[l][r] = true;
                    if (r - l + 1 > maxLen) {
                        maxLen = r - l + 1;
                        maxStart = l;
                        maxEnd = r;
                    }
                }
            }
        }

        return s.substring(maxStart, maxEnd + 1);
    }

    @Test
    public void testDouble() {
//        Set<String> st = new HashSet<String>(){{add("string;");}};
//        List<String> lt = new ArrayList<String>(){{add("string");}};
//        HashMap<String,String> hashMap = new HashMap<String,String>(){{put("str","str");put("xxx","xxx");}};
//        Map<Character, Character> map = new HashMap<Character,Character>(){
//            {put('{','}');put('[',']');put('(',')');put('?','?');}
//        };
        int[] nums = new int[]{3};
        int val = 3;
        System.out.println("长度" + removeElement(nums, val));
        for (int num : nums) {
            System.out.println(num);
        }
    }

//    public int removeElement(int[] nums, int val) {
//        int length = nums.length;
//        int left = 0;
//        for (int right = 0; right < length; right++) {
//            if (nums[right] != val) {
//                nums[left++] = val;
//            }
//        }
//        return length;
//    }

    public int removeElement(int[] nums, int val) {
        int length = nums.length;
        int right = length - 1;
        int left = 0;
        while (left < right) {
            if (nums[left] == val) {
                nums[left] = nums[right--];
            } else {
                left++;
            }
        }
        return left;
    }

    //    public int removeElement(int[] nums, int val) {
//        int right = nums.length - 1;
//        int left = 0;
//        int tmp = 0;
//        int cnt = 0;
//        while(left <= right){
//            while(nums[left] != val){cnt++; if (left < nums.length-1) left++; else break;}
//            while(nums[right] == val){
//                if(right > 0)
//                    right--;
//                else break;
//            }
//            if (left >= right) {
//                break;
//            }
//            tmp = nums[left];
//            nums[left] = nums[right];
//            nums[right] = tmp;
//        }
//        return cnt;
//    }
    @Test
    public void testPos() {
//        Set<String> st = new HashSet<String>(){{add("string;");}};
//        List<String> lt = new ArrayList<String>(){{add("string");}};
//        HashMap<String,String> hashMap = new HashMap<String,String>(){{put("str","str");put("xxx","xxx");}};
//        Map<Character, Character> map = new HashMap<Character,Character>(){
//            {put('{','}');put('[',']');put('(',')');put('?','?');}
//        };
        int[] nums = new int[]{1, 3};
        int val = 2;
        System.out.println(getPosition(nums, 0, nums.length - 1, val, true));
//        System.out.println(searchInsert(nums, val));

    }

    private int getPosition(int[] nums, int left, int right, int target, boolean flag) {
        if (left > right) {
//            int pos = flag ? right : left;
//            return pos < 0 ? 0 : pos;
            // mid往右那就是left 往左的时候本来是right-1 但是这个数字 会占用一格所以是left
            return left;
        }
        int midPos = left + (right - left) / 2;
        int mid = nums[midPos];
        if (target < mid) {
            return getPosition(nums, left, midPos - 1, target, true);
        } else if (target > mid) {
            return getPosition(nums, midPos + 1, right, target, false);
        } else {
            return midPos;
        }
    }


    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }

    @Test
    public void testAddBinary() {
        System.out.println(addBinary("1010", "1011"));
    }

    public String addBinary(String a, String b) {
        int ra = a.length() - 1;
        int rb = b.length() - 1;
        int tmp = 0;
        StringBuilder sb = new StringBuilder();
        for (; ra >= 0 || rb >= 0; ra--, rb--) {
            int sum = tmp;
            sum += ra >= 0 ? a.charAt(ra) - '0' : 0;
            sum += rb >= 0 ? b.charAt(rb) - '0' : 0;
            sb.append(sum & 1);
            tmp = sum >> 1;
        }
        sb.append(tmp == 1 ? "1" : "");
        return sb.reverse().toString();
    }

    @Test
    public void testMySqrt() {
        System.out.println(mySqrt3(2147483647));
    }

    public int mySqrt(int x) {
        long b = x;
        long left = x;
        while (b >= 1) {
            if (multiplication(b, x)) {
                b >>= 1;
            } else {
                left = b;
                break;
            }
        }
        long right = left * 2;

        while (right - left > 1) {
            long mid = (left + right) / 2;
            if (multiplication(mid, x)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return (int) (right * right <= x ? right : left);
    }

    private boolean multiplication(long v, long x) {
        return v * v > x;
    }


    public int mySqrt2(int x) {
        int l = 0, r = x, ans = -1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if ((long) mid * mid > x) {
                r = mid - 1;
            } else {
                ans = l;
                l = mid + 1;
            }
        }
        return ans;
    }

    public int mySqrt3(int x) {
        long left = x;
        while (left >= 1) {
            if (left * left > x) {
                left >>= 1;
            } else {
                break;
            }
        }
        long right = x;
        int ans = -1;
        while (left <= right) {
            long mid = (long) (left + right) / 2;
            if (mid * mid > x) {
                right = mid - 1;
            } else {
                ans = (int) mid;
                left = mid + 1;
            }
        }
        return ans;
    }

    @Test
    public void mergeArrays() {
        int[] a = new int[]{0};
        merge(a, 0, new int[]{1}, 1);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index = m + n - 1;
        m--;
        n--;
        if (m < 0) {
            for (int i = 0; i < n+1; i++) {
                nums1[i] = nums2[i];
            }
        }
        while (m >= 0 && n >= 0) {
            nums1[index--] = (m >= 0 && nums1[m] > nums2[n]) ? nums1[m--] : nums2[n--];
        }
    }
}