package com.gtis.portal.entity;

import javax.persistence.*;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jibo
 * Date: 14-5-8
 * Time: 上午6:41
 create table PF_MESSAGE_SEND
 (
 messagesend_id     VARCHAR2(32) not null,
 messagesend_user  VARCHAR2(32) not null,
 messageaccept_username    VARCHAR2(2000) not null,    //最多发送500人
 messagesend_date    DATE not null,
 messagesend_title    VARCHAR2(32),
 messagesend_content    VARCHAR2(1000)
 );
 alter table PF_MESSAGE_ACCEPT
 add constraint PK_PF_MESSAGE_SEND primary key (messagesend_id);
 */
@Entity
@Table(name = "PF_MESSAGE_SEND")
public class PfMessageSend implements Serializable {
    @Id
    @Column
    private String messagesendId;

    @OneToOne
    @JoinColumn(name="MESSAGESEND_USER")
    private PfUser messagesendUser;

    @Column
    private Date messagesendDate;

    @Column
    private String messagesendTitle;

    @Column
    private String messageacceptUsername;

    @Column
    private String messagesendContent;

    public String getMessagesendId() {
        return messagesendId;
    }

    public void setMessagesendId(String messagesendId) {
        this.messagesendId = messagesendId;
    }

    public PfUser getMessagesendUser() {
        return messagesendUser;
    }

    public void setMessagesendUser(PfUser messagesendUser) {
        this.messagesendUser = messagesendUser;
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

    public String getMessageacceptUsername() {
        return messageacceptUsername;
    }

    public void setMessageacceptUsername(String messageacceptUsername) {
        this.messageacceptUsername = messageacceptUsername;
    }

    public String getMessagesendContent() {
        return messagesendContent;
    }

    public void setMessagesendContent(String messagesendContent) {
        this.messagesendContent = messagesendContent;
    }
}



