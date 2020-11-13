import java.util.LinkedList;
import java.util.List;

/**
 * @author lirong
 * @createTime 2020年11月11日 15:34
 */
public class AllArrangeSolution {
    /** 结果存储-容器 */
    List<List<Integer>> res = new LinkedList<>();

    List<List<Integer>> permute(int[] nums){
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(track, nums);
        return res;
    }

    void backtrack(LinkedList<Integer> track, int[] nums){
        if (track.size() == nums.length){
            // 说明到达决策树底部
            res.add(new LinkedList<Integer>(track));
            return;
        }

        for (int i = 0; i < nums.length; i++){
            if (track.contains(nums[i])){
                // 路径已经存在
                continue;
            }
            track.add(nums[i]);
            // 继续选择下一级决策路径，此处递归将会沿着nums[i]决策一直触底。
            backtrack(track, nums);
            // nums[i]决策已经轮训完毕，此时撤回nums[i]，换nums[i+1]继续寻找触底决策。
            track.removeLast();
        }
    }

    public static void main(String[] args) {
        AllArrangeSolution solution = new AllArrangeSolution();
        solution.permute(new int[]{2,1,3});
        System.out.println(solution.res);
    }
}
