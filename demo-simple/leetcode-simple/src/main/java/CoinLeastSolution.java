import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 给出足够多的硬币，币值【1，2，5】，找出需要拼凑出 N 的最少硬币数量凑钱方案。
 * @author lirong
 * @createTime 2020年12月11日 16:35
 */
@Slf4j
public class CoinLeastSolution {

    public static Integer dpCoinSelect(List<Integer> coins, int amount, int[] tempResult){
        if (amount == 0) {
            return 0;
        }
        if (amount < 0){
            return -1;
        }
        int num = -1;
        log.info("计算拼凑{}需要几枚硬币", amount);
        for (Integer coin : coins){
            if (amount - coin > 0) {
                int tempAmountResult = tempResult[amount - coin];
                if (tempAmountResult != 0) {
                    return tempAmountResult;
                }
            }
            int subResult = dpCoinSelect(coins, amount - coin, tempResult);
            if (subResult == -1){
                continue;
            }
            subResult +=1;
            if (subResult > 0 && num == -1){
                num = subResult;
            }
            num = Math.min(num, subResult);
            tempResult[amount-coin] = num;
        }
        log.info("拼凑{}需要{}枚硬币", amount, num);
        return num;
    }

    public static void main(String[] args) {
        List<Integer> resultSeq = Lists.newArrayList();
        int result = dpCoinSelect(Lists.newArrayList(5, 2, 1), 11, new int[11]);
        System.out.println("输出结果：" + result);
        System.out.println("凑钱的顺序" + resultSeq);

        System.out.println("输出结果：" + dpCoinSelect(Lists.newArrayList(5, 2, 1), 10, new int[10]));
    }


}
