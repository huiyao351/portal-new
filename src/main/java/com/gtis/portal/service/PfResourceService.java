package com.gtis.portal.service;


import com.gtis.portal.entity.PfMenu;
import com.gtis.portal.entity.PfResource;
import com.gtis.portal.model.Menu;
import com.gtis.portal.model.Ztree;

import java.util.HashMap;
import java.util.List;

public interface PfResourceService extends BaseService<PfResource, String> {
    public PfResource getResourceHasBs(String resouceId);

    public List<PfResource> getResourceListByUrl(String url);

    public List<PfResource> getResourceListByGroupId(String groupId);

    /**
     * 根据资源id集合，返回对应的资源集合
     * @param resIdList
     * @return
     */
    public List<PfResource> getResListByResIds(List<String> resIdList);
    public HashMap<String,PfResource> toMapByResList(List<PfResource> resourceList);
    public HashMap<String,PfResource> toMapByResIdList(List<String> resIdList);

    /**
     * 根据资源id集合，获取对应资源url地址的参数，返回以id为key，参数为value的map对象
     * 1、获取资源集合
     * 2、遍历资源集合，处理有url地址的资源，并将资源url地址的参数字符串提取出来作为value
     * @param resIdList
     * @return
     */
    public HashMap<String,String> toUrlParamMapByResIdList(List<String> resIdList);

    public void deleteResource(String resourceId);
    public void deleteResourceByGroupId(String groupId);
    public void clearResourceByGroupId(String groupId);
    public Ztree getAllResourceTree(String nocheck);
}
