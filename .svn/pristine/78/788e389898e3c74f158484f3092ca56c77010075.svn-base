package com.gtis.portal.service.impl;

import com.gtis.portal.dao.BaseDao;
import com.gtis.portal.service.BaseService;
import com.gtis.portal.util.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * java对象基础操作类
* @文件名 BaseServiceImpl.java
 * @作者 卢向伟
 * @创建日期 2015年12月12日
 * @创建时间 下午2:53:19 
 * @版本号 V 1.0
 */
@Service
public class BaseServiceImpl<E,ID> implements BaseService<E,ID> {
    @Autowired
    public BaseDao baseDao;
	@PersistenceContext
	public EntityManager em;

    private Class<E> entityClass;
    
    public BaseServiceImpl() {
    	entityClass = ClassUtils.getGenericParameter0(getClass());
    }
    
    public E findById(ID id) {
		if (id != null) {
			return baseDao.getById(entityClass, id);
		} else {
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void insert(E obj) {
		if (obj != null) {
			baseDao.save(obj);
		}
	}
    public void save(E obj) {
    	insert(obj);
	}
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void update(E obj) {
		if (obj != null) {
			baseDao.update(obj);
		}
	}
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(E obj) {
		if (obj != null) {
			baseDao.delete(obj);
		}
	}
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteById(ID id) {
		if (id != null) {
			E obj = findById(id);
			delete(obj);
		}
	}

	@Transactional
	public void deleteByIds(String ids){
		if (ids != null) {
			String[] id = ids.toString().split(",");
			for (int i = 0; i < id.length; i++) {
				baseDao.delete(entityClass,id[i]);
			}
		}
	}
}
