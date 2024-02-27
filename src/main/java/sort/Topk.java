package sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Topk {
    public static void main(String[] args) {
        int[] nums = new int[]{12, 3, 4, 5, 6, 78, 9, 0};
        System.out.println(topKMax(nums, 5));

    }

    private static List<Integer> topKMax(int[] nums, int k) {
        // 寻找前k个最小数，一次将小顶堆大小定义为k
        PriorityQueue<Integer> pq = new PriorityQueue<>(k);
        for (int i = 0; i < nums.length; i++) {
            if (i < k) {
                pq.offer(nums[i]);
            } else if (nums[i] > pq.peek()) {
                pq.poll();
                pq.offer(nums[i]);
            }
        }
        List<Integer> ans = new ArrayList<>();
        while (!pq.isEmpty()) {
            ans.add(pq.poll());
        }
        return ans;
    }

    private static List<Integer> topKMin(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(k, (o1, o2) -> o2 - o1);
        for (int i = 0; i < nums.length; i++) {
            if (i < k) {
                pq.offer(nums[i]);
            } else if (nums[i] < pq.peek()) {
                pq.poll();
                pq.offer(nums[i]);
            }
        }
        return null;
    }
}
