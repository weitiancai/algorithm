package sort;

import org.junit.Test;

import java.util.Arrays;

public class QuickSort {

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
        QuickSort.myQuickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }


    public static void myQuickSort(int[] arr, int left, int right){
        if(left>=right)return;

        int n = arr.length;
        int i = left,j = right;
        int pivot = arr[right];


        while(i<j){
            while(i < j && arr[i] < pivot){
                i++;
            }
            if(i<j) {
                arr[j--] = arr[i];
            }
            while(i < j && arr[j] > pivot){
                j--;
            }
            if(i<j) {
                arr[i++] = arr[j];
            }
        }


        myQuickSort( arr, 0, i-1);
        myQuickSort( arr, i+1,right);
    }



    @Test
    public  void quickS() {
        int[] array = {6,5,5,6,6,7};
        int left = 0, right = array.length-1;
        myquickSort(array,left,right);
        for (int num : array) {
            System.out.println(num+" ");
        }
    }

    private static void myquickSort(int[] array, int left, int right) {
        if(left < right){
            int pivotIndex = partition(array,left,right);
            myquickSort(array, left, pivotIndex - 1);
            myquickSort(array, pivotIndex+1, right);
        }
    }

    public static int partition(int[] array, int low,int high){
        int pivot = array[low];
        while(low < high){
            while(low < high && array[high] >= pivot){
                high--;
            }
            array[low] = array[high];
            while(low < high && array[low] <= pivot){
                low++;
            }
            array[high] = array[low];
        }
        array[low] = pivot;
        return low;
    }

}