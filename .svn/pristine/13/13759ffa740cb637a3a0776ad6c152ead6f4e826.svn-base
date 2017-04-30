package com.gtis.portal.util;

import java.sql.Blob;

/**
 * Created by lenovo on 2016/7/4.
 */
public class BlobHelper {
    public static Blob createBlob(byte[] bytes) {
        return new SerializableBlob( new BlobImpl( bytes ) );
    }
}
