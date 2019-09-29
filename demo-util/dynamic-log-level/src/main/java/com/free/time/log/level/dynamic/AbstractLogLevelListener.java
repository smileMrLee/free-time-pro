package com.free.time.log.level.dynamic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author rongli
 * @since 2019-09-29 11:07
 */
@Slf4j
public abstract class AbstractLogLevelListener implements LogLevelListener {
    private Boolean enable;

    protected LogLevelHandler logLevelHandler;

    protected String LOG_LEVEL_DYNAMIC = "log.level.dynamic.content";

    @Override
    public void setEnable(Boolean enable){
        this.enable = enable;
    }

    public boolean checkEnable(){
        if (enable != null){
            log.info("Log level dynamic enable: {}", enable);
            return enable;
        }

        return false;
    }

    protected abstract LogLevelEvent convertLogLevelEvent(Object sourceEvent);

    @Override
    public void setHandler(LogLevelHandler handler) {
        this.logLevelHandler = handler;
    }

}
