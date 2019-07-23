public class DirectTarjan {
    private static int n = 8;
    private static int a[][] = {{0,1,0,1,0,0,0,0},
                                {0,0,1,0,1,0,0,0},
                                {0,0,0,0,0,1,0,0},
                                {0,0,0,0,1,0,0,0},
                                {0,0,0,0,0,1,0,0},
                                {0,0,0,0,0,0,1,0},
                                {0,0,0,0,0,1,0,0},
                                {1,0,0,0,0,0,0,0}};
    private static int[] dfn = new int[n+1];
    private static int[] low = new int[n+1];
    private static int visit[] = {0,0,0,0,0,0,0,0};
    private static int index = 1;
    public static void main(String[] args) {
        for (int i = 1; i <= n; i++) {
            if (visit[i-1] ==1) continue;
            index = 1;
            tarjan(i,0,1);
        }
    }

    private static void tarjan(int i,int child,int father) {
        visit[i-1] = 1;
        dfn[i-1] = low[i-1] = index++;
        int k = low[i-1];
        for (int j = 1;j<=n;j++) {
            if ((a[j-1][i-1] == 0 && a[i-1][j-1] == 0) || j == father) continue;
            if (visit[j-1] == 1) {
                k = k<low[j-1]?k:low[j-1];
            } else {
              child++;
              tarjan(j,1, i);
              int l = low[i-1]<low[j-1]?low[i-1]:low[j-1];
              k = k<l?k:l;
              if (child >1 && l >= dfn[i-1]) {
                  System.out.println(i);
                  if (low[j-1]>l) {
                      System.out.println(i + "--" + j);
                  }
              }
            }
        }
        low[i-1] = k;


    }
}
