package com.gtis.portal.service;

import java.util.List;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/14
 */
public interface MsgReminderService {

    /**
     * 获取所有消息提醒
     * @return
     */
    public List getAllMsgReminder() throws Exception;
}
