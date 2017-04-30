/*
 * Project:  onemap
 * Module:   common
 * File:     FastjsonView.java
 * Modifier: xyang
 * Modified: 2013-05-17 12:27:39
 *
 * Copyright (c) 2013 Gtmap Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.gtis.portal.support.fastjson;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class FastjsonView extends AbstractView {

    public static final String DEFAULT_CONTENT_TYPE = "application/json; charset=utf-8";

    private String rootKey;
    private String jsonpParameterName = "callback";
    private boolean disableCaching = true;

    public void setRootKey(String rootKey) {
        this.rootKey = rootKey;
    }

    public void setJsonpParameterName(String jsonpParameterName) {
        this.jsonpParameterName = jsonpParameterName;
    }

    public void setDisableCaching(boolean disableCaching) {
        this.disableCaching = disableCaching;
    }

    public FastjsonView() {
        setContentType(DEFAULT_CONTENT_TYPE);
        setExposePathVariables(false);
    }

    @Override
    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(getContentType());
        if (this.disableCaching) {
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
            response.addDateHeader("Expires", 1L);
        }
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Helper.render(rootKey == null ? model : model.get(rootKey), request.getParameter(jsonpParameterName), response.getWriter());
    }
}
