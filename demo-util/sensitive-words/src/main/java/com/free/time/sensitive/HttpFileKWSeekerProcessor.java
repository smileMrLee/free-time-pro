package com.free.time.sensitive;

import com.free.time.sensitive.conf.Config;
import com.free.time.sensitive.core.KWSeekerManage;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author changle
 *         time 18/11/23.
 *         从远程加载敏感词库
 */
@Slf4j
public class HttpFileKWSeekerProcessor extends KWSeekerManage {
    private static volatile HttpFileKWSeekerProcessor instance;

    private HttpFileKWSeekerProcessor(){

    }

    public static HttpFileKWSeekerProcessor getInstance(){
        //获取实例前，实例已经由KWSeekerAutoConfigration 初始化。
        return getInstance(null);
    }
    /**
     * @update 2017年5月16日 by changle
     * 更新内容: 增强敏感词文件读取方式,可手动传入文件地址
     * */
    public static HttpFileKWSeekerProcessor getInstance(String fileUrl) {
        if (null == instance) {
            synchronized (HttpFileKWSeekerProcessor.class) {
                if (null == instance) {
                    instance = new HttpFileKWSeekerProcessor(fileUrl);
                }
            }
        }
        return instance;
    }

    private HttpFileKWSeekerProcessor(String fileUrl) {
        initialize(fileUrl);
    }

    /**
     *  @update 2018年11月26日 by changle
     *  优化敏感词获取来源，增加http下载敏感词方式
     */
    private void initialize(String file) {
        log.info("zcy-sensitive-words file url is {}！", file);
        Map<String, String> map = Config.newInstance(file, Config.FileTypeEnum.HTTP).getAll();
        super.buildSeekers(map);
        log.info("zcy-sensitive-words initialize success！");
    }
}
