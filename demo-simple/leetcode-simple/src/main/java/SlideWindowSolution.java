import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lirong
 * @createTime 2020年11月12日 16:58
 *
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 *
 * 注意：如果 s 中存在这样的子串，我们保证它是唯一的答案。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：s = "ADOBECODEBANC", t = "ABC"
 * 输出："BANC"
 * 示例 2：
 *
 * 输入：s = "a", t = "a"
 * 输出："a"
 *  
 *
 * 提示：
 *
 * 1 <= s.length, t.length <= 105
 * s 和 t 由英文字母组成
 *  
 *
 * 进阶：你能设计一个在 o(n) 时间内解决此问题的算法吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-window-substring
 */
public class SlideWindowSolution {

    public static void main(String[] args) {
        String S = "ADOBECODEBANC", T = "ABC";
        minWindowBest(S, T);
    }

    /** 公式 */
    void slide(){
        List<String> s = new ArrayList<>();
        List<String> window = Lists.newArrayList();
        int left = 0;
        int right = 0;
        while (right < s.size()){
            // 增大窗口
            window.add(s.get(right));
            right ++;

            while (true){
            //while (window needs shrink){
                // 缩小窗口
                window.remove(s.get(left));
                left++;
            }
        }
    }

    /** 实现 */
    public String minWindowBetter(String sourse, String target) {
        int[] window = new int[128], need = new int[128];
        char[] ss = sourse.toCharArray(), tt = target.toCharArray();
        int count = 0, min = ss.length;
        String res = "";
        for (int i = 0; i < tt.length; i++) {
            need[tt[i]]++;
        }
        int i = 0, j = 0;
        while(j < ss.length) {
            char c = ss[j];
            window[c]++;
            if (window[c] <= need[c]) count++;
            while(count == tt.length) {
                if (j - i + 1 <= min){
                    res = sourse.substring(i, j + 1);
                    min = j - i + 1;
                }
                window[ss[i]]--;
                if (window[ss[i]] < need[ss[i]]) count--;
                i++;
                if (i >= ss.length) break;
            }
            j++;
        }
        return res;
    }

    public static String minWindowBest(String s, String t) {
        if (s == null || s == "" || t == null || t == "" || s.length() < t.length()) {
            return "";
        }
        //用来统计t中每个字符出现次数
        int[] needs = new int[128];
        //用来统计滑动窗口中每个字符出现次数
        int[] window = new int[128];
        for (int i = 0; i < t.length(); i++) {
            needs[t.charAt(i)]++;
        }
        int left = 0, right = 0;

        String res = "";
        //目前有多少个字符
        int count = 0;
        //用来记录最短需要多少个字符。
        int minLength = s.length() + 1;
        while (right < s.length()) {
            char ch = s.charAt(right);
            window[ch]++;
            if (needs[ch] > 0 && needs[ch] >= window[ch]) {
                count++;
            }
            //移动到不满足条件为止
            while (count == t.length()) {
                ch = s.charAt(left);
                if (needs[ch] > 0 && needs[ch] >= window[ch]) {
                    count--;
                }
                if (right - left + 1 < minLength) {
                    minLength = right - left + 1;
                    res = s.substring(left, right + 1);

                }
                window[ch]--;
                left++;
            }
            right++;

        }
        return res;
    }
}
