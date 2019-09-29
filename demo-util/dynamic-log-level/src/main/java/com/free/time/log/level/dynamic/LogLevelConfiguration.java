package com.free.time.log.level.dynamic;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author rongli
 * @since 2019-09-29 11:02
 */
@Slf4j
@Configuration
public class LogLevelConfiguration {
    private static Map<String, String> listeners = Maps.newConcurrentMap();

    @Value("${log.level.dynamic.listener: apollo}")
    private String listener;

    @Value("${log.level.dynamic.enable: false}")
    private Boolean enable;

    static{
        // 初始化默认listener，利用Class.forName()进行解耦，不必强制依赖第三方配置中心jar包。
        listeners.put("apollo", "com.free.time.log.level.dynamic.ApolloLogLevelListener");
    }

    public static void registerListener(String listener, String clazzName){
        listeners.put(listener, clazzName);
    }

    @Bean
    public LogLevelHandler logLevelHandler(){
        return new DynamicLogLevelHandler();
    }

    @Bean
    public LogLevelListener logLevelListener() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        log.info("Dynamic log level listener use:{}", listener);
        Class listenerClazz = Class.forName(listeners.get(listener));
        LogLevelListener instance = (LogLevelListener) listenerClazz.newInstance();
        instance.setEnable(enable);
        instance.setHandler(logLevelHandler());
        instance.listen();
        return instance;
    }

}
