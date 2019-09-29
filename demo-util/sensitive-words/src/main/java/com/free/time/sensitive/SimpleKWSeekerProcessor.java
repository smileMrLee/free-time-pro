package com.free.time.sensitive;

import com.free.time.sensitive.conf.Config;
import com.free.time.sensitive.core.KWSeekerManage;

import java.util.Map;

/**
 * 
 * 简单的敏感词处理器，从配置文件读取敏感词初始化，<br>
 * 使用者只需要在classpath放置sensitive-word.properties敏感词文件即可
 * 
 * @author hailin0@yeah.net
 * @createDate 2016年5月22日
 *
 */
public class SimpleKWSeekerProcessor extends KWSeekerManage {

    private static volatile SimpleKWSeekerProcessor instance;

    /**
     * 获取实例
     * 
     * @return
     */
    public static SimpleKWSeekerProcessor getInstance() {
        if (null == instance) {
            synchronized (SimpleKWSeekerProcessor.class) {
                if (null == instance) {
                    instance = new SimpleKWSeekerProcessor();
                }
            }
        }
        return instance;
    }

    /**
     * @update 2017年5月16日 by changle
     * 更新内容: 增强敏感词文件读取方式,可手动传入文件地址
     * */
    public static SimpleKWSeekerProcessor getInstance(String fileName) {
        if (null == instance) {
            synchronized (SimpleKWSeekerProcessor.class) {
                if (null == instance) {
                    instance = new SimpleKWSeekerProcessor(fileName);
                }
            }
        }
        return instance;
    }

    /**
     * 私有构造器
     * @update 2017年5月16日 by changle
     * 更新内容: 增强敏感词文件读取方式,可手动传入文件地址
     */
    private SimpleKWSeekerProcessor(String fileName) {
        initialize(fileName);
    }

    private SimpleKWSeekerProcessor() {
        initialize(null);
    }

    /**
     *  @update 2017年5月16日 by changle
     *  敏感词初始化，改为使用父类逻辑。便于各个子类复用。
     */
    private void initialize(String fileName) {
        Map<String, String> map = Config.newInstance(fileName).getAll();
        super.buildSeekers(map);
    }
}
