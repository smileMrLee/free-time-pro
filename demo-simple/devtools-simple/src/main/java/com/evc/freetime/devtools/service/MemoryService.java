package com.evc.freetime.devtools.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.evc.freetime.devtools.model.MemoryBO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rongli
 * @since 2019-07-02 14:40
 */
@Service
public class MemoryService {
    private List<MemoryBO> list = new ArrayList<>();

    @Async
    public void addCollection(){
        String str = "{\n" +
                "    \"query\": \"111.721471,29.020581\",\n" +
                "    \"searchUrl\": \"https://restapi.amap.com/v3/geocode/regeo?key=7d332f845c93a3fc9d9e8ad88cda4152&location=111.721471,29.020581&radius=200&extensions=all&batch=false&roadlevel=0\",\n" +
                "    \"resultAmap\": {\n" +
                "        \"address\": \"碑吉边路西50米\",\n" +
                "        \"businessarea\": \"[]\",\n" +
                "        \"direction\": \"西北\",\n" +
                "        \"distance\": \"50.4769\",\n" +
                "        \"id\": \"B0FFHCQEZW\",\n" +
                "        \"location\": \"111.72118,29.020957\",\n" +
                "        \"name\": \"嘉景苑小区\",\n" +
                "        \"poiweight\": \"0.212125\",\n" +
                "        \"tel\": [],\n" +
                "        \"type\": \"商务住宅;住宅区;住宅小区\"\n" +
                "    },\n" +
                "    \"resulBmap\": null,\n" +
                "    \"searchUrlBmap\": \"http://api.map.baidu.com/parking/search?ak=MQRXm51I9uZvf4kT4a1HXL45fWeWDMyl&location=111.721471,29.020581&coordtype=gcj02ll\"\n" +
                "}";
        for (int i= 0 ; i < 10; i++){
            MemoryBO bo = JSON.parseObject(str, new TypeReference<MemoryBO>() {});
            bo.setQuery(String.valueOf(System.currentTimeMillis()));
            list.add(bo);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
