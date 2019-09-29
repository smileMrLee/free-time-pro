package com.free.time.sensitive;

import com.free.time.sensitive.conf.SensitiveConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author changle
 *         time 18/11/26.
 *         搜索器自动初始化
 */
@Configuration
@ComponentScan({"com.free.time.sensitive"})
@EnableConfigurationProperties({SensitiveConfigProperties.class})
public class KWSeekerAutoConfiguration {
    @Autowired
    SensitiveConfigProperties sensitiveConfig;

    @PostConstruct
    public void init(){
        //初始化搜索器
        HttpFileKWSeekerProcessor.getInstance(sensitiveConfig.getFileUrl());
    }
}
