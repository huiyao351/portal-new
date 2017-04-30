package com.gtis.portal.service;


import com.gtis.plat.vo.PfWorkFlowDefineVo;
import com.gtis.plat.vo.UserInfo;
import com.gtis.portal.entity.PfResourceGroup;
import com.gtis.portal.entity.PfWorkflowDefinition;
import com.gtis.portal.model.Ztree;

import java.util.List;
import java.util.Map;

public interface PfWorkflowDefinitionService{
    public PfWorkflowDefinition findById(String wfdId);
    public void update(PfWorkflowDefinition pfWorkflowDefinition);
    public List<PfWorkflowDefinition> getAll();
    public Ztree getAllWfdTree();
    public List<Ztree> getAllWfdSimpleTree();

    public Map<String,List<PfWorkFlowDefineVo>> getWorkFlowDefineMap(UserInfo userInfo,String rid);
}
