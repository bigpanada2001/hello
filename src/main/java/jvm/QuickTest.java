package jvm;

import java.util.Arrays;

public class QuickTest {
	public static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	public static void popsort(int[] a) {
		for(int i=0;i<a.length-1;i++) {
			for(int j=0;j<a.length-1-i;j++) {
				if(a[j] > a[j+1]) {
					swap(a, j , j+1);
				}
			}
		}
	}
	
	public static void selectsort(int[] a) {
		for(int i=0;i<a.length-1;i++) {
			int k = i;
			for(int j=k+1;j<a.length;j++) {
				if(a[j] < a[k]) {
					k=j;
				}
			}
			if(i != k) {
				swap(a, i, k);
			}
		}
	}
	public static void quicksort(int[] a,int low,int height){
        int i=low;
        int j=height;
        if (i>j) {    //放在k之前，防止下标越界
            return;
        }
        int k=a[i];
        while (i<j) {   
            while (i<j&&a[j]>k) {    //找出小的数
                j--;
            }
            while (i<j&&a[i]<=k) {  //找出大的数
                i++;
            }
            if(i<j){   //交换
                int swap=a[i];
                a[i]=a[j];
                a[j]=swap;
            }
            
        }
        //交换K
        swap(a,i,low);
        //对左边进行排序,递归算法
        quicksort(a, 0, i-1);
        //对右边进行排序
        quicksort(a,i+1,height);
    }
    public static void quickSort_2(int[] data, int start, int end) {
        if (data == null || start >= end) return;
        int i = start, j = end;
        int pivotKey = data[start];
        while (i < j) {
            while (i < j && data[j] >= pivotKey) j--;
            if (i < j) data[i++] = data[j];
            while (i < j && data[i] <= pivotKey) i++;
            if (i < j) data[j--] = data[i];
        }
        data[i] = pivotKey;
        quickSort_2(data, start, i - 1);
        quickSort_2(data, i + 1, end);
    }

	public static void main(String[] args) {
		int[] a = {4,7,1,3,2,9,8,5,6,0}; 
		QuickTest.quickSort_2(a, 0, 9);
		System.out.println(Arrays.toString(a));
	}

}
