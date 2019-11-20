public class UglyNum {
    private static int[] arr = new int[2000];
    private static int current = 0;
    private static int a2 = 0, a3 =0, a5 =0;
    public static void main(String[] args) {
        int n = 1500;
        arr[0] = 1;
        while (n!=0) {
            nextUgly();
            n--;
        }
        for (int i = 10; i < 1500; i += 10)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    public static void nextUgly() {
        if (arr[a2] * 2 <= arr[current]) {
            a2++;
        }
        if (arr[a3] * 3 <= arr[current]) {
            a3++;
        }
        if (arr[a5] * 5 <= arr[current]) {
            a5++;
        }
        if (arr[a2] * 2 <= arr[a5] * 5) {
            arr[++current] = arr[a2] * 2 <= arr[a3] * 3 ? arr[a2] * 2 : arr[a3] * 3;
        } else {
            arr[++current] = arr[a5] * 5 <= arr[a3] * 3 ? arr[a5] * 5 : arr[a3] * 3;
        }
    }
}
