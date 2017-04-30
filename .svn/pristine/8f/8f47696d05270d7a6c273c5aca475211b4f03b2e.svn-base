package com.gtis.portal.support.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.*;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.CollectionType;
import org.hibernate.type.ManyToOneType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 当直接添加或删除ManyToOne对象时,OneToMany端的L2 cache不会被清除,造成缓存不同步,此类就是用来主动清除这些缓存
 * <p/>
 *
 * @author <a href="mailto:oxsean@gmail.com">sean yang</a>
 * @version V1.0, 13-7-5
 */
public final class OneToManyCacheCleaner implements PreInsertEventListener, PreDeleteEventListener, InitializingBean {
    private static final long serialVersionUID = 8106108888493751911L;
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        clean(event.getSession(), event.getEntity(), event.getPersister());
        return false;
    }

    @Override
    public boolean onPreDelete(PreDeleteEvent event) {
        clean(event.getSession(), event.getEntity(), event.getPersister());
        return false;
    }

    private void clean(EventSource session, Object entity, EntityPersister persister) {
        Type[] propertyTypes = persister.getPropertyTypes();
        for (int i = 0, len = propertyTypes.length; i < len; i++) {
            Type type = propertyTypes[i];
            if (type instanceof ManyToOneType) {
                Object parent = persister.getPropertyValue(entity, i);
                if (parent == null) {
                    continue;
                }
                EntityPersister parentPersister = session.getEntityPersister(((ManyToOneType) type).getAssociatedEntityName(), parent);
                for (Type type1 : parentPersister.getPropertyTypes()) {
                    if (type1 instanceof CollectionType) {
                        CollectionType collectionType = (CollectionType) type1;
                        CollectionPersister collectionPersister = session.getFactory().getCollectionPersister(collectionType.getRole());
                        if (persister.getEntityName().equals(collectionPersister.getElementType().getName()) && collectionPersister.hasCache()) {
                            session.getFactory().getCache().evictCollection(collectionType.getRole(), parentPersister.getIdentifier(parent, session));
                            break;
                        }
                    }
                }
            }
        }
    }


    @Override
    public void afterPropertiesSet() {
        EventListenerRegistry registry = ((SessionFactoryImplementor) sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(this);
        registry.getEventListenerGroup(EventType.PRE_DELETE).appendListener(this);
    }
}
