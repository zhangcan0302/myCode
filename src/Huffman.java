import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.Map;

public class Huffman
{
    //region 赫夫曼节点
    /// <summary>
    /// 赫夫曼节点
    /// </summary>
    public class Node
    {
        /// <summary>
        /// 左孩子
        /// </summary>
        public Node left;

        /// <summary>
        /// 右孩子
        /// </summary>
        public Node right;

        /// <summary>
        /// 父节点
        /// </summary>
        public Node parent;

        /// <summary>
        /// 节点字符
        /// </summary>
        public char c;

        /// <summary>
        /// 节点权重
        /// </summary>
        public int weight;

        //赫夫曼“0"or“1"
        public char huffmancode;

        /// <summary>
        /// 标记是否为叶子节点
        /// </summary>
        public boolean isLeaf;
    }
    //endregion

    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.weight));

    /// <summary>
    /// 编码对应表（加速用）
    /// </summary>
    Map<Character, String> huffmanEncode = new Hashtable<>();

    /// <summary>
    /// 解码对应表（加速用）
    /// </summary>
    Map<String, Character> huffmanDecode = new Hashtable<>();

    /// <summary>
    /// 明文
    /// </summary>
    String word = "";

    public Node root = new Node();

    public Huffman(String str)
    {
        this.word = str;

        Map<Character, Integer> dic = new Hashtable<>();

        for (int i = 0; i<str.length(); i++)
        {
            char s = str.charAt(i);
            if (dic.containsKey(s))
                dic.replace(s, dic.get(s) + 1);
            else
                dic.put(s, 1);
        }

        for (char item : dic.keySet())
        {
            Node node = new Node();
            node.c = item;
            node.weight = dic.get(item);

            //入队
            queue.add(node);
        }
    }

    //region 构建赫夫曼树
    /// <summary>
    /// 构建赫夫曼树
    /// </summary>
    public void Build()
    {
        //构建
        while (queue.size() > 0)
        {
            //如果只有一个节点，则说明已经到根节点了
            if (queue.size() == 1)
            {
                root = queue.poll();

                break;
            }

            //节点1
            Node node1 = queue.poll();

            //节点2
            Node node2 = queue.poll();

            //标记左孩子
            node1.huffmancode = '0';

            //标记为右孩子
            node2.huffmancode = '1';

            //判断当前节点是否为叶子节点,hufuman无度为1点节点（方便计算huffman编码）
            if (node1.left == null)
                node1.isLeaf = true;

            if (node2.left == null)
                node2.isLeaf = true;

            //父节点
            root = new Node();

            root.left = node1;

            root.right = node2;

            root.weight = node1.weight + node2.weight;

            //当前节点为根节点
            node1.parent = node2.parent = root;

            //将当前节点的父节点入队列
            queue.add(root);
        }

        //深度优先统计各个字符的编码
        DFS(root);
    }
    //endregion

    //region 赫夫曼编码
    /// <summary>
    /// 赫夫曼编码
    /// </summary>
    /// <returns></returns>
    public String Encode()
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i<word.length(); i++)
        {
            char s = word.charAt(i);
            sb.append(huffmanEncode.get(s));
        }

        return sb.toString();
    }
    //endregion

    //region 赫夫曼解码
    /// <summary>
    /// 赫夫曼解码
    /// </summary>
    /// <returns></returns>
    public String Decode(String str)
    {
        StringBuilder decode = new StringBuilder();

        String temp = "";

        for (int i = 0; i < str.length(); i++)
        {
            temp += str.charAt(i);

            //如果包含 O(N)时间
            if (huffmanDecode.containsKey(temp))
            {
                decode.append(huffmanDecode.get(temp));

                temp = "";
            }
        }

        return decode.toString();
    }
    //endregion

    //region 深度优先遍历子节点，统计各个节点的赫夫曼编码
    /// <summary>
    /// 深度优先遍历子节点，统计各个节点的赫夫曼编码
    /// </summary>
    /// <returns></returns>
    public void DFS(Node node)
    {
        if (node == null)
            return;

        //遍历左子树
        DFS(node.left);

        //遍历右子树
        DFS(node.right);

        //如果当前叶节点
        if (node.isLeaf)
        {
            String code = "";

            Node temp = node;

            //回溯的找父亲节点的huffmancode LgN 的时间
            while (temp.parent != null)
            {
                //注意，这里最后形成的 “反过来的编码”
                code += temp.huffmancode;

                temp = temp.parent;
            }

            String codetemp = new StringBuilder(code).reverse().toString();

            huffmanEncode.put(node.c, codetemp);

            huffmanDecode.put(codetemp, node.c);
        }
    }
    //endregion

    public void printHuffmanEncode() {
        for (Map.Entry<Character, String> entry : huffmanEncode.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        for(int a = 0 ; a<300 ; a++){
            System.out.printf(a + "                ");
            System.out.println((char) a);
        }
    }

}
