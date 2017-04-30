/*
 * Project:  onemap
 * Module:   common
 * File:     FreeMarkerContext.java
 * Modifier: xyang
 * Modified: 2013-05-31 04:36:16
 *
 * Copyright (c) 2013 Gtmap Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */

package com.gtis.portal.support.freemarker;

import com.alibaba.fastjson.JSON;
import com.gtis.config.AppConfig;
import com.gtis.plat.vo.UserInfo;
import com.gtis.web.SessionUtil;
import org.apache.commons.io.IOUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:rayzy1991@163.com">Ray Zhang</a>
 * @version V1.0, 13-4-23
 */

public class FreeMarkerContext {

    public String getEx(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            e.printStackTrace(pw);
            return sw.toString();
        } finally {
            IOUtils.closeQuietly(pw);
        }
    }

    public String toJSON(Object obj) {
        return JSON.toJSONString(obj);
    }

    public UserInfo getUser() {
        return SessionUtil.getCurrentUser();
    }

    public String getUserName() {
        return getUser().getUsername();
    }

    public String getEnv(String key){
        String value = AppConfig.getProperty(key);
        if (value == null){
            value = "";
        }
        value = AppConfig.getPlaceholderValue(value);
        return value;
    }

    public Boolean getBooleanEnv(String key){
        return AppConfig.getBooleanProperty(key,false);
    }
}