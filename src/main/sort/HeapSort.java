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
        arr = heapSort(arr, arr.length);
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
}
