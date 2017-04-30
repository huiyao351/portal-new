package com.gtis.portal.service;


import com.gtis.portal.entity.PfRole;
import com.gtis.portal.entity.PfUserRoleRel;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;

import java.util.LinkedHashMap;
import java.util.List;

public interface PfUserRoleService extends BaseService<PfUserRoleRel, String> {
    public void deleteUserRoleByRoleId(String id);

    List<PfUserRoleRel> findUserROleListById(String roleId);

    void deleteUserRoleByUseridAndRoleId(String roleId,String userId);

    boolean findUserRole(PfUserRoleRel userRole);

    void deleteUserRoleByUserid(String userId);

    List<Ztree> findRolebyUserId(String userId);

    public List<PfRole> getRoleListByUserId(String userId);

    /**
     * 根据用户id，保存角色列表与用户的关系
     * @param userId
     * @param changeList
     */
    public void addRoleRel(String userId,List<ZtreeChanged> changeList);

    /**
     * 根据角色id，保存用户列表与角色的关系
     * @param roleId
     * @param changeList
     */
    public void addRoleUserRel(String roleId,List<ZtreeChanged> changeList);

    public LinkedHashMap<String,PfRole> getRoleMapByUserid(String userId);
}
