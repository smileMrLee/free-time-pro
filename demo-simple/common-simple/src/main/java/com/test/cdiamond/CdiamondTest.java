package com.test.cdiamond;

import com.caocao.cdiamond.client.DiamondConfigure;
import com.caocao.cdiamond.client.DiamondSubscriber;
import com.caocao.cdiamond.client.SubscriberListener;
import com.caocao.cdiamond.client.impl.DiamondClientFactory;
import com.caocao.cdiamond.configinfo.ConfigureInfomation;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;

/**
 * @author rongli
 * @since 2019-04-18 14:24
 */
@Slf4j
public class CdiamondTest {
    private static final String diamondDomainName = "10.0.10.54";
    private static final int port = 8081;
    private static DiamondConfigure diamondConfigure = new DiamondConfigure();
    private static DiamondSubscriber diamondSubscriber = DiamondClientFactory.getSingletonDiamondSubscriber();
    private static String GROUP_ID = "test";
    private static String DATA_ID = "publish1";

    public static void main(String[] args) {
        try {
            diamondConfigure.addDomainName(diamondDomainName);
            diamondConfigure.setPort(port);  //默认8081，开发测试环境可以不加
            //客户端v1.4.6新增自定义轮训时间 单位秒
            diamondConfigure.setPollingIntervalTime(10);
            diamondSubscriber.setDiamondConfigure(diamondConfigure);
            //注意：监听器不能写多个（单例的），否则可能会导致获取不到数据
            diamondSubscriber.setSubscriberListener(new SubscriberListener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(ConfigureInfomation configureInfomation) {
                    if (configureInfomation.getGroup().equals(GROUP_ID)) {
                        String groupId = configureInfomation.getGroup();
                        String dataId = configureInfomation.getDataId();
                        log.info("Receive change: " + groupId + "," + dataId);
                    }
                }
                @Override
                public void deleteConfigInfo(ConfigureInfomation configureInfomation) {
                    // 删除group某个dataid配置时触发
                    if (configureInfomation.getGroup().equals(GROUP_ID)) {
                        String groupId = configureInfomation.getGroup();
                        String dataId = configureInfomation.getDataId();
                        log.info("delete configure: " + groupId + "," + dataId);
                    }
                }
            });
            diamondSubscriber.start();
            //diamondSubscriber.getConfigInfo(DATA_ID, GROUP_ID, 1000);   //查询单个dataID
            diamondSubscriber.subscribeGroup(GROUP_ID);   //群组订阅
            Thread.sleep(1000000);
        } catch (Exception e) {
            log.error("load diamondConfigure from " + diamondDomainName + "failed!");
        }
    }
}
