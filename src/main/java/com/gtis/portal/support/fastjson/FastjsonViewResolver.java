/*
 * Project:  onemap
 * Module:   common
 * File:     FastjsonViewResolver.java
 * Modifier: xyang
 * Modified: 2013-05-17 12:14:48
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

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-5-8
 */
public class FastjsonViewResolver implements ViewResolver {
    private View view = new FastjsonView();

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return view;
    }
}
