import lombok.extern.slf4j.Slf4j;

/**
 *
 * 扫描字符串中指定字符是否成对出现。
 *
 * 给定字符串sswfev(dwfdefv)dcdfvcf{defer}cecfee[ecevfv] 共享屏幕 判断字符串中的小括号、中括号 大括号是否是成对出现的 
 *
 * 大概就是这样了。
 *
 * @author lirong
 * @createTime 2021年01月25日 15:41
 */
@Slf4j
public class StringHandlerTest {
    /** 不好意思，刚才题目没粘贴出来。我结束共享回去粘贴的 */
    public static void main(String[] args) {
        String resource = "sswfev(dwfdefv)dcdfvcf{defer}cecfee[ecevfv]";
        boolean smallResult = judgeResult(resource, "\\(", "\\)");
        log.info("小括号成对出现的扫描结果：{}", smallResult);
        boolean midResult = judgeResult(resource, "\\[", "\\]");
        log.info("中括号成对出现的扫描结果：{}", midResult);
        boolean bigResult = judgeResult(resource, "\\{", "\\}");
        log.info("大括号成对出现的扫描结果：{}", bigResult);
    }

    public static boolean judgeResult(String resource, String targetStart, String targetEnd) {
        String[] bigArr = resource.split(targetStart);
        boolean hasBefore = false;
        for (String beforeStr : bigArr){
            if (hasBefore){
                // 寻找是否有目标括号 右括号
                String[] beforeStrArr = beforeStr.split(targetEnd);
                if (beforeStrArr.length != 2){
                    return false;
                }
            }else {
                hasBefore = true;
            }
        }
        return true;
    }

}
