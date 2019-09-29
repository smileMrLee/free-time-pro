package com.free.time.log.level.dynamic;

import lombok.Builder;
import lombok.Data;

/**
 * @author rongli
 * @since 2019-09-29 13:30
 */
@Data
@Builder
public class LogLevelEvent {
    private ChangeType changeType;

    private String newValue;

    private String oldValue;

    enum ChangeType{
        ADD,MODIFY,DELETE;
    }
}
