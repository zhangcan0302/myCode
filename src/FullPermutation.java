import java.util.ArrayList;
import java.util.List;

public class FullPermutation {

    public static void main(String[] args) {
        char[] a = {'a', 'b', 'c'};
//        permutation(a,0, a.length - 1);
        List<Character> re = new ArrayList<>();
        combination(a, 3, 0, re);
    }

    public static void permutation(char[] s, int begin ,int end) {
        if (begin == end) {
            System.out.println(s);
        }

        for (int k = begin ;k<=end;k++) {
            swap(s, begin, k);
            permutation(s, begin+1, end);
            swap(s, begin, k);
        }
    }

    public static void swap(char[] s, int a ,int b) {
        char temp = s[a];
        s[a] = s[b];
        s[b]= temp;
    }

    public static void combination(char[] s, int a, int k ,List<Character> re) {
        if (a==0) {
            System.out.println(re);
            return;
        }

        if (k >= s.length) return;
        re.add(s[k]);
        combination(s, a-1, k+1, re);
        re.remove(re.size()-1);
        combination(s, a, k+1, re);
    }
}
