public class MaxShares {
    private static int NUM = 5;

    private static int testShares(int a[], int n, int m, int sum, int groupsum, int aux[], int goal, int groupId) {
        if (goal < 0)
            return 0;

        if (goal == 0)
        {
            groupId++;
            goal = groupsum;

            if (groupId == m+1)
                return 1;
        }

        for (int i = 0; i < n; i++)
        {
            if (aux[i] != 0)
                continue;

            aux[i] = groupId;
            if (testShares(a, n, m, sum, groupsum, aux, goal-a[i], groupId) == 1)
                return 1;

            aux[i] = 0;				//a[i]分配失败，将其置为未分配状态
        }

        return 0;
    }

    private static int maxShares(int a[], int n) {
        int sum = 0;
	    int[] aux = new int[n+1];

        for (int i = 0; i < n; i++)
            sum += a[i];

        for (int m = n; m >= 2; m--)
        {
            if (sum%m != 0)
                continue;

            for (int i = 0; i < n; i++)
                aux[i] = 0;

            if (testShares(a, n, m, sum, sum/m, aux, sum/m, 1) == 1)
            {
                //打印分组情况
                System.out.println("\n分组情况：");
                for (int j = 1; j <= m; j++) {
                    for (int i = 0; i < NUM; i++) {
                        if (aux[i] == j) {
                            System.out.print(a[i] + " ");
                        }
                    }
                    System.out.println();
                }


                aux = null;
                return m;
            }
        }

        aux = null;
        return 1;
    }

    public static void main(String[] args) {
        int a[] = {3, 2, 4, 3, 6};

        //打印数组值
        System.out.println("数组的值：");
        for (int i = 0; i < NUM; i++)
            System.out.print(a[i] + " ");
        System.out.println();

        System.out.println("可以分配的最大组数为：" + maxShares(a, NUM));

        System.out.println("pause");
    }
}
