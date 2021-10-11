package com.example.demo.bean;

import io.swagger.models.auth.In;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

//class MyThread implements Runnable{
//    @Override
//    public void run() {
//        for (int i = 0;i<10;i++){
//            System.out.println(Thread.currentThread().getName()+" "+i);
//            Thread.yield();//
//        }
//    }
//}
//
//public class Test {
//    public static int[] bubbleSort(int[] array) {
//        for (int i = 1; i < array.length; i++) {
//            for (int j = 0; j < array.length - 1; j++) {
//                if (array[j] > array[j + 1]) {
//                    int temp = array[j];
//                    array[j] = array[j + 1];
//                    array[j + 1] = temp;
//                }
//            }
//        }
//        return array;
//    }
//
//    public static int[] selectionSort(int[] array) {
//        int minIndex;
//        //总共要经过n-1轮比较
//        for (int i = 0; i < array.length - 1; i++) {
//            minIndex = i;
//            for (int j = i + 1; j < array.length; j++) {
//                if (array[minIndex] > array[j]) {
//                    minIndex = j;
//                }
//            }
//            if (minIndex != i) {
//                int temp = array[i];
//                array[i] = array[minIndex];
//                array[minIndex] = temp;
//            }
//        }
//        return array;
//    }
//
//    public static int[] insertSort(int[] array) {
//        for (int i = 1; i < array.length; i++) {
//            int index = i;
//            for (int j = i - 1; j >= 0; j--) {
//                if (array[index] < array[j]) {
//                    int temp = array[index];
//                    array[index] = array[j];
//                    array[j] = temp;
//                    index--;
//                } else {
//                    break;
//                }
//            }
//        }
//        return array;
//    }
//
//    public static int[] mergeSort(int[] array) {
//        if (array.length < 2) {
//            return array;
//        }
//        int[] ints = Arrays.copyOf(array, array.length);
//        int part = (int) Math.floor(array.length / 2);
//
//        int[] left = Arrays.copyOfRange(ints, 0, part);
//        int[] right = Arrays.copyOfRange(ints, part, array.length);
//
//        return merge(mergeSort(left), mergeSort(right));
//    }
//
//    public static int[] merge(int[] left, int[] right) {
//        int[] result = new int[left.length + right.length];
//        int i = 0;
//        while (left.length > 0 && right.length > 0) {
//            if (left[0] <= right[0]) {
//                result[i++] = left[0];
//                left = Arrays.copyOfRange(left, 1, left.length);
//            } else {
//                result[i++] = right[0];
//                right = Arrays.copyOfRange(right, 1, right.length);
//            }
//        }
//
//        while (left.length > 0) {
//            result[i++] = left[0];
//            left = Arrays.copyOfRange(left, 1, left.length);
//        }
//
//        while (right.length > 0) {
//            result[i++] = right[0];
//            right = Arrays.copyOfRange(right, 1, right.length);
//        }
//
//        return result;
//    }
//
//    public static int[] quickSort(int[] array, int left, int right) {
//        if (left < right) {
//            int partitionIndex = partition(array, left, right);
//            quickSort(array, left, partitionIndex - 1);
//            quickSort(array, partitionIndex + 1, right);
//        }
//        return array;
//    }
//
//    public static int partition(int[] array, int left, int right) {
//        // 设定基准值（pivot）
//        int pivot = left;
//        int index = pivot + 1;
//        for (int i = index; i <= right; i++) {
//            if (array[i] < array[pivot]) {
//                int temp = array[i];
//                array[i] = array[index];
//                array[index] = temp;
//                index++;
//            }
//        }
//        int temp = array[pivot];
//        array[pivot] = array[index - 1];
//        array[index - 1] = temp;
//        return index - 1;
//    }
//
//    public static int[] heapSort(int[] array) {
//        int length = array.length;
//        //建立全部元素的大顶堆
//        maxHeapify(array, length);
//        for (int i = length - 1; i > 0; i--) {
//            //将最大的元素放到最后
//            int temp = array[i];
//            array[i] = array[0];
//            array[0] = temp;
//            //对长度-1的数组进行堆化
//            length--;
//            heapify(array, 0, length);
//        }
//        return array;
//    }
//
//    public static void maxHeapify(int[] array, int length) {
//        for (int i = (int) Math.floor(length / 2); i >= 0; i--) {
//            heapify(array, i, length);
//        }
//    }
//
//    public static void heapify(int[] array, int i, int length) {
//        int left = 2 * i + 1;
//        int right = 2 * i + 2;
//        int largest = i;
//        if (left < length && array[left] > array[largest]) {
//            largest = left;
//        }
//        if (right < length && array[right] > array[largest]) {
//            largest = right;
//        }
//        if (largest != i) {
//            int temp = array[i];
//            array[i] = array[largest];
//            array[largest] = temp;
//            heapify(array, largest, length);
//        }
//    }
//
//    public static int firstBadVersion(int n) {
//        if (n == 1) {
//            return 1;
//        }
//        int left = 1;
//        int right = n;
//        while(left<right){
//            int mid = (right - left)/2+left;
//            if (isBadVersion(mid)){
//                right = mid;
//            }else {
//                left = mid + 1;
//            }
//        }
//        return left;
//    }
//
//    public static boolean isBadVersion(int n) {
//        if (n >= 2) {
//            return true;
//        } else return false;
//    }
//
//    public static int searchInsert(int[] nums, int target) {
//        int left = 0;
//        int right = nums.length-1;
//        int index = 0;
//        while(left <= right){
//            int mid = (right-left)/2 + left;
//            if(nums[mid]==target){
//                return mid;
//            }else if (mid < target){
//                left = mid + 1;
//            }else{
//                index = mid + 1;
//                right = mid - 1;
//            }
//        }
//        return index;
//    }
//
//    public static int[] sortedSquares(int[] nums) {
//         int left = 0 ;
//         int right = nums.length - 1;
//         int[] array = new int[nums.length];
//         int i = nums.length - 1;
//         while(left<=right){
//             if((nums[left]*nums[left])>=(nums[right]*nums[right])){
//                 array[i] =nums[left]*nums[left];
//                 left ++;
//             }else if((nums[left]*nums[left])<(nums[right]*nums[right])){
//                 array[i] =nums[right]*nums[right];
//                 right--;
//             }
//             i--;
//         }
//         return array;
//    }
//
//    public static void moveZeroes(int[] nums) {
//        int left = 0;
//        int right =0;
//        int i = 0;
//        int ref = 0;
//        while(i<nums.length){
//            if(nums[i] == 0 && ref == 0){
//                left = i;
//                ref = 1;
//            }else if(nums[i] == 0 && ref != 0){
//
//            }else{
//                right = i;
//                ref = 0;
//            }
//            if(left < right && i!=0){
//                int temp = nums[left];
//                nums[left] = nums[right];
//                nums[right] = temp;
//                if (nums[left+1] == 0){
//                    left++;
//                    ref=1;
//                }
//                else {
//                    left = right;
//                }
//            }
//            i++;
//        }
//    }
//
//    public static void main(String[] args) {
//
//    }
//
//}