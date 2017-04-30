package com.gtis.portal.dao;

import com.gtis.portal.entity.PfMessageAccept;
import com.gtis.portal.entity.PfMessageSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: jibo
 * Date: 14-5-8
 * Time: 上午7:31
 * To change this template use File | Settings | File Templates.
 */
public interface MessageAcceptDao extends JpaRepository<PfMessageAccept, String>,QueryDslPredicateExecutor<PfMessageAccept> {


}
