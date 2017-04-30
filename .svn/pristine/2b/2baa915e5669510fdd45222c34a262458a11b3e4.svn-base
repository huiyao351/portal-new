/*
 * Project:  onemap
 * Module:   onemap-common
 * File:     JpaCurrentSessionContext.java
 * Modifier: xyang
 * Modified: 2013-07-25 04:03:24
 *
 * Copyright (c) 2013 Gtmap Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */

package com.gtis.portal.support.jpa;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.context.spi.CurrentSessionContext;
import org.hibernate.jpa.HibernateEntityManager;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManagerFactory;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-7-25
 */
public class JpaCurrentSessionContext implements CurrentSessionContext {
    private static final long serialVersionUID = -137376108845197272L;
    private EntityManagerFactory entityManagerFactory;
    private static JpaCurrentSessionContext INSTANTE;

    public JpaCurrentSessionContext(SessionFactoryImplementor sessionFactory) {
        if (INSTANTE == null) {
            INSTANTE = this;
        }
    }

    public JpaCurrentSessionContext() {
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        INSTANTE.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Session currentSession() throws HibernateException {
        if (INSTANTE == this) {
            EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(entityManagerFactory);
            return ((HibernateEntityManager) emHolder.getEntityManager()).getSession();
        } else {
            return INSTANTE.currentSession();
        }
    }
}
