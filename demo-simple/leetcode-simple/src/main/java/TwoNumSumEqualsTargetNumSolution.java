import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *
 *  
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 * TODO 负数时不对
 *
 */
class TwoNumSumEqualsTargetNumSolution {
    public int[] twoSum(int[] nums, int target) {
        // 如果想要返回的结果是按数组顺序输出，此处换成LinkedHashMap
        Map<Integer, Integer> numOfIndexMaps = new HashMap(nums.length);

        // 此处使用二维数组也可以
        int[] targertArray = new int[2];
        int index = 0;
        int sameK = target/2;
        boolean oushu = target % 2 == 0;
        boolean existFlag = false;
        Integer existIndex = 0;
        for(int k : nums){
            if (oushu && k == sameK){
                if (existFlag){
                    targertArray[0] = existIndex;
                    targertArray[1] = index;
                    return targertArray;
                }
                existFlag = true;
                existIndex = index;
            }
            numOfIndexMaps.put(k, index++);
        }
        int selectedIndex = 0;
        List<Integer> selectedNum = new ArrayList();
        for (Map.Entry<Integer,Integer> entry : numOfIndexMaps.entrySet()){
            if(entry.getKey()<target && !selectedNum.contains(entry.getKey())){
                int targetNum = target - entry.getKey();
                if(numOfIndexMaps.containsKey(targetNum) && targetNum != entry.getKey()){
                    targertArray[selectedIndex++] = entry.getValue();
                    targertArray[selectedIndex++] = numOfIndexMaps.get(targetNum);
                    selectedNum.add(targetNum);
                }
            }
        }
        return targertArray;
    }

    public static void main(String[] args){
        int[] nums = new int[]{1, 3, 2, 7, 6, 11, 15};
        int target = 9;
        TwoNumSumEqualsTargetNumSolution twoNumSumEqualsTargetNumSolution = new TwoNumSumEqualsTargetNumSolution();
        //int[] resultIndex = twoNumSumEqualsTargetNumSolution.twoSum(nums, target);
        //System.out.println(resultIndex);

        int[] nums2 = new int[]{3, 2, 4};
        int target2 = 6;
        int[] resultIndex2 = twoNumSumEqualsTargetNumSolution.twoSum(nums2, target2);
        System.out.println(resultIndex2);

    }
}