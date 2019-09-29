package com.free.time.sensitive;

import com.free.time.sensitive.core.KWSeeker;
import com.free.time.sensitive.core.KWSeekerManage;
import org.springframework.stereotype.Component;

/**
 * @author changle
 *         time 18/11/26.
 *         搜索器工厂类
 */
@Component
public class SeekerFactory {
    private volatile KWSeekerManage kwSeekerManage = null;

    public KWSeeker getKWSeeker(String wordType){
        if (kwSeekerManage == null){
            kwSeekerManage = HttpFileKWSeekerProcessor.getInstance();
        }
        return kwSeekerManage.getKWSeeker(wordType);
    }
}
