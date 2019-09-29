package com.free.time.log.level.dynamic;

/**
 * @author rongli
 * @since 2019-09-29 11:03
 */
public interface LogLevelListener {
    void setEnable(Boolean enable);
    void setHandler(LogLevelHandler handler);
    void listen();
}
