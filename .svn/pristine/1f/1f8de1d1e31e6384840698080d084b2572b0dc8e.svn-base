/*
 * Project:  onemap
 * Module:   onemap-common
 * File:     PrefixNamingStrategy.java
 * Modifier: xyang
 * Modified: 2013-06-21 21:49
 *
 * Copyright (c) 2013 Gtmap Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.gtis.portal.support.hibernate;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 11-12-24
 */
public class PrefixNamingStrategy extends ImprovedNamingStrategy {
    private static final long serialVersionUID = -5422825505217904901L;
    private String prefix = "t_";

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String classToTableName(String tableName) {
        return prefix + super.classToTableName(tableName);
    }

    @Override
    public String tableName(String tableName) {
        if (!tableName.startsWith(prefix)) {
            tableName = prefix + tableName;
        }
        return super.tableName(tableName);
    }
}
