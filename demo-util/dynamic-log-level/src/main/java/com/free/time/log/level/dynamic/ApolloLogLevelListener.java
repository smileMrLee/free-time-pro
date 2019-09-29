package com.free.time.log.level.dynamic;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author changle
 *         time 19/1/7.
 *         动态切换日志级别-默认实现
 */
@Slf4j
@Data
public class ApolloLogLevelListener extends AbstractLogLevelListener {
    public ApolloLogLevelListener(){
    }

    public ApolloLogLevelListener(LogLevelHandler logLevelHandler){
        super.logLevelHandler = logLevelHandler;
    }

    @Override
    public void listen() {
        if (!checkEnable()){
            return;
        }
        //config instance is singleton for each namespace and is never null
        Config config = ConfigService.getAppConfig();
        config.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                for(String key : changeEvent.changedKeys()){
                    ConfigChange configChange = changeEvent.getChange(key);
                    if (key.startsWith(LOG_LEVEL_DYNAMIC)){
                        log.info("Found change key: {}, oldValue: {}, newValue: {}, changeType: {}",
                                configChange.getPropertyName(), configChange.getOldValue(), configChange.getNewValue(), configChange.getChangeType());
                        //解析事件类型
                        LogLevelEvent logLevelEvent = convertLogLevelEvent(configChange);
                        logLevelHandler.handler(logLevelEvent);
                        continue;
                    }
                }
            }
        });
    }

    @Override
    protected LogLevelEvent convertLogLevelEvent(Object sourceEvent){
        ConfigChange configChange = (ConfigChange) sourceEvent;
        LogLevelEvent.ChangeType eventType = null;
        if (PropertyChangeType.DELETED == configChange.getChangeType()){
            eventType = LogLevelEvent.ChangeType.DELETE;
        }else if (PropertyChangeType.ADDED == configChange.getChangeType()){
            eventType = LogLevelEvent.ChangeType.ADD;
        }else if (PropertyChangeType.MODIFIED == configChange.getChangeType()){
            eventType = LogLevelEvent.ChangeType.MODIFY;
        }
        return LogLevelEvent.builder()
                .changeType(eventType)
                .newValue(configChange.getNewValue())
                .oldValue(configChange.getOldValue())
                .build();
    }
}
