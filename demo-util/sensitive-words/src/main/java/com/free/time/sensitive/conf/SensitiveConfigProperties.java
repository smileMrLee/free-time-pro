package com.free.time.sensitive.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author changle
 *         time 18/11/26.
 *         description to do
 */

@ConfigurationProperties(prefix = SensitiveConfigProperties.SENSITIVE_PREFIX)
@Data
public class SensitiveConfigProperties {
    public static final String SENSITIVE_PREFIX = "com/free/time/sensitive";
    private String fileUrl;
}
