public class MaxLength {
    public static void main(String[] args) {
        int a[] = {9, 4, 3, 2, 5, 4, 3, 2};
        int b[] = new int[a.length];
        int c[] = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = 1;
            c[i] = i;
        }
        int k = a.length - 1;
        int max = 1;
        for (int i = a.length - 2; i >= 0; i--) {
            for (int j = i+1; j < a.length; j++) {
                if (a[i] > a[j] && b[i] < b[j] + 1) {
                    c[i] = j;
                    b[i] = b[j] + 1;
                    if (b[i] > max) {
                        max = b[i];
                        k = i;
                    }
                }
            }
        }
        while(k != c[k]) {
            System.out.printf(a[k] + " ");
            k = c[k];
        }
        System.out.println(a[k]);
    }
}
