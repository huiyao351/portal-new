/*
 * Project:  onemap
 * Module:   common
 * File:     Helper.java
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

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.Writer;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-5-9
 */
public class Helper {
    public static void render(Object obj, String jsonpCallback, Writer writer) throws IOException {
        SerializeWriter out = new SerializeWriter();
        out.config(SerializerFeature.DisableCircularReferenceDetect, true);
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.write(obj);
        try {
            if (StringUtils.isEmpty(jsonpCallback)) {
                out.writeTo(writer);
            } else {
                writer.write(jsonpCallback + "(");
                out.writeTo(writer);
                writer.write(");");
            }
            writer.flush();
        } finally {
            out.close();
        }
    }
}
