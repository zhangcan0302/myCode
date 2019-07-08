import java.util.Arrays;

public class BinaryIndexTree {

    static int[] sumArray = new int[8];

    static int[] arr = new int[8];

    public static void main(String[] args) {
        Init();

        System.out.println("A数组的值:" + Arrays.toString(arr));
        System.out.println("S数组的值:" + Arrays.toString(sumArray));

        System.out.println("修改A[1]的值为3");
        Modify(1, 3);

        System.out.println("A数组的值:" + Arrays.toString(arr));
        System.out.println("S数组的值:" + Arrays.toString(sumArray));
    }

    //region 初始化两个数组
    /// <summary>
    /// 初始化两个数组
    /// </summary>
    public static void Init()
    {
        for (int i = 1; i <= 8; i++)
        {
            arr[i - 1] = i;

            //设置其实坐标：i=1开始
            int start = (i - Lowbit(i));

            int sum = 0;

            while (start < i)
            {
                sum += arr[start];

                start++;
            }

            sumArray[i - 1] = sum;
        }
    }
    //endregion


    public static void Modify(int x, int newValue)
    {
        //拿出原数组的值
        int oldValue = arr[x];

        arr[x] = newValue;

        for (int i = x; i < arr.length; i += Lowbit(i + 1))
        {
            //减去老值，换一个新值
            sumArray[i] = sumArray[i] - oldValue + newValue;
        }
    }

    //region 求前n项和
    /// <summary>
    /// 求前n项和
    /// </summary>
    /// <param name="x"></param>
    /// <returns></returns>
    public static int Sum(int x)
    {
        int ans = 0;

        int i = x;

        while (i > 0)
        {
            ans += sumArray[i - 1];

            //当前项的最大子树
            i -= Lowbit(i);
        }

        return ans;
    }
    //endregion

    //region 当前的sum数列的起始下标
    /// <summary>
    /// 当前的sum数列的起始下标
    /// </summary>
    /// <param name="i"></param>
    /// <returns></returns>
    public static int Lowbit(int i)
    {
        return i & -i;
    }
    //endregion
}
