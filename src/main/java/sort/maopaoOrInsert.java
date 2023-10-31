package sort;

import java.util.Arrays;

public class maopaoOrInsert {
    public static void main(String[] args) {
        int[] arr = new int[] {435,8,24,2,9,456,45,63,34};
//        mpsort(arr);
//        insertSort(arr);
        selectionSort(arr);
        System.out.println(Arrays.toString(arr));

    }
    // 插入排序，平均时间复杂度 o(n的平方）
    // 本身有序的情况下， 最佳排序复杂度 o(n)
    private static void insertSort(int[] arr) {
        int length = arr.length;
        int key,j;
        for (int i = 1; i < length; i++) {
            key = arr[i];
            j = i-1;
            while (j >= 0 && arr[j] > key) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    // 冒泡排序，平均时间复杂度 o(n的平方）
    // 本身有序的情况下， 最佳排序复杂度 o(n)
    private static void mpsort(int[] arr) {
        for (int i = arr.length -1 ; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }
    }

    private static void selectionSort(int[] arr) {
        int n= arr.length;
        for (int i = 0; i < n-1; i++) {
            int k = i;
            for (int j = i + 1; j < n; j++) {
                if(arr[k] > arr[j]){
                    k = j;
                }
            }
            int tmp = arr[i];
            arr[i] = arr[k];
            arr[k] = tmp;
        }
    }
}
