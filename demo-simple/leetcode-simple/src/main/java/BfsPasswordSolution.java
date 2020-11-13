import com.google.common.collect.Lists;

import java.util.*;

/**
 * @author lirong
 * @createTime 2020年11月12日 15:32
 */
public class BfsPasswordSolution {

    public static void main(String[] args) {
        BfsPasswordSolution solution = new BfsPasswordSolution();
        int step = solution.bfs(Lists.newArrayList("2192,0201"),"2719");
        System.out.println(step);
    }

    String plusOne(String s, int j){
        char[] ch = s.toCharArray();
        if (ch[j] == '9'){
            ch[j] = '0';
        }else {
            ch[j] += 1;
        }
        return new String(ch);
    }

    String minusOne(String s, int j){
        char[] ch = s.toCharArray();
        if (ch[j] == '0'){
            ch[j] = '9';
        }else {
            ch[j] -= 1;
        }
        return new String(ch);
    }

    /**
     * 方案一：单向BFS广度搜索
     */
    int bfs(List<String> deadends, String target){
        Set<String> deads = new HashSet<>();
        for (String s: deadends){
            deads.add(s);
        }
        Queue<String> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        q.offer("0000");
        visited.add("0000");
        int step = 0;
        while (!q.isEmpty()){
            int size = q.size();
            for (int i = 0; i < size; i++){
                String current = q.poll();
                if (deads.contains(current)){
                    continue;
                }
                if (target.equals(current)){
                    return step;
                }
                for (int j = 0; j < 4; j++){
                    String up = plusOne(current, j);
                    String down = minusOne(current, j);
                    if (!visited.contains(up)){
                        q.offer(up);
                        visited.add(up);
                    }
                    if (!visited.contains(down)){
                        q.offer(down);
                        visited.add(down);
                    }
                }
            }
            step++;
        }
        return -1;
    }

    /**
     * 方案二：
     */
    public int openLock(List<String> deadends, String target){
        Set<String> deads = new HashSet<>();
        for (String s: deadends){
            deads.add(s);
        }
        Set<String> q1 = new HashSet<>();
        Set<String> q2 = new HashSet<>();
        Set<String> visited = new HashSet<>();
        q1.add("0000");
        q2.add(target);
        int step = 0;
        while (!q1.isEmpty() && !q2.isEmpty()){
            Set<String> temp = new HashSet<>();
            for (String current : q1){
                if (deads.contains(current)){
                    continue;
                }
                if (q2.contains(current)){
                    return step;
                }
                visited.add(current);
                for (int j = 0; j < 4; j++){
                    String up = plusOne(current, j);
                    String down = minusOne(current, j);
                    if (!visited.contains(up)){
                        visited.add(up);
                    }
                    if (!visited.contains(down)){
                        visited.add(down);
                    }
                }
            }
            step++;
            q1 = q2;
            q2 = temp;
        }
        return -1;
    }

}
