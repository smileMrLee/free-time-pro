package com.evc.freetime.devtools.controller;

import com.evc.freetime.devtools.service.MemoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author rongli
 * @since 2019-04-24 11:24
 */
@RestController
@RequestMapping("/demo")
public class DemoDevtoolsController {
    @Resource
    private MemoryService memoryService;

    @RequestMapping("/get")
    public String getDynamicParam(){
        System.out.println("开始测试！");
        memoryService.addCollection();
        return String.valueOf(System.currentTimeMillis());
    }
}
/**/