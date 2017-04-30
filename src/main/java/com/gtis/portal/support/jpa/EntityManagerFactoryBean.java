/*
 * Project:  onemap
 * Module:   common
 * File:     EntityManagerFactoryBean.java
 * Modifier: xyang
 * Modified: 2013-06-04 08:46:16
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

import com.google.common.collect.Sets;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.Set;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-6-4
 */
public class EntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean {
    private DatabasePopulator databasePopulator;
    public static final Set<String> AUTOS = Sets.newHashSet("update", "create", "create-drop");

    public void setDatabasePopulator(DatabasePopulator databasePopulator) {
        this.databasePopulator = databasePopulator;
    }

    @Override
    protected void postProcessEntityManagerFactory(EntityManagerFactory emf, PersistenceUnitInfo pui) {
        String hbm2ddlAuto = (String) getJpaPropertyMap().get(AvailableSettings.HBM2DDL_AUTO);
        if (hbm2ddlAuto != null && AUTOS.contains(hbm2ddlAuto) && databasePopulator != null) {
            DatabasePopulatorUtils.execute(this.databasePopulator, getDataSource());
        }
        super.postProcessEntityManagerFactory(emf, pui);
    }
}
