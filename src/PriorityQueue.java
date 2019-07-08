import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PriorityQueue<T> {
    /// <summary>
    /// 定义一个数组来存放节点
    /// </summary>
    private List<HeapNode> nodeList = new ArrayList<>();

    //region 堆节点定义
    /// <summary>
    /// 堆节点定义
    /// </summary>
    public class HeapNode {
        /// <summary>
        /// 实体数据
        /// </summary>
        public T t;

        /// <summary>
        /// 优先级别 1-10个级别 (优先级别递增)
        /// </summary>
        public int level;

        public HeapNode(T t, int level) {
            this.t = t;
            this.level = level;
        }
    }
    //endregion

    //region  添加操作
    /// <summary>
    /// 添加操作
    /// </summary>
    public void Eequeue(T t, int level) {
        //将当前节点追加到堆尾
        nodeList.add(new HeapNode(t, level));

        //如果只有一个节点，则不需要进行筛操作
        if (nodeList.size() == 1)
            return;

        //获取最后一个非叶子节点
        int parent = nodeList.size() / 2 - 1;

        //堆调整
        UpHeapAdjust(nodeList, parent);
    }
    //endregion

    //region 对堆进行上滤操作，使得满足堆性质
    /// <summary>
    /// 对堆进行上滤操作，使得满足堆性质
    /// </summary>
    /// <param name="nodeList"></param>
    /// <param name="index">非叶子节点的之后指针（这里要注意：我们
    /// 的筛操作时针对非叶节点的）
    /// </param>
    public void UpHeapAdjust(List<HeapNode> nodeList, int parent) {
        while (parent >= 0) {
            //当前index节点的左孩子
            int left = 2 * parent + 1;

            //当前index节点的右孩子
            int right = left + 1;

            //parent子节点中最大的孩子节点，方便于parent进行比较
            //默认为left节点
            int max = left;

            //判断当前节点是否有右孩子
            if (right < nodeList.size()) {
                //判断parent要比较的最大子节点
                max = nodeList.get(left).level < nodeList.get(right).level ? right : left;
            }

            //如果parent节点小于它的某个子节点的话，此时筛操作
            if (nodeList.get(parent).level < nodeList.get(max).level) {
                //子节点和父节点进行交换操作
                HeapNode temp = nodeList.get(parent);
                nodeList.set(parent, nodeList.get(max));
                nodeList.set(max, temp);

                //继续进行更上一层的过滤
                parent = (int) Math.ceil(parent / 2d) - 1;
            } else {
                break;
            }
        }
    }
    //endregion

    //region 优先队列的出队操作
    /// <summary>
    /// 优先队列的出队操作
    /// </summary>
    /// <returns></returns>
    public HeapNode Dequeue() {
        if (nodeList.size() == 0)
            return null;

        //出队列操作，弹出数据头元素
        HeapNode pop = nodeList.get(0);

        //用尾元素填充头元素
        nodeList.set(0, nodeList.get(nodeList.size() - 1));

        //删除尾节点
        nodeList.remove(nodeList.size() - 1);

        //然后从根节点下滤堆
        DownHeapAdjust(nodeList, 0);

        return pop;
    }
    //endregion

    //region  对堆进行下滤操作，使得满足堆性质
    /// <summary>
    /// 对堆进行下滤操作，使得满足堆性质
    /// </summary>
    /// <param name="nodeList"></param>
    /// <param name="index">非叶子节点的之后指针（这里要注意：我们
    /// 的筛操作时针对非叶节点的）
    /// </param>
    public void DownHeapAdjust(List<HeapNode> nodeList, int parent) {
        while (2 * parent + 1 < nodeList.size()) {
            //当前index节点的左孩子
            int left = 2 * parent + 1;

            //当前index节点的右孩子
            int right = left + 1;

            //parent子节点中最大的孩子节点，方便于parent进行比较
            //默认为left节点
            int max = left;

            //判断当前节点是否有右孩子
            if (right < nodeList.size()) {
                //判断parent要比较的最大子节点
                max = nodeList.get(left).level < nodeList.get(right).level ? right : left;
            }

            //如果parent节点小于它的某个子节点的话，此时筛操作
            if (nodeList.get(parent).level < nodeList.get(max).level) {
                //子节点和父节点进行交换操作
                HeapNode temp = nodeList.get(parent);
                nodeList.set(parent, nodeList.get(max));
                nodeList.set(max, temp);

                //继续进行更下一层的过滤
                parent = max;
            } else {
                break;
            }
        }
    }
    //endregion

    //region 获取元素并下降到指定的level级别
    /// <summary>
    /// 获取元素并下降到指定的level级别
    /// </summary>
    /// <returns></returns>
    public HeapNode GetAndDownPriority(int level) {
        if (nodeList.size() == 0)
            return null;

        //获取头元素
        HeapNode pop = nodeList.get(0);

        //设置指定优先级（如果为 MinValue 则为 -- 操作）
        nodeList.get(0).level = level == Integer.MIN_VALUE ? --nodeList.get(0).level : level;

        //下滤堆
        DownHeapAdjust(nodeList, 0);

        return nodeList.get(0);
    }
    //endregion

    //region 获取元素并下降优先级
    /// <summary>
    /// 获取元素并下降优先级
    /// </summary>
    /// <returns></returns>
    public HeapNode GetAndDownPriority() {
        //下降一个优先级
        return GetAndDownPriority(Integer.MIN_VALUE);
    }
    //endregion

}

//region 定义一个实体
/// <summary>
/// 定义一个实体
/// </summary>
class Url {
    public String data;

    public Url(String data) {
        this.data = data;
    }

    public static void main(String[] args) throws InterruptedException {
        PriorityQueue<Url> heap = new PriorityQueue<>();
        //随机插入20个节点
        for (int i = 1; i < 20; i++)
        {
            int rand = new Random().nextInt(20);

            Thread.sleep(10L);

            heap.Eequeue(new Url("test" + i), i);
        }

        while (true)
        {
            PriorityQueue.HeapNode node = heap.Dequeue();

            if (node == null)
                break;

            System.out.println("当前url的优先级为:" + node.level + ",数据为:" + ((Url)node.t).data);
        }
    }
}
//endregion
