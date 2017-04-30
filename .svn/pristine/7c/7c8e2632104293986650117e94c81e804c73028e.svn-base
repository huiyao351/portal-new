package com.gtis.portal.service.impl;

import com.gtis.config.AppConfig;
import com.gtis.portal.dao.BaseDao;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Menu;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.*;
import com.gtis.portal.util.RequestUtils;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class PfResourceServiceImpl extends BaseServiceImpl<PfResource, String> implements PfResourceService {
    @Autowired
    PfBusinessService businessService;
    @Autowired
    PfMenuService menuService;
    @Autowired
    PfResourceGroupService resourceGroupService;
    @Autowired
    PfAuthorizeService authorizeService;
    @Autowired
    PfInstanceAuthorizeService instanceAuthorizeService;

    /**
     * 将业务信息放进资源中统一展示
     * @param resouceId
     * @return
     */
    public PfResource getResourceHasBs(String resouceId){
        PfResource resource = findById(resouceId);
        if (resource != null){
            if (StringUtils.isNotBlank(resource.getBusinessId())){
                PfBusiness business = businessService.findById(resource.getBusinessId());
                resource.setBusiness(business);
            }
            if (resource.getBusiness() == null){
                resource.setBusiness(new PfBusiness());
            }
        }
        return resource;
    }

    /**
     * 根据资源地址进行匹配查找
     * @param url
     * @return
     */
    public List<PfResource> getResourceListByUrl(String url){
        if (StringUtils.isNotBlank(url)){
            QPfResource qPfResource = QPfResource.pfResource;
            JPQLQuery query = new JPAQuery(em);
            return query.from(qPfResource).where(qPfResource.resourceUrl.like("%"+url+"%")).orderBy(qPfResource.resourceNo.asc()).list(qPfResource);
        }
        return null;
    }

    public List<PfResource> getAll(){
        QPfResource qPfResource = QPfResource.pfResource;
        JPQLQuery query = new JPAQuery(em);
        return query.from(qPfResource).orderBy(qPfResource.resourceNo.asc()).list(qPfResource);
    }

    public List<PfResource> getResourceListByGroupId(String groupId){
        List<PfResource> resourceList = null;
        if (StringUtils.isNotBlank(groupId)){
//            QPfMenu qPfMenu = QPfMenu.pfMenu;
//            JPQLQuery query = new JPAQuery(em);
//            if (StringUtils.isBlank(roles))
//                menuList = query.from(qPfMenu).where(qPfMenu.subsystems.any().subsystemName.eq(subsystem)).orderBy(qPfMenu.menuOrder.asc()).list(qPfMenu);
//            String jpql = "select t from PfResource t where t.groupId=?0";
//            baseDao.getByJpql(jpql,groupId);
            QPfResource qPfResource = QPfResource.pfResource;
            JPQLQuery query = new JPAQuery(em);
            resourceList = query.from(qPfResource).where(qPfResource.groupId.eq(groupId)).orderBy(qPfResource.resourceNo.asc()).list(qPfResource);
        }
        return resourceList;
    }

    /**
     * 根据资源id集合，返回对应的资源集合
     * @param resIdList
     * @return
     */
    public List<PfResource> getResListByResIds(List<String> resIdList){
        List<PfResource> resourceList = null;
        if (resIdList != null && resIdList.size() > 0){
            QPfResource qPfResource = QPfResource.pfResource;
            JPQLQuery query = new JPAQuery(em);
            String[] idArs = (String[])resIdList.toArray(new String[resIdList.size()]);
            resourceList = query.from(qPfResource).where(qPfResource.resourceId.in(idArs)).orderBy(qPfResource.resourceNo.asc()).list(qPfResource);
        }
        return resourceList;
    }

    /**
     * 资源list转为资源map
     * @param resourceList
     * @return
     */
    public HashMap<String,PfResource> toMapByResList(List<PfResource> resourceList){
        HashMap<String,PfResource> resMap = new HashMap<String, PfResource>();
        if (resourceList != null && resourceList.size() > 0){
            for (int i = 0; i < resourceList.size(); i++) {
                String key = resourceList.get(i).getResourceId();
                resMap.put(resourceList.get(i).getResourceId(),resourceList.get(i));
            }
        }
        return resMap;
    }

    /**
     * 根据资源id集合，返回对应的资源集合的map
     * @param resIdList
     * @return
     */
    public HashMap<String,PfResource> toMapByResIdList(List<String> resIdList){
        List<PfResource> resourceList = getResListByResIds(resIdList);
        return toMapByResList(resourceList);
    }

    /**
     * 根据资源id集合，获取对应资源url地址的参数，返回以id为key，参数为value的map对象
     * 1、获取资源集合
     * 2、遍历资源集合，处理有url地址的资源，并将资源url地址的参数字符串提取出来作为value
     * @param resIdList
     * @return
     */
    public HashMap<String,String> toUrlParamMapByResIdList(List<String> resIdList){
        HashMap<String,String> paramsMap = new HashMap<String, String>();
        //根据资源id集合查找资源集合
        List<PfResource> resourceList = getResListByResIds(resIdList);
        if (resourceList != null && resourceList.size() > 0){
            for (int i = 0; i < resourceList.size(); i++) {
                String url = resourceList.get(i).getResourceUrl();
                if (StringUtils.indexOf(url,"?") > -1){
                    String params = StringUtils.substringAfter(url,"?");
                    if (StringUtils.isNotBlank(params)){
                        paramsMap.put(resourceList.get(i).getResourceId(),params);
                    }
                }
            }
        }
        return paramsMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteResource(String resourceId){
        if (StringUtils.isNotBlank(resourceId)){
            //此处需要处理资源是否被菜单关联，如果被关联，则需要更新对应的菜单，将菜单的资源id设置为空
            //将原有菜单关联置空
            menuService.resetResouceRel(resourceId);

            //删除主菜单授权中关于资源的功能分区配置
            authorizeService.deleteAuthorizeListByResource(resourceId);

            //删除实例权限中配置的资源信息
            instanceAuthorizeService.deleteAuthorizeListByResource(resourceId);
            deleteById(resourceId);
        }
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteResourceByGroupId(String groupId){
        if (StringUtils.isNotBlank(groupId)){
            //将资源一并删除，此处需要处理资源是否被菜单关联，如果被关联，则需要更新对应的菜单，将菜单的资源id设置为空
            List<PfResource> resourceList = getResourceListByGroupId(groupId);
            if (resourceList != null && resourceList.size() > 0){
                for (int i=0;i<resourceList.size();i++){
                    deleteResource(resourceList.get(i).getResourceId());
                }
            }
        }
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void clearResourceByGroupId(String groupId){
        if (StringUtils.isNotBlank(groupId)){
            //将资源一并删除，此处需要处理资源是否被菜单关联，如果被关联，则需要更新对应的菜单，将菜单的资源id设置为空
            List<PfResource> resourceList = getResourceListByGroupId(groupId);
            if (resourceList != null && resourceList.size() > 0){
                for (int i=0;i<resourceList.size();i++){
                    resourceList.get(i).setGroupId(null);
                    update(resourceList.get(i));
                }
            }
        }
    }


    @Override
    public Ztree getAllResourceTree(String hascheck) {
        boolean nocheck = true;
        if (StringUtils.isNotBlank(hascheck) && StringUtils.equalsIgnoreCase(hascheck,"true")){
            nocheck = false;
        }
        //获取业务列表、资源分组、资源表记录
        //之后根据业务、资源分组、资源表关系对数据进行整理
        List<Ztree> treeList = new ArrayList<Ztree>();
        HashMap<String, Ztree> treeMap = new LinkedHashMap<String, Ztree>();
        List<PfBusiness> businessList = businessService.getAll();
        if (businessList != null && businessList.size() > 0){
            for (PfBusiness business : businessList) {
                Ztree tree = toZtreeByBusiness(business);
                treeMap.put(tree.getId(), tree);
            }
            List<PfResourceGroup> resourceGroupList = resourceGroupService.getAll();
            if (resourceGroupList != null && resourceGroupList.size() > 0){
                for (PfResourceGroup resourceGroup : resourceGroupList) {
                    Ztree tree = toZtreeByResourceGroup(resourceGroup,nocheck);
                    treeMap.put(tree.getId(), tree);
                }
                List<PfResource> resourceList = getAll();
                if (resourceList != null && resourceList.size() > 0){
                    for (PfResource resource : resourceList) {
                        Ztree tree = toZtreeByResource(resource);
                        treeMap.put(tree.getId(), tree);
                    }
                }
            }
        }
        Ztree root = new Ztree();

        Ztree firstNode = new Ztree();
        firstNode.setId("treeroot");
        firstNode.setName("系统资源");
        firstNode.setNocheck(true);
        firstNode.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/folder_blank.png"));
        treeMap.put(firstNode.getId(),firstNode);

        for (Ztree tree : treeMap.values()) {
            if (tree.getPid() != null) {
                Ztree ztree = treeMap.get(tree.getPid());
                if (ztree != null) {
                    ztree.addChild(tree);
                }
            }else {
                tree.setOpen(true);
                root = tree;
            }
        }
        return root;
    }
    private Ztree toZtreeByBusiness(PfBusiness business) {
        Ztree tree = new Ztree();
        tree.setId(business.getBusinessId());
        tree.setName(business.getBusinessName());
        tree.setPid("treeroot");
        tree.setNocheck(true);
        tree.setGroup(false);
        tree.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url")+"/static/images/folder_blank.png"));
        return tree;
    }
    private Ztree toZtreeByResourceGroup(PfResourceGroup resourceGroup,boolean nocheck) {
        Ztree tree = new Ztree();
        tree.setId(resourceGroup.getGroupId());
        tree.setName(resourceGroup.getGroupName());
        tree.setPid(resourceGroup.getBusinessId());
        tree.setNocheck(nocheck);
        tree.setGroup(true);
        tree.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url")+"/static/images/folder.gif"));
        return tree;
    }
    private Ztree toZtreeByResource(PfResource resource) {
        Ztree tree = new Ztree();
        tree.setId(resource.getResourceId());
        tree.setName(resource.getResourceName());
//        tree.setUrl(resource.getResourceUrl());
        if (StringUtils.isBlank(resource.getGroupId())){
            tree.setPid(resource.getBusinessId());
        }else {
            tree.setPid(resource.getGroupId());
        }
        tree.setGroup(false);
        return tree;
    }

}
