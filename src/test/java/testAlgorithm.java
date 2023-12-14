import Utils.TreeUtils;
import leetCode.NTreeNode;
import leetCode.TreeNode;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

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

    @Test
    public void testRoman(){
        System.out.println(romanToInt("MCMXCIV"));
    }

    // 我做的，罗马数字转整数 ，其实就是 加加减减
    public int romanToInt(String s) {
        Map<Character, Integer> orderMap = new HashMap<Character, Integer>(){{
                put('I',1);
        put('V',2);
        put('X',3);
        put('L',4);
        put('C',5);
        put('D',6);
        put('M',7);
        }};
        Map<Character, Integer> valueMap = new HashMap<Character, Integer>(){{
            put('I',1);
            put('V',5);
            put('X',10);
            put('L',50);
            put('C',100);
            put('D',500);
            put('M',1000);
        }};
        char[] arr = s.toCharArray();
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            // 一判断 后面两位是否是 大于的数  是的话，就相减这个数就好了
            if(i+1 < arr.length && orderMap.get(arr[i]) < orderMap.get(arr[i+1])){
                ans -= valueMap.get(arr[i]);
            }
            if(i+2 < arr.length && orderMap.get(arr[i]) < orderMap.get(arr[i+2])){
                ans -= valueMap.get(arr[i]);
            }else{
                ans += valueMap.get(arr[i]);
            }
        }
        return ans;
    }
    //83
    public ListNode deleteDuplicatesFail(ListNode head) {
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

    public ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;
        while (cur != null && cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
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
    public int lengthOfLongestSubstringFail(String s) {
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

    public String longestPalindromeTrue(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        int strlength = s.length();
        int maxleft = 0;
        int maxright = 0;
        int maxlen = 0;
        boolean dp[][] = new boolean[strlength][strlength];
        char[] charArray = s.toCharArray();
        for (int r = 1; r < charArray.length; r++) {
            for (int l = 0; l < r; l++) {
                if (charArray[l] == charArray[r] && (dp[l + 1][r - 1] || r - l <= 2)) {
                    dp[l][r] = true;
                    if (maxlen < r - l + 1) {
                        maxlen = r - l + 1;
                        maxleft = l;
                        maxright = r;
                    }
                }
            }
        }
        return s.substring(maxleft, maxright + 1);
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
            for (int i = 0; i < n + 1; i++) {
                nums1[i] = nums2[i];
            }
        }
        while (m >= 0 && n >= 0) {
            nums1[index--] = (m >= 0 && nums1[m] > nums2[n]) ? nums1[m--] : nums2[n--];
        }
    }

    public int[] twoSumFail(int[] nums, int target) {
        if (nums.length == 2) {
            return new int[]{0, 1};
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; i <= nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{999, 999};
    }

    public int[] twoSum3(int[] nums, int target) {
        Map<Integer, Integer> hashTable = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashTable.containsKey(target - nums[i])) {
                return new int[]{i, hashTable.get(target - nums[i])};
            }
            hashTable.put(nums[i], i);
        }
        return new int[0];
    }
    public int[] twoSum7(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i =0;i<nums.length; i++){
            if(map.containsKey(target-nums[i])){
                return new int[]{nums[i],map.get(target-nums[i])};
            }
            map.put(nums[i],i);
        }
        return new int[0];
    }

    public int[] twoSum4(int[] nums, int target) {
        int left =0, right =0;
        Map<Integer,Integer> map = new HashMap<>();
        for(int i =0;i<nums.length; i++){
            if(!map.containsKey(target-nums[i])){
                map.put(nums[i],i);
            }else{
                right=i;
                left = map.get(target-nums[i]);
            }
        }
        return new int[]{nums[left],nums[right]};
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            List<String> valueList = map.getOrDefault(key, new ArrayList<String>());
            valueList.add(key);
        }
        Collection<List<String>> values = map.values();
        return new ArrayList<>(map.values());
    }

    public int longestConsecutiveFail(int[] nums) {
        boolean[] rec = new boolean[2000000000];
        for (int i = 0; i < nums.length; i++) {
            rec[nums[i] + 1000000000] = true;
        }
        int begin = 0;
        int right = 0;
        boolean cont = false;
        int max = 1;
        for (int i = 0; i < rec.length; i++) {
            if (rec[i]) {
                if (!cont) {
                    begin = i;
                }
                cont = true;
            }
            if (!rec[i] && cont) {
                right = i;
                max = Math.max(max, right - begin);
                cont = false;
            }
        }
        return max;
    }

    public int longestConsecutive(int[] nums) {
        Set<Integer> num_set = new HashSet<Integer>();

        for (int num : nums) {
            num_set.add(num);
        }

        int longestStreak = 0;

        for (int num : num_set) {
            if (!num_set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                while (num_set.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        return longestStreak;
    }

    @Test
    public void twoSum2() {
//        int[] a = new int[]{2,7,11,15};
//        int[] ints = twoSum2(a, 9);
//        for (int i = 0; i < ints.length; i++) {
//            System.out.println(ints[i]);
//        }
        int[] a = new int[]{100, 4, 200, 1, 3, 2};

        System.out.println(longestConsecutive(a));

    }


    public int PrimeCount(int n) {
        int count = 0;
        for (int i = 2; i <= n; i++) {
            count += isPrime(i) ? 1 : 0;
        }
        return count;
    }

    private boolean isPrime(int num) {
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testPrimeFunc() {
        // 普通素数算法
//        System.out.println(PrimeCount(100));
        // 埃氏筛法
        System.out.println(eratosThenes(100));

    }

    private int eratosThenes(int n) {
        // false 代表素数 初始化
        boolean[] primeArr = new boolean[n + 1];
        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (!primeArr[i]) {
//                for (int j = 2 * i; j <= n; j += i) {
                for (int j = i * i; j <= n; j += i) {
                    primeArr[j] = true;
                }
                count++;
            }
        }
        return count;
    }


    public int[] distinctDifferenceArray(int[] nums) {
        Map<Integer, Set<Integer>> beforemap = new HashMap<>();
        Map<Integer, Set<Integer>> aftermap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            for (int k = i; k >= 0; k--) {
                Set<Integer> set = beforemap.getOrDefault(i, new HashSet<>());
                set.add(nums[k]);
                beforemap.put(i, set);
            }
            for (int j = i + 1; j < nums.length; j++) {
                Set<Integer> set2 = aftermap.getOrDefault(i, new HashSet<>());
                set2.add(nums[j]);
                aftermap.put(i, set2);
            }
        }
        aftermap.put(nums.length - 1, new HashSet<>());
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            result[i] = beforemap.get(i).size() - aftermap.get(i).size();
        }
        return result;
    }

    public int[] distinctDifferenceArrayTrue(int[] nums) {
        int n = nums.length;
        int[] suf = new int[n + 1];
        Set<Integer> s = new HashSet<Integer>();
        for (int i = n - 1; i > 0; i--) {
            s.add(nums[i]);
            suf[i] = s.size();
        }

        s.clear();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            s.add(nums[i]);
            ans[i] = s.size() - suf[i + 1];
        }
        return ans;
    }

    @Test
    public void testDistinctDifferenceArray() {
        int[] ints = distinctDifferenceArray(new int[]{1, 2, 3, 4, 5});
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    //    暴力解法
    public int lengthOfLongestSubstringBaoli(String s) {
        int maxlen = 0;
        char[] chars = s.toCharArray();
        Set<Character> charSet = new HashSet<>();
        for (int i = 0; i < chars.length; i++) {
            for (int j = i; j < chars.length + 1; j++) {
                if (j < chars.length) {
                    if (!charSet.contains(chars[j])) {
                        charSet.add(chars[j]);
                    } else {
                        maxlen = Math.max(j - i, maxlen);
                        charSet.clear();
                        break;
                    }
                } else if (j == chars.length) {
                    maxlen = Math.max(j - i, maxlen);
                    charSet.clear();
                    break;
                }
            }
        }
        return maxlen;
    }

    // o(n)解法
    public int lengthOfLongestSubstring(String s) {
        Set<Character> occ = new HashSet<>();
        int n = s.length();
        int rk = -1, ans = 0;
        for (int i = 0; i < n; i++) {
            if (i != 0) {
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断向右移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

    int lengthOfLongestSubstring2(String s) {
        int ans = 0;
        int rk = -1;
        Set<Character> cset = new HashSet<>();
        char[] chars = s.toCharArray();
        int n = chars.length;
        for (int i = 0; i < n; i++) {
            if (i != 0) {
                cset.remove(chars[i - 1]);
            }
            //右指针是可以一次性找到最右边的
            while (rk + 1 < n && !cset.contains(chars[rk + 1])) {
                cset.add(chars[rk + 1]);
                rk++;
            }
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }


    public int lengthOfLongestSubstringWrong(String s) {
        int maxlen = 0;
        int len = s.length();
        int right = 0;
        Set<Character> charSet = new HashSet<>();
        for (int i = 0; i < len; i++) {
            if (i != 0) {
                charSet.remove(s.charAt(i));
//                charSet.remove(s.charAt(i-1));  这个才对
            }
//            while(right < len && !charSet.contains(s.charAt(right))){ 这个才对
            while (right < len && !charSet.contains(right)) {

                charSet.add(s.charAt(right));
                right++;
            }

            maxlen = Math.max(maxlen, right - i);
        }

        return maxlen;
    }

    // 状态转义方程
//    dp[l][r] = dp[l+1][r-1] == true && char[l]=char[r]
//    初始条件 r-l <=2 时两个元素 或者三个元素 char[l]=char[r]
    public String longestPalindromeTrue2(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        int maxlen = 0;
        int maxLeft = 0;
        int maxRight = 0;
        char[] charArray = s.toCharArray();
        int n = charArray.length;
        boolean dp[][] = new boolean[n][n];
        for (int r = 1; r < n; r++) {
            for (int l = 0; l < r; l++) {
                if (charArray[l] == charArray[r] && (r - l <= 2 || dp[l + 1][r - 1])) {  // 不是<=1
                    dp[l][r] = true; //千万别忘记
                    if (r - l + 1 > maxlen) {
                        maxlen = r - l + 1;
                        maxRight = r;
                        maxLeft = l;
                    }
                }
            }
        }
        return s.substring(maxLeft, maxRight + 1);
    }

    @Test
    public void longestDuplicatedStr() {
        String str = "abcb";
        System.out.println(lengthOfLongestSubstringWrong(str));
    }



    public boolean isSymmetric(TreeNode root) {
        return check(root, root);
    }

    public boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
    }


    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        middle_ergodic(root, list);
        return list;
    }

    public void middle_ergodic(TreeNode root, List<Integer> list) {
        if (root == null) return;
        middle_ergodic(root.left, list);
        list.add(root.val);
        middle_ergodic(root.right, list);
    }


    public ListNode reverseList(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        while (head != null) {
            stack.push(head);
            head = head.next;
        }
        ListNode dummyNode = new ListNode(-1);
        if (stack.isEmpty()) {
            return null;
        }
        ListNode newHead = stack.pop();
        dummyNode.next = newHead;
        while (!stack.isEmpty()) {
            newHead.next = stack.pop();
            newHead = newHead.next;
            newHead.next = null;
        }
        return dummyNode.next;
    }

    public ListNode reverseList2(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummyNode = new ListNode(-1);
        ListNode pre;
        dummyNode.next = head;
        pre = dummyNode;
        int cnt = right - left;
        while (--left > 0) {
            head = head.next;
            pre = pre.next;
        }
        ListNode next;
        while (cnt-- > 0) {
            next = head.next;
            head.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return dummyNode.next;
    }

    @Test
    public void testArrayw() {
        ListNode head = new ListNode(1);
        ListNode head2 = new ListNode(2);
        ListNode head3 = new ListNode(3);
        ListNode head4 = new ListNode(4);
        head.next = head2;
        head2.next = head3;
        head3.next = head4;

        ListNode newHead = reverseList2(head);
        showListNode(newHead);
    }

    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        int length = m + n;

        int left = 0;
        int right = 0;
        if (m == 0) {
            nums1 = nums2;
//            for (int i : nums1) {
//                System.out.println(i);
//            }
            return;
        }
        if (n == 0) {
            return;
        }

        while (length-- > 0) {
            if (left < m && right < n) {
                if (nums1[left] > nums2[right]) {
                    int tmp = nums1[left];
                    nums1[left] = nums2[right];
                    nums2[right] = tmp;
                    left++;
                } else {
                    nums1[left] = nums1[left++];
                }
            } else if (left < m) {
                nums1[left] = nums1[left++];
            } else if (right < n) {
                int tmp = nums1[left];
                nums1[left] = nums2[right];
                nums2[right] = tmp;
                left++;
                right++;
            }
        }
    }

    // 既然空数组不让赋值的话，就用新数组代替了 还是不行
    public void merge3(int[] nums1, int m, int[] nums2, int n) {
        int length = m + n;

        int left = 0;
        int right = 0;
        if (m == 0) {
            nums1 = new int[nums2.length];
            nums1 = nums2;
            System.out.println(nums1);
            return;
        }
        if (n == 0) {
            return;
        }

        while (length-- > 0) {
            if (left < m && right < n) {
                if (nums1[left] > nums2[right]) {
                    int tmp = nums1[left];
                    nums1[left] = nums2[right];
                    nums2[right] = tmp;
                    left++;
                } else {
                    nums1[left] = nums1[left++];
                }
            } else if (left < m) {
                nums1[left] = nums1[left++];
            } else if (right < n) {
                int tmp = nums1[left];
                nums1[left] = nums2[right];
                nums2[right] = tmp;
                left++;
                right++;
            }
        }
        System.out.println(nums1);
    }

    private void merge4(int[] nums1, int m, int[] nums2, int n) {
        while (n > 0 || m > 0) {
            if (n == 0) return;
            if (m > 0 && nums1[m - 1] > nums2[n - 1]) {
                nums1[m + n - 1] = nums1[--m];
            } else {
                nums1[m + n - 1] = nums2[--n];
            }
        }
    }

    @Test
    public void testMerge() {
//        int[] num = new int[]{1,2,3,0,0,0};
//        int m =3;
//        int[] num2 = new int[]{4,5,6};
//        int n = 3;
//        即使你通过赋值操作改变了数组变量的引用，也无法改变原始数组对象的长度
        int[] num = new int[]{};
//        int[] num = new int[]{1,2,3,0,0,0};
        int m = 0;
        System.out.println(num);//1
        int[] num2 = new int[]{1};
//        int[] num2 = new int[]{4,5,6};
        int n = 1;
        System.out.println(num2);//2
        merge4(num, m, num2, n);//2
        System.out.println(num);//1
        for (int i : num) {
            System.out.println(i);
        }
    }

    public int maxDepth22(TreeNode root) {
        if (root == null) return 0;
        //one path
        return Math.max(get_depth(root.left), get_depth(root.right)) + 1;
    }

    private int get_depth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        if (root.left != null && root.right != null) return Math.max(get_depth(root.left), get_depth(root.right)) + 1;
        if (root.left == null && root.right != null) return get_depth(root.right) + 1;
        if (root.left != null && root.right == null) return get_depth(root.left) + 1;
        return 0;
    }

    public int maxDepth(TreeNode root) {
//        左侧递归返回2
//        左侧递归返回2
//        右侧递归返回2
//        右侧递归返回4
//        5
//        if (root == null) return 0;
//        int l = 0, r = 0;
//        if (root.left != null) {
//            l = maxDepth(root.left)+1;
//            System.out.println("左侧递归返回" +l);
//        }
//        if (root.right != null) {
//            r = maxDepth(root.right)+1;
//            System.out.println("右侧递归返回" +r);
//        }
//        return Math.max(l, r)+1;
        if (root == null) return 0;
        int l = 0, r = 0;
        if (root.left != null) {
            l = maxDepth(root.left);
            System.out.println("左侧递归返回" +l);
        }
        if (root.right != null) {
            r = maxDepth(root.right);
            System.out.println("右侧递归返回" +r);
        }
        return Math.max(l, r)+1;
//        左侧递归返回1
//        左侧递归返回1
//        右侧递归返回1
//        右侧递归返回2
//        3
    }







    @Test
    public void HashMapTest() {
        Integer[] num = new Integer[]{3,9,20,null,null,15,7};
        TreeNode root = TreeUtils.intArrayToTreeNode(num);
        int deep = maxDepth(root);
        System.out.println(deep);
    }

    public int minDepth(TreeNode root) {
        if(root==null) return 0;
        return Math.min(minDepth(root.left),minDepth(root.right))+1;
    }

    @Test
    public void minDepth() {
        Integer[] num = new Integer[]{2,null,3,null,4,null,5,null,6};
        TreeNode root = TreeUtils.intArrayToTreeNode(num);
        int deep = minDepth(root);
        System.out.println(deep);
    }
    //最长递增子序列
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int max = 1; // 中药
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;  //重要
            for (int j = 0; j < i; j++) {
                if(nums[i]>nums[j]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }


    public int lengthOfLIS2(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int max = 1; // 中药
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;  //重要
            for (int j = 0; j < i; j++) {
                if(nums[i]>nums[j]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }


    public int findNumberOfLIS(int[] nums) {
        if(nums.length==0){
            return 0;
        }
        int[] dp = new int[nums.length];
        int max = 1;
        int[] cnt = new int[nums.length];
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            cnt[i] = 1;
            for (int j = 0; j < i; j++) {
                if(nums[i] > nums[j]){
//                    dp[i] = Math.max(dp[i],dp[j]+1);
                    if(dp[i] < dp[j]+1){
                        dp[i] = dp[j]+1;
                        cnt[i] = cnt[j];
                    }else if(dp[j] + 1 == dp[i]){
                        cnt[i] += cnt[j];
                    }
                }
            }
//            max = Math.max(max,dp[i]);
            if(dp[i]>max){
                max = dp[i];
                ans = cnt[i];
            }
            else if(dp[i] == max){
                ans += cnt[i];
            }
        }
        return ans;
    }
    public int findLengthOfLCISFail(int[] nums) {
        if(nums.length==0) return 0;
        int[] diff= new int[nums.length];
        int max = 0;
        for(int i = 0;i<nums.length;i++){
            if(i == 0) diff[0] =1;
            else if(nums[i] < nums[i-1]){
                diff[i] = 1;
            }
            else {
                diff[i] = nums[i] - nums[i-1];
            }
        }
        int cnt = 0;
        int i = 0;
        while( i <nums.length){
            while( i<nums.length && diff[i] > 0){
                cnt++;
                i++;
            }
            if(i<nums.length && diff[i]<=0){i++;}
            max = Math.max(max,cnt);
            cnt = 0;
        }
        return max;
    }
    public int findLengthOfLCIS(int[] nums) {
        int ans= 0;
        int n = nums.length;
        int start= 0;
        for (int i = 0; i < n; i++) {
            if(i>0 && nums[i] <= nums[i -1]) start = i;
            ans = Math.max(ans, i - start + 1);
        }
        return ans;
    }
    @Test
    public void findLengthOfLCISTest() {
        int[] num = new int[]{1,3,5,4,2,3,4,5};
        System.out.println(findLengthOfLCIS(num));
    }



    @Test
    public void bitOperator() {
        int i1 = Integer.highestOneBit(10);
        System.out.println(i1);

        int n = 17;
        n |= n>>>1;
        n |= n>>>2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        n = n - (n >>> 1);
        System.out.println(n);

        ArrayList<Integer> c ;
        LinkedList<Integer> a;
    }


    public boolean isPalindrome(String s) {
        StringBuffer sgood = new StringBuffer();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                sgood.append(Character.toLowerCase(ch));
            }
        }
        StringBuffer sgood_rev = new StringBuffer(sgood).reverse();
        return sgood.toString().equals(sgood_rev.toString());
    }


    @Test
    public void finallyException(){
        try {
            int a = 1 / 0;
        } catch (Exception e) {

        }finally {
            try {
                int[] b = new int[1];
                System.out.println(b[2]);
            } catch (Exception er) {
                er.printStackTrace();
            }
        }
    }

    @Test

    public void testToNumber(){
        System.out.println(titleToNumber("AB"));
        System.out.println(titleToNumberReverse("AB"));
    }
    // 正向来算的
    public int titleToNumber(String columnTitle) {
        char[] chars = columnTitle.toCharArray();
        int sum = 0;
        int jinzhi = 1;
        for (int i = chars.length-1; i >=0 ; i--) {
            int x = getX(chars[i]);
            sum += x * jinzhi;
            jinzhi *= 26;
        }
        return sum;
    }

    private static int getX(char chars) {
        return chars - 'A' + 1;
    }
    // 逆向来算
    public int titleToNumberReverse(String columnTitle) {
        char[] chars = columnTitle.toCharArray();
        int sum = 0;
        for (int i = 0; i <chars.length ; i++) {
            sum = sum * 26 + getX(chars[i]);
        }
        return sum;
    }
    @Test
    public void testReverseBits(){
//        System.out.println(reverseBits(-3));
//        System.out.println(reverseBits(43261596));
        System.out.println( minMaxGame(new int[]{1, 3, 5, 2, 4, 8, 2, 2}));
    }

    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int sum = 0;
        for (int i = 0; i < 32; i++) {
            int bit = n & 1;
            sum = sum * 2 + bit;
            n >>>= 1;
        }
        return sum;
    }


    public int minMaxGame(int[] nums) {
        int length = nums.length;
        if(length == 1){
            return nums[0];
        }
        int newLength;
        if((length & 1) == 1){
            newLength = length/2 + 1;
        }else{
            newLength = length/2;
        }
        int[] newNums = new int[newLength];
        for (int i = 0; i < length / 2; i++) {
            if((i&1) == 0){
                newNums[i] = Math.min(nums[i*2],nums[i*2+1]);
            }else{
                newNums[i] = Math.max(nums[i*2],nums[i*2+1]);
            }
        }
        if ((newLength & 1) == 1 && newLength !=1) {
            newNums[length/2] = nums[length-1];
        }
        return minMaxGame(newNums);
    }

    public List<String> findWords(char[][] board, String[] words) {
        Set<String> result = new HashSet<>();
        for (String word : words) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if(board[i][j] == word.charAt(0)){
                        if (backtrack(board, i, j, 0, word.toCharArray())) {
                            result.add(word);
                            break;
                        }
                    }
                }
            }
        }
        return new ArrayList<>(result);
    }

    private boolean backtrack(char[][] board, int i ,int j, int wordIndex,char[] wordChars){
        if (wordIndex == wordChars.length) {
            return true;
        }
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != wordChars[wordIndex]) {
            return false;
        }
        board[i][j] = '#';

        boolean result = backtrack(board, i - 1, j, wordIndex + 1, wordChars) ||
                backtrack(board, i, j + 1, wordIndex + 1, wordChars)||
                backtrack(board, i+1, j, wordIndex + 1, wordChars)||
                backtrack(board, i, j - 1, wordIndex + 1, wordChars);

        board[i][j] = wordChars[wordIndex];
        return result;
    }


    public int waysToStep(int n) {
        long[] a= new long[n+3];
        a[0] = 1;
        a[1] = 1;
        a[2] = 2;
        for (int i = 3; i <= n; i++) {
            a[i] = (a[i-3] + a[i-2] + a[i-1]) % 1000000007;
        }
        return (int)a[n];
    }

    public long countQuadruplets(int[] nums) {
        int n = nums.length;
        int[] v = new int[n];
        long as =0;
        for (int i = 1; i < n; i++) {
            int c = 0;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    as += v[j];
                    c++;
                } else {
                    v[j]+=c;
                }
            }
        }
        return as;
    }

    @Test
    public void testGetrow(){
        System.out.println(generate(5));
    }
//自己做的实现  记得i 的开始 和下标处理
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 1; i <= numRows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                if (j == 0 || j == i - 1) {
                    row.add(1);
                } else {
                    row.add(ans.get(i-2).get(j)+ans.get(i-2).get(j-1));
                }
            }
            ans.add(row);
        }
        return ans;
    }

    public List<Integer> getRow(int rowIndex) {
        if(rowIndex==0) return new ArrayList<Integer>(){{add(1);}};
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i <= rowIndex/2; i++) {
            int begin = 1;
            int row = rowIndex - i;
            while(row-- > 0){
                begin+=i;
            }
            ans.add(begin);
        }
        if ((rowIndex & 1) == 1) {
            List<Integer> reversed = new ArrayList<>(ans);
            Collections.reverse(reversed);
            ans.addAll(reversed);
        }else{
            List<Integer> reversed = ans.subList(0, ans.size() - 1);
            Collections.reverse(reversed);
            ans.addAll(reversed);
        }
        return ans;
    }
    public List<Integer> getRow2(int rowIndex) {
        int[]dp = new int[rowIndex];
        dp[0] = 1;
        for (int i = 0; i < rowIndex; i++) {
            for (int j = i; j >0 ; j--) {
                dp[j] = dp[j - 1] + dp[j];
            }
        }
        return Arrays.stream(dp).boxed().collect(Collectors.toList());
    }

    public List<Integer> getRow3(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        row.add(1);
        for (int i = 1; i < rowIndex; i++) {
            row.add(0);
            for (int j = i; j > 0; j--) {
                row.set(j, row.get(j) + row.get(j-1));
            }
        }
        return row;
    }
    // 我自己的实现， 如果j 从前往后遍历 会覆盖上层结果，所以，只能最后加0 往前遍历
    public List<Integer> getRow4(int rowIndex) {
        List<Integer> ans= new ArrayList<>();
        ans.add(1);
        if(rowIndex == 1) return ans;
        for (int i = 1; i < rowIndex ; i++) {
            ans.add(0);
            for (int j = i; j > 0; j--) {
                ans.set(j,ans.get(j)+ans.get(j-1));
            }
        }
        return ans;
    }

    @Test
    public void testLevelOrder(){
        TreeNode treeNode = TreeUtils.intArrayToTreeNode(new Integer[]{1, 2, 2, 3, null, null, 3, 4, 5, null, 677, 3});
//        List<Integer> integers = levelOrder(treeNode);
//        integers.stream().forEach(System.out::println);
//        beforeOrder(treeNode);
        List<List<Integer>> lists = levelOrderWithLevel(treeNode);
//        List<List<Integer>> lists = levelOrderReal(treeNode);
        for (List<Integer> list : lists) {
            list.forEach(System.out::print);
            System.out.println();
        }
    }

    // 层序遍历
    public List<Integer> levelOrder(TreeNode root) {
        if(root == null)return null;
        Queue<TreeNode> queue = new LinkedList<>();
        List<Integer> ans = new ArrayList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            int val = poll.getVal();
            ans.add(val);
            if (poll.getLeft() != null) {
                queue.add(poll.getLeft());
            }if (poll.getRight() != null) {
                queue.add(poll.getRight());
            }
        }
        return ans;
    }


    public void beforeOrder(TreeNode root) {
        if (root == null) return;
//        System.out.println(root.getVal());  //前序遍历
        if (root.left != null) {
            beforeOrder(root.left);
        }
//        System.out.println(root.getVal());  //中序遍历

        if (root.right != null) {
            beforeOrder(root.right);
        }

        System.out.println(root.getVal());  //后序遍历
    }

    // 带层号
    class TreeNodeLevel{
        TreeNode treeNode;
        int level;

        public TreeNodeLevel(TreeNode root, int cnt) {
            this.treeNode = root;
            this.level = cnt;
        }

        public TreeNode getTreeNode() {
            return treeNode;
        }

        public int getLevel() {
            return level;
        }
    }

    public List<List<Integer>> levelOrderWithLevel(TreeNode root) {
        if(root == null)return null;
        List<TreeNodeLevel> outputList = new ArrayList<>();
        Queue<TreeNodeLevel> queue = new LinkedList<>();
        int cnt = 0;
        queue.offer(new TreeNodeLevel(root, cnt));
        while(!queue.isEmpty()){
            int n = queue.size();
            cnt++;
            for (int i = 0; i < n; i++) {
                TreeNodeLevel treeNode = queue.poll();
                TreeNode node = treeNode.getTreeNode();
                outputList.add(treeNode);
                if (node.left != null) {
                    queue.offer(new TreeNodeLevel(node.left,cnt));
                }
                if (node.right != null) {
                    queue.offer(new TreeNodeLevel(node.right,cnt));
                }
            }
        }
        List<TreeNodeLevel> subList = Collections.singletonList(outputList.get(0));
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 1; i < outputList.size(); i++) {
            if (!Objects.equals(outputList.get(i).getLevel(), outputList.get(i - 1).getLevel())) {
                ans.add(subList.stream().map(TreeNodeLevel::getTreeNode).map(TreeNode::getVal).collect(Collectors.toList()));
                subList = new ArrayList<>();
            }
            subList.add(outputList.get(i));
        }
        ans.add(subList.stream().map(TreeNodeLevel::getTreeNode).map(TreeNode::getVal).collect(Collectors.toList()));
        return ans;
    }


    public List<List<Integer>> levelOrderReal(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }
//        List<Integer> row = new ArrayList<>();
        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> row = new ArrayList<>();
//            TreeNode poll = queue.poll();
//            row.add(poll.val);
            // 其实是一个缓存的概念
            for (int i = 0; i < n; i++) {
                TreeNode poll = queue.poll();
                row.add(poll.val);
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
            ans.add(row);
        }
        return ans;
    }

    public List<List<Integer>> levelOrderJc(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }
//        List<Integer> row = new ArrayList<>();
        int cnt = 0;
        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> row = new ArrayList<>();
//            TreeNode poll = queue.poll();
//            row.add(poll.val);
            // 其实是一个缓存的概念
            for (int i = 0; i < n; i++) {
                TreeNode poll = queue.poll();
                row.add(poll.val);
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
            if((cnt++ & 1) == 1) Collections.reverse(row);
            ans.add(row);
        }
        return ans;
    }


    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }
        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> row = new ArrayList<>();
            for (int i = 0; i <n; i++) {
                TreeNode poll = queue.poll();
                row.add(poll.val);
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
            ans.add(row);
        }
        Collections.reverse(ans);
        return ans;
    }


    public List<List<Integer>> levelOrder(NTreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        Queue<NTreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }
        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> row = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                NTreeNode poll = queue.poll();
                row.add(poll.val);
                for (int j = 0; j < poll.children.size(); j++) {
                    queue.offer(poll.children.get(j));
                }
            }
            ans.add(row);
        }
        return ans;
    }

    @Test
    public void convertToTitle(){
//        System.out.println(convertToTitle(701));
        System.out.println(majorityElement(new int[]{1}));
//        System.out.println(majorityElement(new int[]{3,3,2,2,2}));
    }

    public String convertToTitle(int columnNumber) {
        StringBuilder sb = new StringBuilder();
        while (columnNumber > 0) {
            columnNumber--; // 这一行是最重要的，因为从1 开始的，所以进制不对的
            sb.insert(0, (char) (columnNumber % 26 + 'A'));
            columnNumber /= 26;

        }
        return sb.toString();
    }
// 重要的
    public String convertToTitleReal(int cn) {
        StringBuilder sb = new StringBuilder();
        while (cn > 0) {
            cn--;
            sb.append((char)(cn%26+'A'));
            cn/=26;
        }
        sb.reverse();
        return sb.toString();
    }

    public int majorityElement(int[] nums) {
        int min = 0;
        int max= nums.length -1;
        if (max == 0) {
            return nums[0];
        }
        int majority = (max - min) / 2 + 1;

        Random rand = new Random();
        while (true) {
            int candidateIndex = rand.nextInt(nums.length - 1);
            int candidate = nums[candidateIndex];
            int cnt=0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == candidate) {
                    cnt++;
                }
            }
            if (cnt >= majority) {
                return candidate;
            }
        }
    }

    public ListNode reverseBetween2(ListNode head, int left, int right) {
        if(head == null)return null;

        ListNode dummyNode = new ListNode();
        dummyNode.next = head;
        ListNode pre = dummyNode;
        int cnt = left;
        while(cnt-- >0) {
            pre = pre.next;
            head = head.next;
        }
        // pre -> head .... right

        ListNode follow = head.next;
        for (int i = 0; i < right - left + 1; i++) {
            head.next = follow.next;
            follow.next = pre.next;
            pre.next = follow;
            follow = head.next;
        }

        return dummyNode.next;
    }

    public boolean hasCycle(ListNode head) {
        if(head == null || head.next == null) return false;
        ListNode follow = head.next;
        while (true) {
            follow = follow.next.next;
            head = head.next;
            if (follow == head) {
                return true;
            }
            if (follow.next == null || head.next == null) {
                return false;
            }
        }
    }

    @Test
    public void testQuickSort(){
        int[] ints = {3, 2, 1, 5, 6, 4};
//        quickSort(ints,0,5);
//        System.out.println(Arrays.toString(ints));

        System.out.println(findKthLargest(ints, 3));

//        System.out.println(majorityElement(new int[]{3,3,2,2,2}));
    }

    public void quickSort(int[] a, int left, int right) {
        if (left >= right) {
            return;
        }
        int i = left, j = right;
        int pivot = a[right];

        while (i < j) {
            while (i < j && a[i] < pivot) {
                i++;
            }
            if (i < j) a[j--] = a[i];
            while (i < j && a[j] > pivot) {
                j--;
            }
            if (i < j) a[i++] = a[j];
        }
        a[i] = pivot;
        quickSort(a, left, i - 1);
        quickSort(a, i + 1, right);
    }



    public int findKthLargest(int[] nums, int k) {
        return quickSort3(nums, 0, nums.length - 1, k);
    }


    public int quickSort3(int[] a, int left, int right, int k) {
        if (left >= right) {
            return a[k-1];
        }
        int i = left, j = right;
        int pivot = a[right];

        while (i < j) {
            while (i < j && a[i] > pivot) {
                i++;
            }
            if (i < j) a[j--] = a[i];
            while (i < j && a[j] < pivot) {
                j--;
            }
            if (i < j) a[i++] = a[j];
        }
        a[i] = pivot;
        if (k - 1 <= i - 1 && k - 1 >= left) {
            return quickSort3(a, left, i - 1, k);
        } else {
            return quickSort3(a, i + 1, right, k);
        }
    }


    public int findKthLargest2(int[] nums, int k) {
        return quickSort4(nums, 0, nums.length - 1, k);
    }


    public int quickSort4(int[] a, int left, int right, int k) {
        if (left == right) {
            return a[k-1];
        }
        int i = left, j = right;
        int pivot = a[right];

        while (i < j) {
            while (i < j && a[i] > pivot) {
                i++;
            }
            if (i < j) a[j--] = a[i];
            while (i < j && a[j] < pivot) {
                j--;
            }
            if (i < j) a[i++] = a[j];
        }
        a[i] = pivot;
        if (k - 1 <= i - 1 && k - 1 >= left) {
            return quickSort4(a, left, i - 1, k);
        } else if (k - 1 >= i + 1 && k - 1 <= right) {
            return quickSort4(a, i + 1, right, k);
        } else {
            return pivot;
        }
    }
    //三数之和
    @Test
    public void threeSum(){
        List<List<Integer>> lists = threeSum(new int[]{1, -1, 2, -2, 3});
        for (List<Integer> list : lists) {
            for (Integer integer : list) {
                System.out.print(integer +" ");
            }
            System.out.println();
        }
    }
    // 答案中不可以包含重复的三元组。
    public List<List<Integer>> threeSum(int[] arr) {
        Set<List<Integer>> listSet = new HashSet<>();
        int n = arr.length;
        // 以中间数据为桥梁
        for (int i = 1; i < n-1; i++) {
            Map<Integer, Integer> map = new HashMap<>();
            int left = i-1;
            while (left >= 0) {
                map.put(-(arr[left] + arr[i]), left);
                left--;
            }
            int right = i+1;
            while (right < n) {
                if(map.containsKey(arr[right])){
                    List<Integer> oneResultList = Arrays.asList(arr[map.get(arr[right])], arr[i], arr[right]);
                    oneResultList.sort(Integer::compareTo);
                    listSet.add(oneResultList);
                }
                right++;
            }

        }
        return new ArrayList<>(listSet);
    }

    public int findFinalValue(int[] nums, int original) {
        int n =  nums.length;
        if(n == 0) return 0;
        int ans;
        Map<Integer, Boolean> booleanMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (nums[i] % original == 0) {
                booleanMap.put(nums[i] / original, true);
            }
        }
        for (int i = 1; ; i = i*2) {
            if(!booleanMap.containsKey(i)){
                return i*original;
            }
        }
    }

    @Test
    public void hasPathTest(){
        Integer[] num = new Integer[]{1,2,3};
        TreeNode root = TreeUtils.intArrayToTreeNode(num);
        System.out.println(hasPathSumReal(root, 5));
//        System.out.println(hasPathSumFailure(root, 5));
    }

    public boolean hasPathSumFailure(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        return getPathSum(root, targetSum);
    }

    public boolean getPathSum(TreeNode root, int remain) {
//        没有判断 root 是否为叶子节点。比如 root 为空的话，题目的意思是要返回 False 的，而上面的代码会返回 sum == 0。又比如，对于测试用例 树为 [1,2], sum = 0 时，上面的结果也会返回为 True，因为对于上述代码，只要左右任意一个孩子的为空时 sum == 0 就返回 True。
        // 这里少了一步 root == null 的判断
        if (root.left == null && root.right == null ) {
            return remain == root.val;
        }
        return getPathSum(root.left, remain - root.val) ||
                getPathSum(root.right, remain - root.val);
    }

    public boolean hasPathSumReal(TreeNode root, int targetSum) {
        // 1
        if (root == null) {
            return false;
        }
        // 2
        if (root.left == null && root.right == null ) {
            return targetSum == root.val;
        }
        // 1，,2 缺一不可
        return hasPathSumReal(root.left, targetSum - root.val) ||
                hasPathSumReal(root.right, targetSum - root.val);
    }

    @Test
    public void testisAnagram(){
        System.out.println(isAnagram("car", "rat"));

    }
    public boolean isAnagram(String s, String t) {
        char[] charArray1 = s.toCharArray();
        char[] charArray2 = t.toCharArray();
        if(charArray1.length != charArray2.length)
            return false;
        int[] charCnt1 = new int[26] ;

        for (int i = 0; i < charArray1.length; i++) {
            charCnt1[charArray1[i]-'a']++;
            charCnt1[charArray2[i]-'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if(charCnt1[i]!=0)
                return false;
        }
        return true;
    }

    @Test
    public void testIndexOf(){
//        System.out.println(strStr("a", "a"));
        System.out.println(1+ 3/3);
    }

    public int strStr(String haystack, String needle) {
        char[] chars1 = haystack.toCharArray();
        char[] chars2 = needle.toCharArray();
        int length = chars1.length;
        int length2 = chars2.length;
        if (length2 > length) {
            return -1;
        }
        for (int i = 0; i <= length-length2; i++) {
                int j = 0;
                int k = i;
                while(j<length2 && i+j < length && chars1[k]==chars2[j]) {
                    j++;k++;
                }
                if(j==length2) return i;
        }
        return -1;
    }

    // 二叉树是否平衡
    public boolean isBalanced(TreeNode root) {
        if(root == null) return true;
        return Math.abs(getDeepth(root.left) - getDeepth(root.right))<=1 && isBalanced(root.left) && isBalanced(root.right);
    }

    private int getDeepth(TreeNode root) {
        if(root == null) return 0;
        else{
            return Math.max(getDeepth(root.left), getDeepth(root.right))+1;
        }
    }
    // 链表相交    a+c+b == b+c+a
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA;
        ListNode b = headB;
        if(a==null || b == null) return null;
        while(a != b){
            a = a == null?headB : a.next;
            b = b == null?headA : b.next;
        }
        return a;
    }


    @Test
    public void testHapyy(){
        System.out.println(isHappy(2));
    }

    public boolean isHappy(int n) {
        if(n==1){
            return true;
        }else if(n == 2 || n == 4){
            return false;
        }
        List<Integer> list = toEveryNumber(n);
        Integer n1 = toResultSqr(list);
        return isHappy(n1);
    }

    List<Integer> toEveryNumber(int n) {
        List<Integer> list = new ArrayList<>();
        while(n>0){
            list.add(n % 10);
            n /=10;
        }
        return list;
    }

    Integer toResultSqr(List<Integer> list) {
        int sum =0;
        for (int i = 0; i < list.size(); i++) {
            Integer integer = list.get(i);
            sum += integer * integer;
        }
        return sum;
    }

    @Test
    public void testIsomorphic(){
//        System.out.println(isIsomorphic2("abcdefghijklmnopqrstuvwxyzva","abcdefghijklmnopqrstuvwxyzck"));
        System.out.println(isIsomorphic2("foo","bar"));
    }

    public boolean isIsomorphic(String s, String t) {
        char[] arr1 = s.toCharArray();
        char[] arr2 = t.toCharArray();
        if(arr2.length != arr1.length) return false;
        String string1 = isIsomorphicFunc(arr1).toString();
        System.out.println(string1);
        String string2= isIsomorphicFunc(arr2).toString();
        System.out.println(string2);
        // abcda
        // ehije
//        都转化成12341
        return string1.equals(string2);
    }

    private StringBuilder isIsomorphicFunc(char[] arr1) {
        StringBuilder sb = new StringBuilder();
        int num= 0;
        Map<Character,Character> map = new HashMap<>();
        for (int i = 0; i < arr1.length; i++) {
            char c = arr1[i];
            if(map.containsKey(c)){
               sb.append(map.get(c));
            }
            else {
                map.put(c, (char)num);
                sb.append(num++);
            }
        }
        return sb;
    }

    public boolean isIsomorphic2(String s, String t) {
        char[] arr1 = s.toCharArray();
        char[] arr2 = t.toCharArray();
        if(arr2.length != arr1.length) return false;
        Map<Character, Character> aMap = new HashMap<Character, Character>();
        Map<Character, Character> bMap = new HashMap<Character, Character>();
        for (int i = 0; i < arr1.length; i++) {
            char a = arr1[i],b=arr2[i];
            if(aMap.containsKey(a) && aMap.get(a) != b || bMap.containsKey(b) && bMap.get(b)!=a){
                return false;
            }
            aMap.put(a, b);
            bMap.put(b, a);
        }
        return true;
    }



    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set2 = toSet(nums2);
        Set<Integer> set1 = toSet(nums1);
        List<Integer> res = new ArrayList<>();
        set1.forEach(
                key -> {
                    if (set2.contains(key)) {
                        res.add(key);
                    }
                }
        );
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    private Set<Integer> toSet(int [] nums1){
        Set a = new HashSet<Integer>();
        for (int i = 0; i < nums1.length; i++) {
            a.add(nums1[i]);
        }
        return a;
    }


    public int[] intersection2(int[] nums1, int[] nums2) {
        Set<Integer> set1 = toSet2(nums1);
        Set<Integer> res = new HashSet<>();
        for (int i = 0; i < nums2.length; i++) {
            if (set1.contains(nums2[i])) {
                res.add(nums2[i]);
            }
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    private Set<Integer> toSet2(int [] nums1){
        Set a = new HashSet<Integer>();
        for (int i = 0; i < nums1.length; i++) {
            a.add(nums1[i]);
        }
        return a;
    }


    public String reverseVowels(String s) {
        char[] charArray = s.toCharArray();
        int i = 0, j = charArray.length-1;
        char temp;
        while(i<j){
            while (!isyuanyin(charArray[i]) && i < j) {
                i++;
            }
            temp = charArray[i];
            while (!isyuanyin(charArray[j]) && i < j) {
                j--;
            }
            charArray[i] =charArray[j];
            charArray[j] = temp;
            i++;j--;
        }
        return String.valueOf(charArray);
    }
    private boolean isyuanyin(char c){
        return (c == 'a' || c == 'e' || c == 'i' ||
                c == 'o' || c == 'u'||
                c == 'A' || c == 'E' || c == 'I' ||
                c == 'O' || c == 'U');
    }


    @Test
    public void testIntersection(){
//        int[] intersection = intersection(new int[]{12, 3, 33}, new int[]{2, 3, 4, 55});
//        for (int i : intersection) {
//            System.out.println(i);
//        }

//        System.out.println(reverseVowels("hello"));
        boolean perfectSquare = isPerfectSquare(16);
        System.out.println(perfectSquare);
    }


    public boolean isPerfectSquare(int num) {
        int originalValue = num;
        if(num<0) return false;
        if(num == 1 )return true;
        if(num/2>0){
            num /= 2;
            if (num * num <= originalValue) {
                return func(num, originalValue,originalValue);
            }
            if (num * num > originalValue) {
                return func(1,num-1,originalValue);
            }
        }
        return false;
    }


    public boolean func(int begin, int right, int originalValue){
        if(begin > right) return false;
        int num = (right - begin) /2;
        if (num * num <= originalValue) {
            return func(num, originalValue,originalValue);
        }
        if (num * num > originalValue) {
            return func(1,num-1,originalValue);
        }
        return false;
    }


    public boolean isPerfectSquareFalse(int num) {
        int l = 0, r = num;
        while(l<r){
            int mid = l + r + 1 >> 1;
            if(mid * mid<= num) l=mid;
            else r = mid-1;
        }
        return l * l == num;
    }


    public boolean isPerfectSquareTrue(int num) {
        long l = 0, r = num;
        while(l<r){
            long mid = l + r + 1 >> 1;
            if(mid * mid<= num) l=mid;
            else r = mid-1;
        }
        return l * l == num;

    }

}