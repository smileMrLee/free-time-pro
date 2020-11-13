import java.util.Arrays;

/**
 * @author lirong
 * @createTime 2020年11月10日 21:33
 */
public class Solution {
    private static final int NULL = -1;

    public int coinChange(int[] coins, int amount) {
        // 初始化 dp 数组
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, NULL);
        dp[0] = 0;

        // dp 开始
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin < 0) {
                    continue;
                }
                if (dp[i - coin] == NULL) {
                    continue;
                }
                if (dp[i] == -1) {
                    // 使用第一个面额，加1是因为第一个面额也算是一枚硬币
                    dp[i] = dp[i - coin] + 1;
                } else {
                    // 使用非第一个面额凑钱，和使用第一个面额凑钱的进行比较，谁比较小就取谁
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.coinChange(new int[]{1, 2, 5}, 11));
//        System.out.println(solution.coinChange(new int[]{2}, 3));
//        System.out.println(solution.coinChange(new int[]{}, 11));
    }
}