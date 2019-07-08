import java.io.*;

public class HuffmanCoding {

    public static void main(String[] args) throws IOException {
        double startTime = System.currentTimeMillis() ;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 100; i++)
        {
            sb.append("人民网北京12月8日");
        }

        writeFile("d://1.txt", sb.toString());
        Huffman huffman = new Huffman(sb.toString());

        huffman.Build();
        huffman.printHuffmanEncode();

        System.out.println("构建赫夫曼树耗费成功");

        //将8位二进制转化为ascII码
        String s = huffman.Encode();

        String result= "";

        int start = 0;

        for (int i = 8; i < s.length(); i = i + 8)
        {
            result += BinstrToChar(s.substring(i - 8, i));

            start = i;
        }

        //当字符编码不足8位时， 用‘艹'来标记，然后拿出’擦‘以后的所有0,1即可
        result += "艹" + s.substring(start);

        writeFile("d://2.txt", result);
        System.out.println("压缩完毕！");

        //解码
        String str = readFile("d://2.txt");
        StringBuilder sf = new StringBuilder();
        for (int i = 0; i < str.length(); i++)
        {
            char ua = str.charAt(i);

            //说明已经取完毕了  用'艹'来做标记
            if (ua == '艹') {
                sf.append(str.substring(i+1));
                break;
            }
            else {
                sf.append(addZeroForNum(Integer.toBinaryString(ua - 33),8));
            }
        }

        String sss = huffman.Decode(sf.toString());
        System.out.println(sss.length());
        System.out.println(str.length());
        System.out.println(sss.equals(sb.toString()));
        double endTime = System.currentTimeMillis() ;
        System.out.println("time is : " + (endTime - startTime)/1000 + "s");

    }

    public static int compare(String a, String b) {
        int k = 0;
        while (k < b.length() && k < a.length() && a.charAt(k) == b.charAt(k)) k++;
        return k;
    }

    public static char  BinstrToChar(String binStr) {
        int[] temp=BinstrToIntArray(binStr);
        int sum=0;
        for(int i=0; i<temp.length;i++){
            sum +=temp[temp.length-1-i]<<i;
        }
        return (char)(sum + 33);
    }

    public static int[] BinstrToIntArray(String binStr) {
        char[] temp=binStr.toCharArray();
        int[] result=new int[temp.length];
        for(int i=0;i<temp.length;i++) {
            result[i]=temp[i]-48;
        }
        return result;
    }

    public static String stringToBinary(String str){
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i]);
        }
        return result;
    }

    public static void writeFile(String fileName, String content) {
        try {
            File writeName = new File(fileName); // 相对路径，如果没有则要建立一个新的output.txt文件
            if (writeName.exists()) {
                writeName.delete();
            }
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-16")));
            out.write(content);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String fileName) {
        String result = "";
        try {
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-16"));
            String line;
            while((line=br.readLine())!=null){
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);// 左补0
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }

        return str;
    }
}


