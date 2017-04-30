package com.gtis.portal.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gtis.portal.service.MsgReminderProvider;
import com.gtis.portal.service.MsgReminderService;

import java.util.List;
import java.util.Map;

/**
 * 信息提醒服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/14
 */
public class MsgReminderServiceImpl implements MsgReminderService {

    private List<MsgReminderProvider> msgReminderProviders;
    /**
     * 获取所有消息提醒
     *
     * @return
     */
    @Override
    public List getAllMsgReminder() throws Exception {
        List<Map> reminderList = Lists.newArrayList();
        for(MsgReminderProvider msgReminderProvider:msgReminderProviders){
            if(msgReminderProvider.isEnabled()){
                Map reminderMap = Maps.newHashMap();
                reminderMap.put("name",msgReminderProvider.getName());
                reminderMap.put("count",msgReminderProvider.getCount());
                reminderMap.put("moreUrl",msgReminderProvider.getMoreUrl());
                reminderList.add(reminderMap);
            }
        }
        return reminderList;
    }

    public List<MsgReminderProvider> getMsgReminderProviders() {
        return msgReminderProviders;
    }

    public void setMsgReminderProviders(List<MsgReminderProvider> msgReminderProviders) {
        this.msgReminderProviders = msgReminderProviders;
    }
}
