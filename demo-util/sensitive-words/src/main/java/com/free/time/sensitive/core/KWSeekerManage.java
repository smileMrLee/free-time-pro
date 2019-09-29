package com.free.time.sensitive.core;

import com.free.time.sensitive.model.KeyWord;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * 敏感词管理器
 * 
 * @author hailin0@yeah.net
 * @createDate 2016年5月22日
 *
 */
public class KWSeekerManage {

    /**
     * 敏感词模块. key为模块名，value为对应的敏感词搜索器
     */
    protected Map<String, KWSeeker> seekers = new ConcurrentHashMap<String, KWSeeker>();

    /**
     * 初始化
     */
    public KWSeekerManage() {
    }

    /**
     * 
     * @param seekers
     */
    public KWSeekerManage(Map<String, KWSeeker> seekers) {
        this.seekers.putAll(seekers);
    }

    /**
     * 构建搜索器
     * */
    public void buildSeekers(Map<String, String> map){
        Set<Map.Entry<String, String>> entrySet = map.entrySet();

        Map<String, KWSeeker> seekers = new HashMap<String, KWSeeker>();
        Set<KeyWord> kws;

        for (Map.Entry<String, String> entry : entrySet) {
            long start = System.currentTimeMillis();
            String value = entry.getValue();

            String[] words = value.split(",");
            kws = new HashSet<KeyWord>();
            for (String word : words) {
                kws.add(new KeyWord(word));
            }
            seekers.put(entry.getKey(), new KWSeeker(kws));
        }
        this.seekers.putAll(seekers);
    }
    /**
     * 获取默认敏感词搜索器
     *
     * 默认是第一个.
     * @return
     */
    public KWSeeker getDefaultKWSeeker() {
        for(Map.Entry<String, KWSeeker> seekerEntry : seekers.entrySet()){
            return seekerEntry.getValue();
        }
        return null;
    }

    /**
     * 获取一个敏感词搜索器
     *
     * @param wordType
     * @return
     */
    public KWSeeker getKWSeeker(String wordType) {
        return seekers.get(wordType);
    }


    /**
     * 加入一个敏感词搜索器
     * 
     * @param wordType
     * @param kwSeeker
     * @return
     */
    public void putKWSeeker(String wordType, KWSeeker kwSeeker) {
        seekers.put(wordType, kwSeeker);
    }

    /**
     * 移除一个敏感词搜索器
     * 
     * @param wordType
     * @return
     */
    public void removeKWSeeker(String wordType) {
        seekers.remove(wordType);
    }

    /**
     * 清除空搜索器
     */
    public void clear() {
        seekers.clear();
    }

}
