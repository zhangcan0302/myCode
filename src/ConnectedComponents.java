import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class ConnectedComponents {
    private boolean[] marked;
    /*用于标记每个顶点属于哪一个（强）连通分量
                 同一（强）连通分量顶点的（强）连通分量编号值相同*/
    private int[] id;
    private int count;//（强）连通分量的编号，也表示（强）连通分量的个数
    private Graph g;

    public ConnectedComponents(Graph g){
        this.g = g;
        marked = new boolean[g.V()];
        id = new int[g.V()];

        if(g.isDirect()){//有向图求强连通分量的方法
            //反向图DFS的逆后序,从0号顶点开始,可以从任意顶点开始
            LinkedList<Integer> stack = g.reverse().reversePostOrder(0);
            marked = new boolean[g.V()];
            while(!stack.isEmpty()){
                int v = stack.pop();
                if(!marked[v]){
                    dfs(v);
                    count++;
                }
            }
        }else{//无向图的连通分量
            for(int i = 0; i < g.V(); i++){
                if(!marked[i]){
                    dfs(i);
                    count++;
                }
            }
        }
    }

    private void dfs(int v){
        if(!marked[v]){
            marked[v] = true;
            id[v] = count;
            for(Graph.Edge e : g.adjEdge(v)){
                int w = e.other(v);
                dfs(w);
            }
        }
    }


    public int count(){
        return count;
    }

    //与顶点v属于同一连通分量的所有顶点
    public List<Integer> allConnected(int v){
        LinkedList<Integer> list = new LinkedList<Integer>();
        int k = id[v];
        for(int i = 0; i < g.V(); i++){
            if(id[i] == k){
                list.add(i);
            }
        }
        return list;
    }

    public static void main(String[] args) throws FileNotFoundException{
        File path = new File(System.getProperty("user.dir")).getParentFile();
        File f = new File(path,"algs4-data/tinyDG2.txt");
        FileReader fr = new FileReader(f);
        Graph graph = new Graph(fr, true, false);

        System.out.println("图的表示");
        System.out.println(graph);

        ConnectedComponents cc = new ConnectedComponents(graph);

        System.out.println("连通分量数：  " + cc.count());
        System.out.println("\n");

        System.out.println("和顶点 0 共属于同一个连通分量的顶点");
        for(int i : cc.allConnected(0)){
            System.out.printf("%-3d", i);
        }
        System.out.println("\n");

        System.out.println("和顶点 3 共属于同一个连通分量的顶点");
        for(int i : cc.allConnected(3)){
            System.out.printf("%-3d", i);
        }
        System.out.println("\n");

        System.out.println("和顶点 9 共属于同一个连通分量的顶点");
        for(int i : cc.allConnected(9)){
            System.out.printf("%-3d", i);
        }
        System.out.println("\n");

        System.out.println("和顶点 6 共属于同一个连通分量的顶点");
        for(int i : cc.allConnected(6)){
            System.out.printf("%-3d", i);
        }
        System.out.println();
    }
}