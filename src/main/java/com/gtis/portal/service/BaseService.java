package com.gtis.portal.service;

/**
 * Created with IntelliJ IDEA.
 * User: deery
 * Date: 13-10-23
 * Time: 上午5:42
 * To change this template use File | Settings | File Templates.
 */
public interface BaseService<E,ID> {
    
	public E findById(ID id);

	public void insert(E obj);
	public void save(E obj);
	public void update(E obj);
	public void delete(E obj);
	public void deleteById(ID id);
	public void deleteByIds(String ids);
}
