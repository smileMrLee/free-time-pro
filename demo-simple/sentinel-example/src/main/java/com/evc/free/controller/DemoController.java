package com.evc.free.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rongli
 * @since 2019-05-24 17:46
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Value("${csp.sentinel.dashboard.server}")
    private String dashBoardHost;
    @RequestMapping("/get")
    public String execute(){
        return "返回sentinel控制台地址：" + dashBoardHost;
    }
}
