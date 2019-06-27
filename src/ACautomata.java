import java.util.HashSet;

public class ACautomata {

    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.AddTrieNode("say", 1);
        trie.AddTrieNode("she", 2);
        trie.AddTrieNode("shr", 3);
        trie.AddTrieNode("her", 4);
        trie.AddTrieNode("he", 5);

        trie.BuildFailNodeBFS();

        String s = "yasherhs";

        HashSet<Integer> hashSet = trie.SearchAC(s);

        System.out.println("在主串"+ s +"中存在模式串的编号为:" + hashSet.toString());
    }




}
