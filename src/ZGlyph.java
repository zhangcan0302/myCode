import java.util.Arrays;

public class ZGlyph {
    public static void main(String[] args) {
        int n = 7;
        int[][] a = new int[n][n];
        int k = 1;
        int i = 0, j = 0;
        while (k <= n * n) {
            if ((i+j)%2 == 0) {
                a[i][j] = k++;
                if (i % 2 == (n+1) % 2 && j == n-1){
                    i++;
                } else {
                    if (i > 0) {
                        i--;
                    }
                    if (j < n-1) {
                        j++;
                    }
                }
            } else {
                a[i][j] = k++;
                if (i == n-1 && j % 2 == n % 2) {
                    j++;
                } else {
                    if(i < n-1) {
                        i++;
                    }
                    if (j > 0) {
                        j--;
                    }
                }
            }
        }
        for (int m = 0; m < n; m++) {
            System.out.println(Arrays.toString(a[m]));
        }
    }
}
