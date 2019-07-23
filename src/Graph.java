import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Graph{
    private int v;//顶点数量
    private int e;//边数量
    private boolean isWeight;  //时候是带权重的图
    private boolean isDirect;  //是否是有向图
    private boolean hasCycle;  //图中时候含有环
    private LinkedList<Edge>[] adj;//临接表

    //图中边的表示
    public static class Edge implements Comparable<Edge>{
        private final int from;//边起始顶点
        private final int to;//边终结顶点
        private final double w; //权值
        public Edge(int from, int to, double w){
            this.from = from;
            this.to = to;
            this.w = w;
        }

        //返回任意一个顶点
        public int either(){
            return from;
        }

        //返回另一个顶点
        public int other(int v){
            return v == this.from ? to : from;
        }

        //用于有向图
        public int from(){
            return from;
        }

        //用于有向图
        public int to(){
            return to;
        }

        public double weight(){
            return w;
        }

        //边比较器，已权重为依据
        @Override
        public int compareTo(Edge that) {
            if(this.w > that.w){
                return 1;
            }else
            if(this.w < that.w){
                return -1;
            }else{
                return 0;
            }
        }

        //边的显示方法
        @Override
        public String toString(){
            return new String(String.format("%-2d", from) + "  "
                    + String.format("%-2d", to) + "  "
                    + String.format("%-4.2f", w));
        }
    }

//	public static class Cmp implements Comparator<Edge>{
//		@Override
//		public int compare(Edge e1, Edge e2) {
//			return e1.compareTo(e2);
//		}
//	}

    //从文件流中读入图的txt文件来构造图
    @SuppressWarnings("unchecked")
    public Graph(Reader r, boolean isDirect, boolean isWeight){
        BufferedReader br = new BufferedReader(r);
        Scanner scn = new Scanner(br);
        v = scn.nextInt();
        e = scn.nextInt();
        this.isWeight = isWeight;
        this.isDirect = isDirect;

        adj = (LinkedList<Edge>[])new LinkedList[v];

        for(int i = 0; i < v; i++){
            adj[i] = new LinkedList<Edge>();
        }

        for(int i = 0; i < e; i++){
            int from = scn.nextInt();
            int to = scn.nextInt();
            double w;
            if(isWeight){
                w = scn.nextDouble();
            }else{//如果不是带权重的图，默认权重是1
                w = 1;
            }
            Edge e = new Edge(from, to, w);
            adj[from].add(e);
            if(!isDirect){
                adj[to].add(e);
            }
        }
        scn.close();
    }

    //当前图的反向图构造函数
    @SuppressWarnings("unchecked")
    private Graph(Graph g){
        v = g.V();
        e = g.E();
        this.isWeight = g.isWeight;
        this.isDirect = g.isDirect;
        hasCycle = g.hasCycle;

        adj = (LinkedList<Edge>[]) new LinkedList[v];
        for(int i = 0; i < v; i++){
            adj[i] = new LinkedList<Edge>();
        }

        for(int from = 0; from < v; from++){
            for(Edge e : g.adj[from]){
                int to = e.other(from);
                double 	w = e.weight();
                adj[to].add(new Edge(to, from, w));
            }
        }
    }

    //返回当前图的反向图
    public Graph reverse(){
        if(this.isDirect){
            return new Graph(this);
        }else{
            throw new IllegalArgumentException("Graph is not Directed");
        }
    }

    //通过添加边来构造图的构造函数
    @SuppressWarnings("unchecked")
    public Graph(int v, boolean isDirect, boolean isWeight){
        adj = (LinkedList<Edge>[])new LinkedList[v];
        for(int i = 0; i < v; i++){
            adj[i] = new LinkedList<Edge>();
        }
        this.isDirect = isDirect;
        this.isWeight = isWeight;
        this.v = v;
    }

    //添加一条边
    public void addEdge(Edge e){
        adj[e.from].add(e);
        this.e++;
        if(!isDirect){
            this.e++;
            adj[e.to()].add(e);
        }
    }

    //返回图中顶点个数
    public int V(){
        return v;
    }

    //返回图中边的数量
    public int E(){
        return e;
    }

    //邻接顶点，返回与顶点v相邻的所有顶点的编号
    public List<Integer> adjVertex(int v){
        ArrayList<Integer> list = new ArrayList<Integer>(adj[v].size());
        for(Edge e : adj[v]){
            list.add(e.other(v));
        }
        return list;
    }

    //返回与顶点v相邻的边，对于位于同一包中的类，这个方法效率更高
    public List<Edge> adjEdge(int v){
        return adj[v];
    }

    //返回一条边
    public Edge getEdge(int from, int to){
        for(Edge e : adj[from]){
            if(e.other(from) == to){
                return e;
            }
        }
        return null;
    }

    //是否是有向图
    public boolean isDirect(){
        return isDirect;
    }

    //是否是带权重的图
    public boolean isWeight(){
        return isWeight;
    }

    //是否是有向无有环图
    public boolean isDAG(){
        if(!isDirect){
            return false;
        }

        boolean[] marked = new boolean[v];
        boolean[] onStack = new boolean[v];

        for(int i = 0; i < v; i++){
            if(!marked[i]){
                dfs(i, marked, onStack);
            }
        }
        return !hasCycle;
    }

    //用于判断DAG的深度优先遍历
    private void dfs(int v, boolean[] marked, boolean[] onStack){
        if(hasCycle){
            return;
        }

        marked[v] = true;
        onStack[v] = true;
        for(Edge e : adj[v]){
            int w = e.other(v);
            if(!marked[w]){
                dfs(w, marked, onStack);
            }else
            if(onStack[w]){
                hasCycle = true;
                return;
            }
        }
        onStack[v] = false;
    }

    //图的显示方法
    public String toString(){
        StringWriter sw = new StringWriter(5*v + 10*e);//长度不是一个准确值，是尽量往大估计的
        PrintWriter pw = new PrintWriter(sw);
        for(int i = 0; i < v; i++){
            pw.printf("%-3d: ", i);
            for(Edge e : adj[i]){
                if(isWeight){
                    pw.printf("[%-3d, %-4.2f]  ", e.other(i), e.w);
                }else{
                    pw.printf("%-3d ", e.other(i));
                }
            }
            pw.println();
        }
        return sw.getBuffer().toString();
    }

//是否存在从from到to的边
//	public boolean hasEdge(int from, int to){
//		boolean[] marked = new boolean[v];
//		hasEdge0(from, to, marked);
//		return marked[to];
//	}
//
//	private void hasEdge0(int from, int to, boolean[] marked){
//		if(!marked[from]){
//			marked[from] = true;
//			for(Edge e : adj[from]){
//				if(!marked[to]){
//					hasEdge0(e.other(from), to, marked);
//				}else{
//					return;
//				}
//			}
//		}
//	}

    //从from节点开始逆后序遍历,返回逆后序的栈
    public LinkedList<Integer> reversePostOrder(int from){
        LinkedList<Integer> stack = new LinkedList<Integer>();
        boolean[] marked = new boolean[v];
        for(int i = 0; i < v; i++){
            reversePostOrderTar(i, stack, marked);
        }
        return stack;
    }

    //用于逆后序的深度优先遍历
    private void reversePostOrderTar(int from, LinkedList<Integer> stack, boolean[] marked){
        if(!marked[from]){
            marked[from] = true;
            for(Edge e : adj[from]){
                reversePostOrderTar(e.other(from), stack, marked);
            }
            stack.push(from);
        }
    }

    public static void main(String[] args) throws FileNotFoundException{
        File path = new File(System.getProperty("user.dir")).getParentFile();
        File f = new File(path, "algs4-data/tinyDG.txt");
        FileReader fr = new FileReader(f);
        Graph g = new Graph(fr, true, false);
        System.out.println(g.toString());
        System.out.println(g.reverse().toString());
//		System.out.println(g.hasEdge(0, 7));
    }

}