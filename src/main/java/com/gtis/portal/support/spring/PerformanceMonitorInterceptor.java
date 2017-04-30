/*
 * Project:  onemap
 * Module:   common
 * File:     PerformanceMonitorInterceptor.java
 * Modifier: xyang
 * Modified: 2013-06-05 09:54:16
 *
 * Copyright (c) 2013 Gtmap Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */

package com.gtis.portal.support.spring;

import com.gtis.portal.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-6-5
 */
public class PerformanceMonitorInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(PerformanceMonitorInterceptor.class);
    private final String KEY = "pmi_" + hashCode() + "_";
    private String[] excludes;

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (LOG.isDebugEnabled()) {
            if (excludes != null && RequestUtils.matchAny(request, RequestUtils.URL_PATH_HELPER, RequestUtils.PATH_MATCHER, excludes)) {
                return true;
            }
            StopWatch stopWatch = new StopWatch("Uri:" + request.getRequestURI());
            request.setAttribute(getKey(request), stopWatch);
            stopWatch.start();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (LOG.isDebugEnabled()) {
            StopWatch stopWatch = (StopWatch) request.getAttribute(getKey(request));
            if (stopWatch != null) {
                stopWatch.stop();
                LOG.debug(stopWatch.shortSummary());
            }
        }
    }


    private String getKey(HttpServletRequest request) {
        return KEY + request.getRequestURI();
    }
}
