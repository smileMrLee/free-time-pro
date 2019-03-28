/**
 * @author changle
 * time 2019-03-28.
 * 给定正整数 N，返回小于等于 N 且具有至少 1 位重复数字的正整数。
 *
 *
 *
 * 示例 1：
 *
 * 输入：20
 * 输出：1
 * 解释：具有至少 1 位重复数字的正数（<= 20）只有 11 。
 * 示例 2：
 *
 * 输入：100
 * 输出：10
 * 解释：具有至少 1 位重复数字的正数（<= 100）有 11，22，33，44，55，66，77，88，99 和 100 。
 * 示例 3：
 *
 * 输入：1000
 * 输出：262
 *
 *
 * 提示：
 *
 * 1 <= N <= 10^9
 */
public class DupDigistsAtMostN {

    boolean[] isUsed=new boolean[10];
    int n;
    int index=1;
    // 统计个数
    int count=0;
    // 位数索引
    int nindex;

    public int numDupDigitsAtMostN(int N) {
        n=N;
        int tlength=String.valueOf(N).length();

        for(int i=1;i<=tlength;i++){
            nindex=i;
            for (int j = 1; j < 10; j++) {
                isUsed[j]=true;
                dfs(j);
                isUsed[j]=false;
            }
            index=1;
        }
        return N-count;
    }

    public void dfs(int temp){
        if(temp>n) {
            return;
        }
        if(index==nindex){
            count++;
            return;
        }

        for(int i=0;i<10;i++){
            if (temp*10>n) {
                break;
            }
            if(!isUsed[i]){
                isUsed[i]=true;
                index++;
                dfs(temp*10+i);
                index--;
                isUsed[i]=false;
            }
        }
    }

    public static void main(String[] args) {
        DupDigistsAtMostN cal = new DupDigistsAtMostN();
        int sum = cal.numDupDigitsAtMostN(100);
        System.out.println(sum);
    }

}
