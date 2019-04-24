package com.evc.freetime.devtools.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rongli
 * @since 2019-04-24 11:24
 */
@RestController
@RequestMapping("/demo")
public class DemoDevtoolsController {

    @RequestMapping("/get")
    public String getDynamicParam(){
        System.out.println("开始测试！");
        return String.valueOf(System.currentTimeMillis());
    }
}
/**/