package com.gtis.portal.service;


import com.gtis.portal.entity.PfResourceGroup;
import com.gtis.portal.entity.PfSubsystem;
import com.gtis.portal.entity.PfSubsystemMenuRel;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;

import java.util.List;

public interface PfSubsystemService extends BaseService<PfSubsystem, String> {
    public PfSubsystem findInitUrlById(String systemId);
    public List<PfSubsystem> getAllSubsystemList(boolean enable);
    /**
     * 获取当前用户权限下的主题菜单
     * 1、根据主题、权限表、用户角色关联表来获取主题列表
     * @param userId
     * @return
     */
    public List<PfSubsystem> getSubsystemAuthorList(String userId);
    public List<Ztree> getSubsystemTree();
    public PfSubsystem getSubsystemByName(String name);
    public void deleteSubAndRelById(String subId);
    public Integer getMaxSubsystemId();
    public PfSubsystemMenuRel getSubMenuRel(String menuId,String subId);
    public List<PfSubsystemMenuRel> getSubMenuRelList(String menuId,String subId);
    public void deleteSubMenuRel(String menuId,String subId);
    public void insertMenuRel(String menuId,String subId);
    public void updateSubMenuRel(List<ZtreeChanged> changeList,String subId);

    public void updateSubRoleRel(String subId,List<ZtreeChanged> changeList);
}
