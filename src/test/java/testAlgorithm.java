import Utils.TreeUtils;
import leetCode.TreeNode;
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
//自己做的实现
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
}