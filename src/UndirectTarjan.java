import java.util.Stack;

public class UndirectTarjan {
    private static int n = 8;
    private static int a[][] = {{0,1,0,1,0,0,0,0},
                                {0,0,1,0,1,0,0,0},
                                {0,0,0,0,0,1,0,0},
                                {0,0,0,0,1,0,0,0},
                                {1,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,1,0},
                                {0,0,1,0,0,1,0,0},
                                {0,0,0,0,0,0,0,0}};
    private static int[] dfn = new int[n+1];
    private static int[] low = new int[n+1];
    private static int isStack[] = {0,0,0,0,0,0,0,0};
    private static int visit[] = {0,0,0,0,0,0,0,0};
    private static int index = 1;

    public static void main(String[] args) {
        for (int i = 1; i <= n; i++) {
            if (visit[i-1] ==1) continue;
            index = 1;
            tarjan(i);
        }
    }

    private static void tarjan(int i) {
        visit[i-1] = isStack[i-1] = 1;
        dfn[i-1] = low[i-1] = index++;
        for (int j = 1;j<=n;j++) {
            if (a[i-1][j-1] == 1 && visit[j-1] == 0) {
                tarjan(j);
                low[i-1] = low[i-1]<low[j-1]?low[i-1]:low[j-1];
            } else if (a[i-1][j-1] == 1 && visit[j-1] == 1) {
                if (isStack[j-1] ==1) {
                    low[i-1] = low[i-1]<low[j-1]?low[i-1]:low[j-1];
                }
            }
        }
        if (low[i-1] == dfn[i-1]) {
            for (int j=1;j<=n;j++) {
                if (low[j-1] == low[i-1] && isStack[j-1] == 1) {
                    System.out.print(j + " ");
                    isStack[j-1] = 0;
                }
            }
            System.out.println();
        }

    }

}
