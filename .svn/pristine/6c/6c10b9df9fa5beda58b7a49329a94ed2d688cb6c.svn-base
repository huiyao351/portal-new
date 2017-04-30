package com.gtis.portal.service;

import com.gtis.portal.entity.PfMessageAccept;
import com.gtis.portal.entity.PfMessageSend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jibo
 * Date: 14-5-9
 * Time: 上午6:02
 * To change this template use File | Settings | File Templates.
 */
public interface pfMessageService {
    /**
     * 查询发送信息列表
     * @param userId
     * @param title
     * @param beginDate
     * @param endDate
     * @param page
     * @return
     */
    public Page<PfMessageSend> queryMessageSendList(String userId,String title,Date beginDate,Date endDate,Pageable page);


    public Page<PfMessageAccept> queryMessageAcceptList(String userId,String title,Date beginDate,Date endDate,Pageable page);


    public PfMessageSend getMessageSend(String messageId);


    public PfMessageAccept getMessageAccept(String messageId);
}
