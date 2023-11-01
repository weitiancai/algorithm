package sort;

import java.util.Arrays;

public class HeapSort {
    // 堆  就是 左右孩子节点都比父节点小的书
    // 构造小顶堆 就是如果堆顶的数据放最后，那么就是降序
//    最快 ologn
//    最慢 ologn
//     平均 ologn
    public static void main(String[] args) {
        int[] arr = new int[]{1, 3, 5,2, 0,10,6};
        System.out.println(Arrays.toString(arr));
        arr = myHeapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static int[] heapSort(int[] arr, int length) {
        //构件二叉堆
        for (int i = (length-2)/2; i >=0 ; i--) {
            arr = downAdjust(arr,i,length);
        }
        // 进行堆排序
        for (int i = length - 1; i >= 1; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            arr = downAdjust(arr, 0, i);
        }
        return arr;
    }

    public static int[] downAdjust(int[] arr,int parent,int length){
        // 临时保证要下沉的元素
        int temp = arr[parent];
        // 定位左孩子节点
        int child = 2 * parent + 1;
        // 开始下沉
        while(child < length){
            // 如果右孩子 比左孩子小 ，则定位到右孩子
            if (child + 1 < length && arr[child] > arr[child + 1]) {
                child++;
            }
            // 如果父节点比孩子节点小或等于 则下沉结束
            if(temp <= arr[child])
                break;
            // 单向赋值
            arr[parent] = arr[child];
            parent = child;
            child = 2 * parent + 1;
        }
        arr[parent] = temp;
        return arr;
    }


    public static int[] myHeapSort(int[] arr) {
        int n = arr.length;
        for (int i = n/2-1; i>=0; i--) {
            heapify(arr, i, n);
        }
        for (int i = n-1; i > 0; i--) {
            int tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;
            heapify(arr, 0, i);
        }
        return arr;
    }

    public static void heapify(int[] arr, int i, int n) {
        int l = i * 2 + 1;
        int r = i * 2 + 2;
        int p = i;
        while (true) {
            if(l<n && arr[l] > arr[p]){
                p = l;
            }
            if (r < n && arr[r] > arr[p]) {
                p = r;
            }
            if(p == i)
                break;
            int tmp = arr[i];
            arr[i] = arr[p];
            arr[p] = tmp;
            p = i;
        }
    }

    public int maximumPopulation(int[][] logs) {
        int n = logs.length;
        if(n==0) return 0;
        int max = 0;
        int[] range = new int[101];
        for (int i = 0; i < n; i++) {
            for (int j = logs[i][0] -1950; j < logs[i][1]-1950; j++) {
                range[j]++;
                max = Math.max(max, range[j]);
            }
        }
        for (int i = 0; i < range.length; i++) {
            if (range[i] == max) {
                return i+1960;
            }
        }
        return logs[0][0];
    }
    //最多人口年份最早
    public int maximumPopulationReal(int[][] logs) {
        int res = 0,cnt= 0;
        for (int i = 1950; i <= 2050; i++) {
            int s =0;
            for (int[] log : logs) {
                if(i>=log[0] && i<log[1])
                    s++;
            }
            if(s>cnt){
                res = i;
                cnt = s;
            }
        }
        return res;
    }
}
