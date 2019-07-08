public class Bitmap {
    static byte n = 15;

    static byte[] a;

    public static void main(String[] args) {
        //节省空间的做法
        a = new byte[(n >> 3) + 1];

        for (byte i = 0; i < n; i++)
            Clear(i);

        Add((byte) 4);
        Add((byte) 14);
        System.out.println("插入4成功！");

        boolean s = Contain((byte) 4);

        System.out.println("当前是否包含4:" + s);

        s = Contain((byte) 5);

        System.out.println("当前是否包含5:" + s);

        s = Contain((byte) 14);

        System.out.println("当前是否包含14:" + s);

    }

    //region 初始化所用的bit位为0
    /// <summary>
    /// 初始化所用的bit位为0
    /// </summary>
    /// <param name="i"></param>
    static void Clear(byte i)
    {
        //相当于 i%8 的功能
        int shift = i & 0x07;

        //计算应该放数组的下标
        int arrindex = i >> 3;

        //则将当前byte中的指定bit位取0，&后其他对方数组bit位必然不变，这就是 1 的妙用
        int bitPos = ~(1 << shift);

        //将数组中的指定bit位置一  “& 操作”
        a[arrindex] &= (byte)(bitPos);
    }
    //endregion

    //region 设置相应bit位上为1
    /// <summary>
    /// 设置相应bit位上为1
    /// </summary>
    /// <param name="i"></param>
    static void Add(byte i)
    {
        //相当于 i%8 的功能
        int shift = i & 0x07;

        //计算应该放数组的下标
        int arrindex = i >> 3;

        //将byte中的 1 移动到i位
        int bitPos = 1 << shift;

        //将数组中的指定bit位置一  “| 操作”
        a[arrindex] |= (byte)bitPos;
    }
    //endregion

    //region 判断当前的x在数组的位中是否存在
    /// <summary>
    ///判断当前的x在数组的位中是否存在
    /// </summary>
    /// <param name="i"></param>
    /// <returns></returns>
    static boolean Contain(byte i)
    {
        int j = a[i >> 3] & (1 << (i & 0x07));

        if (j == 0)
            return false;
        return true;
    }
    //endregion
}
