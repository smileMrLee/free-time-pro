import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author changle
 * time 2019-03-28.
 * description to do
 */
@Slf4j
public class DupDigistsAtMostNV2 {
    public static int numDupDigitsAtMostN(int N) {
        List<Integer> nums = new ArrayList<>();
        int tempN = N + 1;
        while(tempN != 0){
            nums.add(0,tempN%10);
            tempN/=10;
        }
        int countInvalid = 0;
        //求出整数部分不重复数据
        for(int i = 0; i<nums.size()-1; i++){
            countInvalid += 9 * permutation(9,i);
            log.warn("计算长度{}的数据重复个数，计算公式：9 * permutation(9,{})", nums.size()-1, i);
        }
        //求出数字的位数等于n的不重复数据个数
        int[] isOccupied = new int[10];
        Arrays.fill(isOccupied,-1);
        for(int i = 0;i<nums.size();i++){
            int digit = nums.get(i);
            for(int j = i==0?1:0; j<digit; j++){
                if(isOccupied[j] != -1) {
                    continue;
                } else{
                    int childResut = permutation(10-(i+1),nums.size()-i-1);
                    log.warn("计算长度{}的数据重复个数，digit:{}， j:{}，计算公式：permutation({},{}), 计算个数：{}", nums.size(), digit, j, 10-(i+1), nums.size()-i-1, childResut);
                    countInvalid += childResut;
                }
            }
            if(isOccupied[digit] != -1) break;
            isOccupied[digit] = 1;
        }
        return N - countInvalid;
    }
    private static int permutation(int n, int c){
        int ans = 1;
        for(int i = 0;i<c;i++,n--) ans *= n;
        return ans;
    }

    public static void main(String[] args) {
        int num = numDupDigitsAtMostN(8765);
        System.out.println(num);
    }
}
