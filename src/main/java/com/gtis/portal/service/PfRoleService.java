package com.gtis.portal.service;


import com.gtis.portal.entity.PfRole;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;

import java.util.LinkedHashMap;
import java.util.List;

public interface PfRoleService extends BaseService<PfRole, String> {

    /**
     * 获取按照行政区代码界别组织的角色树
     * @param regionCode
     * @return
     */
    public Ztree getRoleRegionTree(String regionCode);

    public void deleteById(String roleId);
    public List<PfRole> getListByXzqdm(String xzqdm);

    /**
     * 获取简单的角色树，没有根据行政区代码字段进行分组
     * @param xzqdm
     * @return
     */
    public List<Ztree> getTreeByXzqdm(String xzqdm);
    List<PfRole> getRoleByName(String roleName);

    /**
     * 查询那些角色拥有该主题的可见权限
     * @param subId
     * @return
     */
    public Ztree getRoleTreeBySubId(String subId,String regionCode);

    public List<PfRole> getRoleListBySubId(String subId);

    public List<ZtreeChanged> getRoleRelListBySubId(String subId);

    public LinkedHashMap<String,PfRole> roleList2RoleMap(List<PfRole> roleList);
}
