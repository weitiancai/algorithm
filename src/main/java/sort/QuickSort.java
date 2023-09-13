package sort;

import java.util.Arrays;

public class QuickSort {
//    public static void quickSort(int[] arr, int low, int high) {
//        int i,j,temp,t;
//        if(low>high){
//            return;
//        }
//        i=low;
//        j=high;
//        //temp就是基准位
//        temp = arr[low];
//
//        while (i<j) {
//            //先看右边，依次往左递减
//            while (temp<=arr[j]&&i<j) {
//                j--;
//            }
//            //再看左边，依次往右递增
//            while (temp>=arr[i]&&i<j) {
//                i++;
//            }
//            //如果满足条件则交换
//            if (i<j) {
//                t = arr[j];
//                arr[j] = arr[i];
//                arr[i] = t;
//            }
//
//        }
//        //最后将基准为与i和j相等位置的数字交换
//        arr[low] = arr[i];
//        arr[i] = temp;
//        //递归调用左半数组
//        quickSort(arr, low, j-1);
//        //递归调用右半数组
//        quickSort(arr, j+1, high);
//
//    }

    // 快速排序
    // 平均 o(logn)
    // 最快 o(logn)
    // 最慢 o(n^2)
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = arr[left];
        int i = left, j = right;
        while (i < j) {
            while (i < j && arr[j] >= pivot) {
                j--;
            }
            if (i < j) {
                arr[i++] = arr[j];
            }
            while (i < j && arr[i] < pivot) {
                i++;
            }
            if (i < j) {
                arr[j--] = arr[i];
            }
        }
        // 这里arr[i]\arr[j]均可因为最后都是 i==j
        arr[j] = pivot;
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }
    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 4, 7, 1, 3};
        QuickSort.quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}