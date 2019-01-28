package com.free.time.netty.dubbo;

/**
 * @author changle
 * time 2019-01-28.
 * description to do
 */
public class DubboMethodDefined {
    public static void main(String[] args) {
        getMethodDefined();
    }

    public static void getMethodDefined() {
        TelnetUtil.analyzeServiceMethods("172.20.2.121", 49053, "cn.gov.zcy.id.service.IdentityService");
    }
}
