package com.gtis.portal.service.impl;


import com.gtis.common.util.UUIDGenerator;
import com.gtis.config.AppConfig;
import com.gtis.portal.entity.PfRole;
import com.gtis.portal.entity.PfUser;
import com.gtis.portal.entity.PfUserRoleRel;
import com.gtis.portal.entity.QPfUserRoleRel;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.PfRoleService;
import com.gtis.portal.service.PfUserRoleService;
import com.gtis.portal.util.RequestUtils;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PfUserRoleServiceImpl extends BaseServiceImpl<PfUserRoleRel, String> implements PfUserRoleService {
    @Autowired
    PfRoleService roleService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteUserRoleByRoleId(String id) {
        QPfUserRoleRel qPfUserRole=QPfUserRoleRel.pfUserRoleRel;
        new JPADeleteClause(em,qPfUserRole).where(qPfUserRole.roleId.eq(id)).execute();
    }

    @Override
    public List<PfUserRoleRel> findUserROleListById(String roleId) {
        QPfUserRoleRel qPfUserRole=QPfUserRoleRel.pfUserRoleRel;
        JPQLQuery query = new JPAQuery(em);
        List<PfUserRoleRel> pfUserRoleList= query.from(qPfUserRole).where(qPfUserRole.roleId.eq(roleId)).list(qPfUserRole);
        return pfUserRoleList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteUserRoleByUseridAndRoleId(String roleId,String userId) {
        QPfUserRoleRel qPfUserRole=QPfUserRoleRel.pfUserRoleRel;
        new JPADeleteClause(em,qPfUserRole).where(qPfUserRole.userId.eq(userId))
                .where(qPfUserRole.roleId.eq(roleId)).execute();
    }

    @Override
    public boolean findUserRole(PfUserRoleRel userRole) {
        QPfUserRoleRel qPfUserRole=QPfUserRoleRel.pfUserRoleRel;
        JPQLQuery query = new JPAQuery(em);
        List<PfUserRoleRel> pfUserRoleList= query.from(qPfUserRole).where(qPfUserRole.roleId.eq(userRole.getRoleId())).where(qPfUserRole.userId.eq(userRole.getUserId())).list(qPfUserRole);
        if(pfUserRoleList!=null&&pfUserRoleList.size()>=1){
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteUserRoleByUserid(String user_id) {
        QPfUserRoleRel qPfUserRole=QPfUserRoleRel.pfUserRoleRel;
        new JPADeleteClause(em,qPfUserRole).where(qPfUserRole.userId.eq(user_id))
                .execute();
    }

    @Override
    public List<Ztree> findRolebyUserId(String userId) {
        List<Ztree> treeList = new ArrayList<Ztree>();
        List<PfRole> roleList = getRoleListByUserId(userId);
        if (roleList != null && roleList.size() > 0){
            if (roleList != null && roleList.size() > 0){
                for (int i = 0; i < roleList.size(); i++) {
                    Ztree tree = toZtreeByRole(roleList.get(i));
                    treeList.add(tree);
                }
            }
        }
        return treeList;
    }

    private Ztree toZtreeByRole(PfRole role) {
        Ztree tree = new Ztree();
        tree.setId(role.getRoleId());
        tree.setName(role.getRoleName());
        tree.setPid("treeroot");
        tree.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/sup.png"));
        return tree;
    }

    public List<PfRole> getRoleListByUserId(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            String jpql = "select distinct t1 from PfRole t1,PfUserRoleRel t " +
                    " where t.roleId=t1.roleId and t.userId=?0 ";
            jpql += " order by t1.roleNo ";
            return baseDao.getByJpql(jpql, userId);
        }
        return null;
    }

    public boolean checkHasRole(String userId,String roleId){
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(roleId)){
            String jpql = "select t from PfUserRoleRel t where t.userId=?0 and  t.roleId=?1 and rownum=1";
            PfUserRoleRel obj = (PfUserRoleRel)baseDao.getUniqueResultByJpql(jpql,userId,roleId);
            if (obj != null && StringUtils.isNotBlank(obj.getUrrId())){
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void addRoleRel(String userId,List<ZtreeChanged> changeList){
        if (changeList != null && changeList.size() > 0 && StringUtils.isNotBlank(userId)){
            for (int i = 0; i < changeList.size(); i++) {
                ZtreeChanged change = changeList.get(i);
                if (change.isLeaf()){
                    boolean hasRole = checkHasRole(userId,change.getId());
                    if (!hasRole){
                        PfUserRoleRel rel = new PfUserRoleRel();
                        rel.setUrrId(UUIDGenerator.generate18());
                        rel.setRoleId(change.getId());
                        rel.setUserId(userId);
                        insert(rel);
                    }
                }
            }
        }
    }

    @Transactional
    public void addRoleUserRel(String roleId,List<ZtreeChanged> changeList){
        if (changeList != null && changeList.size() > 0 && StringUtils.isNotBlank(roleId)){
            for (int i = 0; i < changeList.size(); i++) {
                ZtreeChanged change = changeList.get(i);
                if (change.isLeaf()){
                    //检查是否选中状态，选中则保存，未选中则删除
                    boolean hasRole = checkHasRole(change.getId(),roleId);
                    if (change.isChecked()){
                        if (!hasRole){
                            PfUserRoleRel rel = new PfUserRoleRel();
                            rel.setUrrId(UUIDGenerator.generate18());
                            rel.setRoleId(roleId);
                            rel.setUserId(change.getId());
                            insert(rel);
                        }
                    }else {
                        deleteUserRoleByUseridAndRoleId(roleId,change.getId());
                    }
                }
            }
        }
    }

    /**
     * 根据人员获取角色列表，将角色list转换为角色map
     * @param userId
     * @return
     */
    public LinkedHashMap<String,PfRole> getRoleMapByUserid(String userId){
        List<PfRole> roleList = getRoleListByUserId(userId);
        return  roleService.roleList2RoleMap(roleList);
    }
}
