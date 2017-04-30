package com.gtis.portal.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * @文件说明
 * @作者 deery
 * @创建日期 11:13
 * @版本号 V 1.0
 */
public class test {
    public static void main(String[] args)throws Exception {
        String paramString = "[{'proid':'testttt','taskid':'eeeeee'},{'proid':'1111','taskid':'eee222eee'},{'proid':'333','taskid':'eee4444eee'}]";
        JSONArray jsonArray = JSON.parseArray(paramString);
        System.out.println(JSON.toJSONString(jsonArray));
    }
}
