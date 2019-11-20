public class ContinuousPositive {
    public static void main(String[] args) {
        int n =27;
        for (int i = 2; i<=n; i++) {
            if (2*n%i == 0) {
                int m = (2*n/i +1 - i) / 2;
                if (m <= 0) continue;
                for (int j = 0; j < i; j++) {
                    System.out.print(m + j + " ");
                }
                System.out.println();
            }

        }
    }
}
