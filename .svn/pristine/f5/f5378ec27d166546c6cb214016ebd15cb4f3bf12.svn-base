package com.gtis.portal.service.impl;

import com.gtis.portal.dao.BaseDao;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfBusinessService;
import com.gtis.portal.service.PfResourceGroupService;
import com.gtis.portal.service.PfResourceService;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PfResourceGroupServiceImpl extends BaseServiceImpl<PfResourceGroup, String> implements PfResourceGroupService {
    @Autowired
    PfBusinessService businessService;
    @Autowired
    PfResourceService resourceService;

    public PfResourceGroup getGroupHasBs(String groupId){
        PfResourceGroup group = findById(groupId);
        if (group != null){
            if (StringUtils.isNotBlank(group.getBusinessId())){
                PfBusiness business = businessService.findById(group.getBusinessId());
                group.setBusiness(business);
            }
            if (group.getBusiness() == null){
                group.setBusiness(new PfBusiness());
            }
        }
        return group;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteGroup(String groupId,boolean delResource){
        if (StringUtils.isNotBlank(groupId)){
            if (delResource){
                //将资源一并删除，此处需要处理资源是否被菜单关联，如果被关联，则需要更新对应的菜单，将菜单的资源id设置为空
                resourceService.deleteResourceByGroupId(groupId);
            }else {
                //如果不删除包含的资源，则需要修改资源的groupid字段为空
                resourceService.clearResourceByGroupId(groupId);
            }
            deleteById(groupId);
        }
    }

    public List<PfResourceGroup> getAll(){
        QPfResourceGroup qPfResourceGroup = QPfResourceGroup.pfResourceGroup;
        JPQLQuery query = new JPAQuery(em);
        return query.from(qPfResourceGroup).orderBy(qPfResourceGroup.groupName.asc()).list(qPfResourceGroup);
    }
}
