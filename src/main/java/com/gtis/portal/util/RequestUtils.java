/*
 * Project:  onemap
 * Module:   common
 * File:     RequestUtils.java
 * Modifier: xyang
 * Modified: 2013-05-29 03:12:37
 *
 * Copyright (c) 2013 Gtmap Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.gtis.portal.util;


import com.gtis.config.AppConfig;
import com.gtis.plat.vo.UserInfo;
import com.gtis.web.SessionUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 11-12-2
 */
public class RequestUtils {
    public static UrlPathHelper URL_PATH_HELPER = new UrlPathHelper() {
        @Override
        public String getLookupPathForRequest(HttpServletRequest request) {
            String key = request.getRequestURI() + "_lookupPath";
            String path = (String) request.getAttribute(key);
            if (path == null) {
                request.setAttribute(key, path = super.getLookupPathForRequest(request));
            }
            return path;
        }
    };

    public static PathMatcher PATH_MATCHER = new AntPathMatcher();

    public static String getClientIP(HttpServletRequest request) {
        String xForwardedFor;
        xForwardedFor = StringUtils.trimToNull(request.getHeader("$wsra"));
        if (xForwardedFor != null) {
            return xForwardedFor;
        }
        xForwardedFor = StringUtils.trimToNull(request.getHeader("X-Real-IP"));
        if (xForwardedFor != null) {
            return xForwardedFor;
        }
        xForwardedFor = StringUtils.trimToNull(request.getHeader("X-Forwarded-For"));
        if (xForwardedFor != null) {
            int spaceIndex = xForwardedFor.indexOf(',');
            if (spaceIndex > 0) {
                return xForwardedFor.substring(0, spaceIndex);
            } else {
                return xForwardedFor;
            }
        }
        return request.getRemoteAddr();
    }

    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        int end = url.indexOf(".");
        if (end == -1)
            return "";
        int start = url.indexOf("//");
        return url.substring(start + 2, end);
    }

    public static boolean isPost(HttpServletRequest request) {
        return "POST".equals(request.getMethod());
    }

    public static boolean matchAny(HttpServletRequest request, UrlPathHelper urlPathHelper, PathMatcher pathMatcher, String[] patterns) {
        if (ArrayUtils.isNotEmpty(patterns)) {
            String lookupPath = urlPathHelper.getLookupPathForRequest(request);
            for (String pattern : patterns) {
                if (pathMatcher.match(pattern, lookupPath)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean matchAll(HttpServletRequest request, UrlPathHelper urlPathHelper, PathMatcher pathMatcher, String[] patterns) {
        if (ArrayUtils.isNotEmpty(patterns)) {
            String lookupPath = urlPathHelper.getLookupPathForRequest(request);
            for (String pattern : patterns) {
                if (!pathMatcher.match(pattern, lookupPath)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Boolean getBool(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return StringUtils.isEmpty(value) ? null : BooleanUtils.toBooleanObject(value);
    }

    public static boolean getBool(HttpServletRequest request, String name, boolean def) {
        Boolean value = getBool(request, name);
        return value == null ? def : value;
    }

    public static Integer getInt(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return StringUtils.isEmpty(value) ? null : NumberUtils.createInteger(value);
    }

    public static int getInt(HttpServletRequest request, String name, int def) {
        Integer value = getInt(request, name);
        return value == null ? def : value;
    }

    public static Double getDouble(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return StringUtils.isEmpty(value) ? null : NumberUtils.createDouble(value);
    }

    public static double getDouble(HttpServletRequest request, String name, double def) {
        Double value = getDouble(request, name);
        return value == null ? def : value;
    }
    public static String initOptProperties(String url) {
        if (url != null) {
            PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("${", "}");
            Properties properties = new Properties();
            properties.putAll(AppConfig.getProperties());
            url = propertyPlaceholderHelper.replacePlaceholders(url, properties);
        }

        return url;
    }

    public static boolean checkIsAdmin(){
        UserInfo userInfo = SessionUtil.getCurrentUser();
        if (userInfo != null && StringUtils.equalsIgnoreCase("0",userInfo.getId())){
            return true;
        }
        return false;
    }
}
