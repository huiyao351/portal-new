package com.gtis.portal.service;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/13
 */
public interface MsgReminderProvider {

    public String getName();

    public Integer getCount() throws Exception;

    public String getMoreUrl();

    public Boolean isEnabled();
}
