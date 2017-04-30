/*
 * Project:  onemap
 * Module:   common
 * File:     ConfigurableObjectWrapper.java
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
package com.gtis.portal.support.freemarker;

import com.google.common.collect.Maps;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-9-8
 */
public class ConfigurableObjectWrapper extends DefaultObjectWrapper {

    private Map<Class, Class<TemplateModel>> classMapping = Collections.emptyMap();
    private Map<Class, Constructor> cache = Maps.newHashMap();

    public void setClassMapping(Map<Class, Class<TemplateModel>> classMapping) {
        this.classMapping = classMapping;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected TemplateModel handleUnknownType(Object obj) throws TemplateModelException {
        Class clazz = obj.getClass();
        Constructor constructor = null;
        if (cache.containsKey(clazz)) {
            constructor = cache.get(clazz);
        } else {
            for (Map.Entry<Class, Class<TemplateModel>> entry : classMapping.entrySet()) {
                if (entry.getKey().isAssignableFrom(clazz)) {
                    constructor = entry.getValue().getConstructors()[0];
                    break;
                }
            }
            cache.put(clazz, constructor);
        }
        if (constructor != null) {
            try {
                return (TemplateModel) constructor.newInstance(obj, this);
            } catch (Exception ignored) {
            }
        }
        return super.handleUnknownType(obj);
    }
}
