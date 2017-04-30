package com.gtis.portal.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gtis.config.AppConfig;
import com.gtis.plat.service.SysWorkFlowDefineService;
import com.gtis.plat.vo.PfWorkFlowDefineVo;
import com.gtis.plat.vo.UserInfo;
import com.gtis.portal.dao.BaseDao;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfBusinessService;
import com.gtis.portal.service.PfResourceGroupService;
import com.gtis.portal.service.PfResourceService;
import com.gtis.portal.service.PfWorkflowDefinitionService;
import com.gtis.portal.util.RequestUtils;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class PfWorkflowDefinitionServiceImpl implements PfWorkflowDefinitionService {
    @Autowired
    public BaseDao baseDao;
    @PersistenceContext
    public EntityManager em;

    @Autowired
    PfBusinessService businessService;
    @Autowired
    PfResourceService resourceService;
    @Autowired
    SysWorkFlowDefineService sysWorkFlowDefineService;

    public PfWorkflowDefinition findById(String wfdId){
        if (StringUtils.isNotBlank(wfdId)){
            PfWorkflowDefinition wfd = baseDao.getById(PfWorkflowDefinition.class,wfdId);
            if (wfd != null){
                PfBusiness business = businessService.findById(wfd.getBusinessId());
                wfd.setBusiness(business);
            }
            return wfd;
        }
        return null;
    }

    @Transactional
    public void update(PfWorkflowDefinition pfWorkflowDefinition){
        if (pfWorkflowDefinition != null && StringUtils.isNotBlank(pfWorkflowDefinition.getWorkflowDefinitionId())){
            PfWorkflowDefinition tmpWfd = findById(pfWorkflowDefinition.getWorkflowDefinitionId());
            if (tmpWfd != null){
//                tmpWfd.setRegionCode(pfWorkflowDefinition.getRegionCode());
//                tmpWfd.setWorkflowCode(pfWorkflowDefinition.getWorkflowCode());
//                tmpWfd.setWorkflowName(pfWorkflowDefinition.getWorkflowName());
//                tmpWfd.setWorkflowDefinitionNo(pfWorkflowDefinition.getWorkflowDefinitionNo());
//                tmpWfd.setWorkflowVersion(pfWorkflowDefinition.getWorkflowVersion());
//                tmpWfd.setPriority(pfWorkflowDefinition.getPriority());
//                tmpWfd.setRemark(pfWorkflowDefinition.getRemark());
//                tmpWfd.setCreateUrl(pfWorkflowDefinition.getCreateUrl());
//                tmpWfd.setCreateHeight(pfWorkflowDefinition.getCreateHeight());
//                tmpWfd.setCreateWidth(pfWorkflowDefinition.getCreateWidth());
//                tmpWfd.setIsValid(pfWorkflowDefinition.getIsValid());
//                tmpWfd.setIsMonitor(pfWorkflowDefinition.getIsMonitor());
////                tmpWfd.setGroupId(pfWorkflowDefinition.getGroupId());
//                tmpWfd.setTimeLimit(pfWorkflowDefinition.getTimeLimit());
//                baseDao.update(tmpWfd);
                String jqpl = "update PfWorkflowDefinition t set " +
                        " t.regionCode='"+pfWorkflowDefinition.getRegionCode()+"', " +
                        " t.workflowCode='"+pfWorkflowDefinition.getWorkflowCode()+"', " +
                        " t.workflowName='"+pfWorkflowDefinition.getWorkflowName()+"', " +
                        " t.workflowDefinitionNo='"+pfWorkflowDefinition.getWorkflowDefinitionNo()+"', " +
                        " t.workflowVersion='"+pfWorkflowDefinition.getWorkflowVersion()+"', " +
                        " t.priority="+pfWorkflowDefinition.getPriority()+", " +
                        " t.remark='"+pfWorkflowDefinition.getRemark()+"', " +
                        " t.createUrl='"+pfWorkflowDefinition.getCreateUrl()+"', " +
                        " t.createHeight="+pfWorkflowDefinition.getCreateHeight()+", " +
                        " t.createWidth="+pfWorkflowDefinition.getCreateWidth()+", " +
                        " t.isValid="+pfWorkflowDefinition.getIsValid()+", " +
                        " t.isMonitor="+pfWorkflowDefinition.getIsMonitor()+", " +
                        " t.timeLimit='"+pfWorkflowDefinition.getTimeLimit()+"' " +
                        " where t.workflowDefinitionId='"+pfWorkflowDefinition.getWorkflowDefinitionId()+"'";
                baseDao.executeJpql(jqpl);
            }
        }
    }

    public List<PfWorkflowDefinition> getAll(){
        List<PfWorkflowDefinition> wdList = null;
        String sql = "select WORKFLOW_DEFINITION_ID,BUSINESS_ID,WORKFLOW_NAME from PF_WORKFLOW_DEFINITION order by BUSINESS_ID,WORKFLOW_DEFINITION_NO";
        List<HashMap> mapList = baseDao.getMapBySql(sql);
        if (mapList != null && mapList.size() > 0){
            wdList = new ArrayList<PfWorkflowDefinition>();
            for (int i = 0; i < mapList.size(); i++) {
                PfWorkflowDefinition wd = new PfWorkflowDefinition();
                wd.setWorkflowDefinitionId(MapUtils.getString(mapList.get(i), "WORKFLOW_DEFINITION_ID"));
                wd.setBusinessId(MapUtils.getString(mapList.get(i), "BUSINESS_ID"));
                wd.setWorkflowName(MapUtils.getString(mapList.get(i),"WORKFLOW_NAME"));
                wdList.add(wd);
            }
        }
        return wdList;
//        QPfWorkflowDefinition qPfWorkflowDefinition = QPfWorkflowDefinition.pfWorkflowDefinition;
//        JPQLQuery query = new JPAQuery(em);
//        return query.from(qPfWorkflowDefinition).orderBy(qPfWorkflowDefinition.workflowDefinitionNo.asc()).list(qPfWorkflowDefinition);
    }

    public Ztree getAllWfdTree() {
        //获取业务列表、工作流列表
        HashMap<String, Ztree> treeMap = new LinkedHashMap<String, Ztree>();
        List<PfBusiness> businessList = businessService.getAll();
        if (businessList != null && businessList.size() > 0){
            for (PfBusiness business : businessList) {
                Ztree tree = toZtreeByBusiness(business);
                treeMap.put(tree.getId(), tree);
            }
            List<PfWorkflowDefinition> wfdList = getAll();
            if (wfdList != null && wfdList.size() > 0){
                for (PfWorkflowDefinition wfd : wfdList) {
                    Ztree tree = toZtreeByWorkflowDefinition(wfd);
                    treeMap.put(tree.getId(), tree);
                }
            }
        }
        Ztree root = new Ztree();

        Ztree firstNode = new Ztree();
        firstNode.setId("treeroot");
        firstNode.setName("工作流定义");
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

    public List<Ztree> getAllWfdSimpleTree() {
        //获取业务列表、工作流列表
        List<Ztree> treeList = new ArrayList<Ztree>();
        HashMap<String, Ztree> treeMap = new LinkedHashMap<String, Ztree>();
        List<PfBusiness> businessList = businessService.getAll();
        if (businessList != null && businessList.size() > 0){
            for (PfBusiness business : businessList) {
                Ztree tree = toZtreeByBusiness(business);
                treeMap.put(tree.getId(), tree);
                treeList.add(tree);
            }
            List<PfWorkflowDefinition> wfdList = getAll();
            if (wfdList != null && wfdList.size() > 0){
                for (PfWorkflowDefinition wfd : wfdList) {
                    Ztree tree = toZtreeByWorkflowDefinition(wfd);
                    treeMap.put(tree.getId(), tree);
                    treeList.add(tree);
                }
            }
        }
        return treeList;
    }
    private static Ztree toZtreeByBusiness(PfBusiness business) {
        Ztree tree = new Ztree();
        tree.setId(business.getBusinessId());
        tree.setName(business.getBusinessName());
        tree.setPid("treeroot");
        tree.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/folder_blank.png"));
        return tree;
    }
    private static Ztree toZtreeByWorkflowDefinition(PfWorkflowDefinition workflowDefinition) {
        Ztree tree = new Ztree();
        tree.setId(workflowDefinition.getWorkflowDefinitionId());
        tree.setName(workflowDefinition.getWorkflowName());
        tree.setPid(workflowDefinition.getBusinessId());
        tree.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/flow.gif"));
        return tree;
    }

    public Map<String,List<PfWorkFlowDefineVo>> getWorkFlowDefineMap(UserInfo userInfo,String rid){
        Map<String,List<PfWorkFlowDefineVo>> mapWorkFlowDefine = Maps.newLinkedHashMap();

        List<PfWorkFlowDefineVo> lstWorkFlowDefine = Lists.newArrayList();
        if (userInfo.isAdmin())
            lstWorkFlowDefine = sysWorkFlowDefineService.getWorkFlowDefineList();
        else
            lstWorkFlowDefine = sysWorkFlowDefineService.getWorkFlowDefineListByRole(userInfo.getRoleIds(), rid, null);

        for(PfWorkFlowDefineVo wfd : lstWorkFlowDefine){
            String businessName = wfd.getBusinessVo().getBusinessName();
            String groupId = wfd.getGroupId();
            String categoryName =  businessName;
            String createUrl = wfd.getCreateUrl() == null ? "" : wfd.getCreateUrl();
            wfd.setCreateUrl(AppConfig.getPlaceholderValue(createUrl));
            if(StringUtils.isNotBlank(groupId))
                categoryName = wfd.getResourceGroupVo().getGroupName();
            if(mapWorkFlowDefine.get(categoryName) != null){
                mapWorkFlowDefine.get(categoryName).add(wfd);
            }else {
                List<PfWorkFlowDefineVo> wfdList = Lists.newArrayList();
                wfdList.add(wfd);
                mapWorkFlowDefine.put(categoryName, wfdList);
            }
        }
        return mapWorkFlowDefine;
    }
}
