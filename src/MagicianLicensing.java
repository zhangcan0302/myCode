import java.util.Arrays;

public class MagicianLicensing {
    public static void main(String[] args) {
        int[] a = new int[13];
        for (int i = 0; i < 13; i++) a[i] = 0;
        int m = 0;
        for (int k = 1; k <= 13; k++) {
            int n = k;
            while(n != 0) {
                if (a[m] == 0) {
                    n--;
                }
                m = (m + 1) % 13;
            }
            a[(m+12)%13] = k;
        }
        System.out.println(Arrays.toString(a));
    }
}
