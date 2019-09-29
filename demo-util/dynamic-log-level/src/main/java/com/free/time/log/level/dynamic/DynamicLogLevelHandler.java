package com.free.time.log.level.dynamic;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;
import java.util.Set;

/**
 * @author rongli
 * @since 2019-08-13 14:26
 */
@Slf4j
public class DynamicLogLevelHandler implements LogLevelHandler{
    protected static final String ROOT = "root";

    protected final static String DEFAULT_ROOT_LEVEL = "INFO";

    protected final static String CLAZZ_SEPARATOR = ",";

    protected final static String LEVEL_SEPARATOR = ":";

    private final Map<String, String> clazzLogLevelMaps = Maps.newConcurrentMap();

    private LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Async
    @Override
    public void handler(LogLevelEvent event){
        if (LogLevelEvent.ChangeType.DELETE == event.getChangeType()){
            deleteLogLevel();
        }else{
            changeLogLevel(event.getNewValue());
        }
    }
    /**
     * @param clazzLogLevels = "com.free.time.DemoClazz:info,com.free.time.BizClazz:debug"
     * clazzLogLevelArr = ["com.free.time.DemoClazz:info", "com.free.time.BizClazz:debug"]
     *
     * 1.更新、插入指定class 日志级别;
     * 2.并恢复被删除class的日志级别和root一致;
     * */
    private void changeLogLevel(String clazzLogLevels){
        try{
            Set<String> clazzChanges = Sets.newHashSet();
            String[] clazzLogLevelArr  = clazzLogLevels.split(CLAZZ_SEPARATOR);
            for (String clazzLogLevel : clazzLogLevelArr){
                String[] logLevelInfo =  clazzLogLevel.split(LEVEL_SEPARATOR);

                if (logLevelInfo.length != 2){
                    log.warn("不合法的日志级别:{}", clazzLogLevel);
                    continue;
                }
                String clazzName = logLevelInfo[0];
                String logLevel = logLevelInfo[1].toUpperCase();
                clazzChanges.add(clazzName);
                if (!checkGoalLevel(logLevel)){
                    log.warn("不支持的日志级别:{}", clazzLogLevel);
                    continue;
                }
                String currentLevel = clazzLogLevelMaps.get(clazzName);
                if (logLevel.equals(currentLevel)){
                    log.info("日志级别无变化{}:{}", clazzName, logLevel);
                    continue;
                }

                //设置某个类日志级别-可以实现定向日志级别调整
                Logger vLogger = loggerContext.getLogger(clazzName);
                setLogLevel(vLogger, Level.toLevel(logLevel));
                clazzLogLevelMaps.put(clazzName, logLevel);
            }

            // 查找出被删除掉的logger，恢复为root的级别；
            deleteLoggerLevel(clazzChanges, clazzLogLevelMaps.keySet());
        }catch (Exception e){
            log.warn("动态切换日志级别发生异常, content:{}", clazzLogLevels, e);
        }
    }

    /**
     * 全部恢复到root 日志级别。
     * */
    private void deleteLogLevel(){
        Logger rootLogger = loggerContext.getLogger(ROOT);
        Level rootLevel = rootLogger.getLevel();
        Set<String> clazzLogLevelDels = Sets.newHashSet();
        for(String clazzName : clazzLogLevelMaps.keySet()){
            Logger needDelLogLevel = loggerContext.getLogger(clazzName);
            needDelLogLevel.setLevel(rootLevel);
            clazzLogLevelDels.add(clazzName);
        }

        for(String clazzName : clazzLogLevelDels){
            clazzLogLevelMaps.remove(clazzName);
        }
    }

    /**
     * changedClazzSet 中存在本次新增的clazzLogLevelInfo 不能删除；
     * */
    private void deleteLoggerLevel(Set<String> changedClazzSet, Set<String> oldClazzSet){
        Logger rootLogger = loggerContext.getLogger(ROOT);
        Level rootLevel = rootLogger.getLevel();
        Sets.SetView<String> diffs = Sets.difference(changedClazzSet, oldClazzSet);
        for (String clazzName : diffs) {
            if (oldClazzSet.contains(clazzName)){
                // 执行恢复和root级别一致
                Logger needDelLogLevel = loggerContext.getLogger(clazzName);
                needDelLogLevel.setLevel(rootLevel);
                clazzLogLevelMaps.remove(clazzName);
            }
        }

    }

    private void setLogLevel(Logger logger, Level level){
        if (logger == null){
            log.warn("Logger is null! no changes!");
            return;
        }

        Level levelCurrent = logger.getLevel();
        log.info("Log level switch success! logName={}, oldLevel={}, newLevel={}", logger.getName(), levelCurrent==null ? "null" : levelCurrent.toString(), level.toString());
        logger.setLevel(level);
    }

    private boolean checkGoalLevel(String goalLevel){
        if ("DEBUG".equals(goalLevel)){
            return true;
        }else if("INFO".equals(goalLevel)){
            return true;
        }else if("WARN".equals(goalLevel)){
            return true;
        }else if("ERROR".equals(goalLevel)){
            return true;
        }
        return false;
    }
}
