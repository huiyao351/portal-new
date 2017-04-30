/*
 * Project:  onemap
 * Module:   onemap-common
 * File:     JSONType.java
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.ObjectUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.internal.util.SerializationHelper;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;
import org.jboss.logging.Logger;
import org.springframework.util.ReflectionUtils;

import javax.persistence.Column;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 12-9-7
 */
public class JSONType implements UserType, DynamicParameterizedType, Serializable {
    public static final String TYPE = "cn.gtmap.onemap.core.support.hibernate.JSONType";
    public static final String CLASS_NAME = "class";
    private static final CoreMessageLogger LOG = Logger.getMessageLogger(CoreMessageLogger.class, JSONType.class.getName());
    private static final long serialVersionUID = 9015678551160115883L;
    private int sqlType = Types.VARCHAR;
    private Class clazz = Object.class;
    private Type type = clazz;

    @Override
    public int[] sqlTypes() {
        return new int[]{sqlType};
    }

    @Override
    public Class returnedClass() {
        return clazz;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Object object = rs.getObject(names[0]);
        if (rs.wasNull()) {
            if (LOG.isTraceEnabled()) LOG.tracev("Returning null as column {0}", names[0]);
            return null;
        } else {
            return JSON.parseObject((String) object, type);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            if (LOG.isTraceEnabled()) LOG.tracev("Binding null to parameter: {0}", index);
            st.setNull(index, sqlType);
        } else {
            st.setObject(index, JSON.toJSONString(value), sqlType);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value instanceof JSONObject) {
            return ((JSONObject) value).clone();
        } else if (value instanceof Cloneable) {
            return ObjectUtils.clone(value);
        } else if (value instanceof Serializable) {
            return SerializationHelper.clone((Serializable) value);
        } else {
            return value;
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    @SuppressWarnings("unchecked")
    public void setParameterValues(Properties parameters) {
        try {
            clazz = ReflectHelper.classForName(parameters.getProperty(DynamicParameterizedType.ENTITY));
            Field field = ReflectionUtils.findField(clazz, parameters.getProperty(DynamicParameterizedType.PROPERTY));
            type = field.getGenericType();
            parseSqlType(field.getAnnotations());
            return;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        final DynamicParameterizedType.ParameterType reader = (DynamicParameterizedType.ParameterType) parameters.get(DynamicParameterizedType.PARAMETER_TYPE);
        if (reader != null) {
            clazz = reader.getReturnedClass();
            parseSqlType(reader.getAnnotationsMethod());
        } else {
            try {
                clazz = ReflectHelper.classForName((String) parameters.get(CLASS_NAME));
            } catch (ClassNotFoundException exception) {
                throw new HibernateException("class not found", exception);
            }
        }
    }

    private void parseSqlType(Annotation[] anns) {
        for (Annotation an : anns) {
            if (an instanceof Column) {
                int length = ((Column) an).length();
                if (length > 4000) {
                    sqlType = Types.CLOB;
                }
                break;
            }
        }
    }
}
