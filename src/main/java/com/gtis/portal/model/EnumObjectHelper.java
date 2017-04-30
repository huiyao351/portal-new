package com.gtis.portal.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jibo
 * Date: 14-4-18
 * Time: 上午5:37
 * To change this template use File | Settings | File Templates.
 */

public class EnumObjectHelper {
    public static Map MenuOpenModel = new LinkedHashMap() {
        {
            put("0", "0_在当前页面框架内打开");
            put("1", "1_在新页面窗口中打开");
            put("2", "2_在当前窗口中打开");
            put("3", "3_在当前页面框架内源码加载");
        }
    };
}
