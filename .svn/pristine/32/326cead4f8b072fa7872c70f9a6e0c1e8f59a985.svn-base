package com.gtis.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.config.AppConfig;
import com.gtis.plat.service.SysWorkFlowDefineService;
import com.gtis.plat.vo.PfWorkFlowDefineVo;
import com.gtis.plat.vo.UserInfo;
import com.gtis.portal.dao.BaseDao;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.*;
import com.gtis.portal.util.RequestUtils;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class PfBusinessGroupServiceImpl extends BaseServiceImpl<PfBusinessGroup, String> implements PfBusinessGroupService {
    @Autowired
    SysWorkFlowDefineService sysWorkFlowDefineService;
    @Autowired
    PfWorkflowDefinitionService workflowDefinitionService;
    @Autowired
    PfBusinessService businessService;

    public List<PfBusinessGroup> getGroupListByName(String name){
        QPfBusinessGroup qPfBusinessGroup =QPfBusinessGroup.pfBusinessGroup;
        JPQLQuery query = new JPAQuery(em);
        return query.from(qPfBusinessGroup).where(qPfBusinessGroup.businessGroupName.eq(name)).list(qPfBusinessGroup);
    }

    public List<PfBusinessGroup> getBusinessGroupList(){
        QPfBusinessGroup qPfBusinessGroup = QPfBusinessGroup.pfBusinessGroup;
        JPQLQuery query = new JPAQuery(em);
        return query.from(qPfBusinessGroup).orderBy(qPfBusinessGroup.businessGroupNo.asc()).list(qPfBusinessGroup);
    }

    public List<Ztree> getBusinessGroupTree() {
        List<PfBusinessGroup> groupList = getBusinessGroupList();
        List<Ztree> treeList = new ArrayList<Ztree>();
        if (groupList != null && groupList.size() > 0){
            for (int i = 0; i < groupList.size(); i++) {
                Ztree tree = toZtreeByGroup(groupList.get(i));
                treeList.add(tree);
            }
        }
        return treeList;
    }

    public List<PublicVo> getBusinessRelList(String groupId){
        List<PublicVo> publicList = new ArrayList<PublicVo>();
        if (StringUtils.isNotBlank(groupId)){
            PfBusinessGroup group = findById(groupId);
            if (group != null){
                String busiIds = group.getBusinessIds();
                if (StringUtils.isNotBlank(busiIds)){
                    HashMap<String,String> relMap = JSON.parseObject(busiIds,HashMap.class);
                    for (Map.Entry<String,String> entry : relMap.entrySet()){
                        String key = entry.getKey();
                        PublicVo publicVo = new PublicVo();
                        publicVo.setValue(entry.getKey());
                        publicVo.setName(entry.getValue());
                        publicList.add(publicVo);
                    }
                }
            }
        }
        return publicList;
    }

    public List<PfBusinessGroup> getBusinessGroupListByRole(UserInfo userInfo,String rid){
        List<PfBusinessGroup> businessGroupList = new ArrayList<PfBusinessGroup>();
        List<PfBusinessGroup> groupTmpList = getBusinessGroupList();
        if (groupTmpList != null && groupTmpList.size() > 0){
            //查找符合权限的业务有哪些，之后判断哪些业务分组包含这些业务，返回符合条件的业务分组
            List<PfWorkFlowDefineVo> lstWorkFlowDefine = Lists.newArrayList();
            if (userInfo.isAdmin())
                lstWorkFlowDefine = sysWorkFlowDefineService.getWorkFlowDefineList();
            else
                lstWorkFlowDefine = sysWorkFlowDefineService.getWorkFlowDefineListByRole(userInfo.getRoleIds(), rid, null);
            LinkedHashMap<String,List<PfWorkFlowDefineVo>> workflowDefineMap = new LinkedHashMap<String, List<PfWorkFlowDefineVo>>();
            if (lstWorkFlowDefine != null && lstWorkFlowDefine.size() > 0){
                for (int i = 0; i < lstWorkFlowDefine.size(); i++) {
                    PfWorkFlowDefineVo wfd = lstWorkFlowDefine.get(i);
                    String categoryKey = wfd.getBusinessId();
                    String createUrl = wfd.getCreateUrl() == null ? "" : wfd.getCreateUrl();
                    wfd.setCreateUrl(AppConfig.getPlaceholderValue(createUrl));

                    if(workflowDefineMap.get(categoryKey) != null){
                        workflowDefineMap.get(categoryKey).add(wfd);
                    }else {
                        List<PfWorkFlowDefineVo> wfdList = Lists.newArrayList();
                        wfdList.add(wfd);
                        workflowDefineMap.put(categoryKey, wfdList);
                    }
                }
            }
            for (int i = 0; i < groupTmpList.size(); i++) {
                String busiIds = groupTmpList.get(i).getBusinessIds();
                if (StringUtils.isNotBlank(busiIds)){
                    LinkedHashMap<String,List<PfWorkFlowDefineVo>> tmpWdMap = new LinkedHashMap<String, List<PfWorkFlowDefineVo>>();
                    LinkedHashMap<String,String> relMap = JSON.parseObject(busiIds,LinkedHashMap.class);
                    for (Map.Entry<String,String> entry : relMap.entrySet()){
                        String key = entry.getKey();
                        if (workflowDefineMap.containsKey(key)){
                            tmpWdMap.put(workflowDefineMap.get(key).get(0).getBusinessVo().getBusinessName()+"@"+key, workflowDefineMap.get(key));
                        }
                    }

                    if (tmpWdMap != null && tmpWdMap.keySet().size() > 0){
                        PfBusinessGroup group = groupTmpList.get(i);
                        group.setWorkflowDefineMap(tmpWdMap);
                        businessGroupList.add(group);
                    }
                }
            }
        }
//        System.out.println(JSON.toJSONString(businessGroupList));
        return businessGroupList;
    }

    @Transactional
    public void updateDetail(PfBusinessGroup businessGroup){
        if (businessGroup != null && StringUtils.isNotBlank(businessGroup.getBusinessGroupId())){
            PfBusinessGroup tmpBs = findById(businessGroup.getBusinessGroupId());
            if (tmpBs != null){
                tmpBs.setBusinessGroupName(businessGroup.getBusinessGroupName());
                tmpBs.setBusinessGroupNo(businessGroup.getBusinessGroupNo());
                baseDao.update(tmpBs);
            }
        }
    }
    @Transactional
    public void updateBusinessIds(String groupId,List<ZtreeChanged> changeList){
        if (changeList != null && changeList.size() > 0 && StringUtils.isNotBlank(groupId)){
            PfBusinessGroup group = findById(groupId);
            if (group != null){
                LinkedHashMap busiMap = new LinkedHashMap();
                String busiIds = group.getBusinessIds();
                if (StringUtils.isNotBlank(busiIds)){
                    busiMap = JSON.parseObject(busiIds,LinkedHashMap.class);
                }
                for (int i = 0; i < changeList.size(); i++) {
                    ZtreeChanged change = changeList.get(i);
                    if (change.isChecked()){
                        //保存新的关系
                        busiMap.put(change.getId(),change.getName());
                    }else {
                        //删除原有关系
                        busiMap.remove(change.getId());
                    }
                }
                group.setBusinessIds(JSON.toJSONString(busiMap));
                baseDao.update(group);
            }
        }
    }

    private Ztree toZtreeByGroup(PfBusinessGroup group) {
        Ztree tree = new Ztree();
        tree.setId(group.getBusinessGroupId());
        tree.setName(group.getBusinessGroupName());
        tree.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/folder_pictures.png"));
        return tree;
    }
}
