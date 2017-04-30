package com.gtis.portal.properties;

import java.util.ArrayList;
import java.util.List;

/**
 * @文件说明
 * @作者 deery
 * @创建日期 10:43
 * @版本号 V 1.0
 */
public class PropertiesContext {
    private List commentOrEntrys = new ArrayList();

    public List getCommentOrEntrys() {
        return commentOrEntrys;
    }

    public void addCommentLine(String line) {
        commentOrEntrys.add(line);
    }

    public void putOrUpdate(PropertyEntry pe) {
        remove(pe.getKey());
        commentOrEntrys.add(pe);
    }

    public void putOrUpdate(String key, String value, String line) {
        PropertyEntry pe = new PropertyEntry(key, value, line);
        remove(key);
        commentOrEntrys.add(pe);
    }

    public void putOrUpdate(String key, String value) {
        PropertyEntry pe = new PropertyEntry(key, value);
        int index = remove(key);
        commentOrEntrys.add(index,pe);
    }

    public int remove(String key) {
        for (int index = 0; index < commentOrEntrys.size(); index++) {
            Object obj = commentOrEntrys.get(index);
            if (obj instanceof PropertyEntry) {
                if (obj != null) {
                    if (key.equals(((PropertyEntry) obj).getKey())) {
                        commentOrEntrys.remove(obj);
                        return index;
                    }
                }
            }
        }
        return commentOrEntrys.size();
    }
}
