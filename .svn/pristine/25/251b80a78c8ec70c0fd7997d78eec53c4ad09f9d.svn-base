package com.gtis.portal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jibo
 * Date: 14-5-8
 * Time: 上午6:41
 * To change this template use File | Settings | File Templates.
 * create table PF_MESSAGE_ACCEPT
 (
 messageaccept_id     VARCHAR2(32) not null,
 messagesend_id      VARCHAR2(32),
 messagesend_username  VARCHAR2(50) not null,
 messageaccept_user    VARCHAR2(32) not null,
 messagesend_date    DATE not null,
 message_type   number(1) default 0,
 messagesend_title    VARCHAR2(32),
 messagesend_content    VARCHAR2(1000),
 messageaccept_date   DATE
 );
 alter table PF_MESSAGE_ACCEPT
 add constraint PK_PF_MESSAGE_ACCEPT primary key (messageaccept_id);
 */
@Entity
@Table(name = "PF_MESSAGE_ACCEPT")
public class PfMessageAccept implements Serializable {
    @Id
    @Column
    private String messageacceptId;

    @Column
    private String messagesendId;

    @Column
    private String messageacceptUser;  //接收人id

    @Column
    private String messagesendUsername;    //发送人姓名

    @Column
    private int messageType;    //消息类型

    @Column
    private Date messagesendDate;  //发送时间

    @Column
    private String messagesendTitle;   //发送标题

    @Column
    private String messagesendContent;   //发送内容

    @Column
    private Date messageacceptDate;   //接受时间

    public String getMessageacceptUser() {
        return messageacceptUser;
    }

    public void setMessageacceptUser(String messageacceptUser) {
        this.messageacceptUser = messageacceptUser;
    }

    public String getMessageacceptId() {
        return messageacceptId;
    }

    public void setMessageacceptId(String messageacceptId) {
        this.messageacceptId = messageacceptId;
    }

    public String getMessagesendId() {
        return messagesendId;
    }

    public void setMessagesendId(String messagesendId) {
        this.messagesendId = messagesendId;
    }

    public String getMessagesendUsername() {
        return messagesendUsername;
    }

    public void setMessagesendUsername(String messagesendUsername) {
        this.messagesendUsername = messagesendUsername;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public Date getMessagesendDate() {
        return messagesendDate;
    }

    public void setMessagesendDate(Date messagesendDate) {
        this.messagesendDate = messagesendDate;
    }

    public String getMessagesendTitle() {
        return messagesendTitle;
    }

    public void setMessagesendTitle(String messagesendTitle) {
        this.messagesendTitle = messagesendTitle;
    }

    public String getMessagesendContent() {
        return messagesendContent;
    }

    public void setMessagesendContent(String messagesendContent) {
        this.messagesendContent = messagesendContent;
    }

    public Date getMessageacceptDate() {
        return messageacceptDate;
    }

    public void setMessageacceptDate(Date messageacceptDate) {
        this.messageacceptDate = messageacceptDate;
    }
}



