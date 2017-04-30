package com.gtis.portal.service.impl;

import com.gtis.portal.dao.MessageAcceptDao;
import com.gtis.portal.dao.MessageSendDao;
import com.gtis.portal.entity.PfMessageAccept;
import com.gtis.portal.entity.PfMessageSend;
import com.gtis.portal.service.pfMessageService;
import com.mysema.query.types.expr.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.gtis.portal.entity.QPfMessageSend.pfMessageSend;
import static com.gtis.portal.entity.QPfMessageAccept.pfMessageAccept;

/**
 * Created with IntelliJ IDEA.
 * User: jibo
 * Date: 14-5-8
 * Time: 上午7:35
 * To change this template use File | Settings | File Templates.
 */
@Service
public class pfMessageServiceImpl implements pfMessageService {
    @Autowired
    MessageSendDao messageSendDao;

    @Autowired
    MessageAcceptDao messageAcceptDao;

    public Page<PfMessageSend> queryMessageSendList(String userId,String title,Date beginDate,Date endDate,@PageableDefault(size = 10) Pageable page) {
        BooleanExpression expression=null;
        if (StringUtils.isNotBlank(userId))
            expression=pfMessageSend.messagesendUser.userId.eq(userId);

        if (StringUtils.isNotBlank(title))
            expression=expression==null ?pfMessageSend.messagesendTitle.like(title):expression.or(pfMessageSend.messagesendTitle.like(title));

        return messageSendDao.findAll(expression,page);
    }

    public Page<PfMessageAccept> queryMessageAcceptList(String userId,String title,Date beginDate,Date endDate,Pageable page) {
        BooleanExpression expression=null;
        if (StringUtils.isNotBlank(userId))
            expression=pfMessageAccept.messageacceptUser.eq(userId);

        if (StringUtils.isNotBlank(title))
            expression=expression==null ?pfMessageAccept.messagesendTitle.like(title):expression.or(pfMessageAccept.messagesendTitle.like(title));

        return messageAcceptDao.findAll(expression,page);
    }

    public PfMessageSend getMessageSend(String messageId){
        return   messageSendDao.findOne(messageId);
    }

    public PfMessageAccept getMessageAccept(String messageId){
        return   messageAcceptDao.findOne(messageId);
    }
}
