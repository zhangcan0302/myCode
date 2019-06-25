
public class DualPivotQuickSort {

    public static void main(String[] args) {
        int[] a= {1,2,5,4,1,3,8,4,1,2,4,5,4,1,5,4,1,41,54,124,33,1,4,5,7,8,9,9,2,4,5,6,4,9,54,7,8};
        sort(a,0,a.length-1);
        for (int i=0; i<a.length-1;i++) {
            System.out.print(a[i] + "->");
        }
        System.out.print(a[a.length-1]);

    }

    public static void sort(int[] a,int start, int end) {
        if (start >= end){
            return;
        }
        int i= start;
        int k= start+1;
        int j= end;
        if (a[end]<a[start]){
            swap(a, start, end);
        }
        int point1 = a[start];
        int point2 = a[end];
        while (k<j) {
            if (a[k] < point1) {
                swap(a, ++i, k++);
            } else if(a[k] <= point2) {
                k++;
            } else {
                for (;j>k;--j){
                    if (a[j]<point2) {
                        break;
                    }
                }
                swap(a,k,j);
            }
        }
        swap(a,i,start);
        swap(a,k,end);
        sort(a,start,i-1);
        sort(a,i+1, k-1);
        sort(a,k+1, end);
    }

    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
