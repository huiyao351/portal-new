package com.gtis.portal.service.impl;

import com.gtis.common.util.CommonUtil;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.*;
import com.gtis.portal.service.PfResourcePartitionService;
import com.gtis.portal.util.Constants;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class PfAuthorizeServiceImpl extends BaseServiceImpl<PfAuthorize, String> implements PfAuthorizeService {
    @Autowired
    PfResourcePartitionService resourcePartitionService;
    @Autowired
    PfMenuService menuService;

    /**
     * 查询该角色下，功能分区配置
     * @param roleId
     * @return
     */
    public List<PfAuthorize> getAuthPartListByRoleId(String roleId,String menuId){
        if (StringUtils.isNotBlank(roleId) && StringUtils.isNotBlank(menuId)){
            PfMenu menu = menuService.findById(menuId);
            if (menu != null && StringUtils.isNotBlank(menu.getResourceId())){
                String jpql = "select t from PfAuthorize t,PfResourcePartition t1 " +
                        "where t1.partitionId=t.authorizeObjId and t.undertakerId=?0 and t1.resourceId=?1 and t.authorizeObjType=0";
                return baseDao.getByJpql(jpql,roleId,menu.getResourceId());
            }
        }

        return null;
    }

    /**
     * 查询该配置功能下，有哪些权限可见
     * @param authorizeObjType
     * @param authorizeObjId
     * 针对菜单在角色下是否可见的功能配置类型authorizeObjType为1
     * 针对菜单在角色下功能分区的功能配置类型authorizeObjType为0
     * 针对主题在角色下是否可见的功能配置类型authorizeObjType为8
     *
     * @return
     */
    public List<PfAuthorize> getAuthPartListByObjId(Integer authorizeObjType,String authorizeObjId){
        List<PfAuthorize> pfAuthorizeList = new ArrayList<PfAuthorize>();
        if (authorizeObjType != null && StringUtils.isNotBlank(authorizeObjId)){
            String jpql = "select t from PfAuthorize t " +
                    "where t.authorizeObjType=?0 and t1.authorizeObjId=?1 and t.menuVisible=>0";
            return baseDao.getByJpql(jpql,authorizeObjType,authorizeObjId);
        }
        return null;
    }

    /**
     * 根据菜单id，获取那些角色可以看到该菜单
     * @param menuId
     * @return
     */
    public List<PfRole> getAuthorizeRoleListByMenuId(String menuId){
        String jpql = "select distinct t1 from PfRole t1,PfAuthorize t " +
                " where t.undertakerId=t1.roleId and t.authorizeObjId=?0 and t.menuVisible>0 order by t1.roleNo";
        return baseDao.getByJpql(jpql,menuId);
    }

    /**
     * 根据角色id，获取该角色可以看到哪些菜单
     * @param roleId
     * @return
     */
    public List<PfMenu> getAuthorizeMenuListByRoleId(String roleId){
        String jpql = "select distinct t1 from PfMenu t1,PfAuthorize t " +
                " where t.authorizeObjId=t1.menuId and t.undertakerId=?0 and t.menuVisible>0 order by t1.menuOrder";
        return baseDao.getByJpql(jpql,roleId);
    }

    /**
     * 检查角色、分区下是否有权限记录，对应的权限类型为0，authorizeObjId在该情况下代表的是分区id
     * 检查角色、菜单下是否有权限记录，对应的权限类型为1，authorizeObjId在该情况下代表的是菜单id
     * @param undertakerId
     * @param authorizeObjId
     * @param authorizeObjType
     * @return
     */
    public boolean checkHasAuth(String undertakerId,String authorizeObjId,Integer authorizeObjType){
        PfAuthorize obj = getAuthByRoleAndObjId(undertakerId,authorizeObjId,authorizeObjType);
        if (obj != null && StringUtils.isNotBlank(obj.getAuthorizeId())){
            return true;
        }
        return false;
    }

    /**根据角色id、权限对象id、权限类型获取权限对象
     * @param undertakerId
     * @param authorizeObjId
     * @param authorizeObjType
     * @return
     */
    public PfAuthorize getAuthByRoleAndObjId(String undertakerId,String authorizeObjId,Integer authorizeObjType){
        if (StringUtils.isNotBlank(undertakerId) && StringUtils.isNotBlank(authorizeObjId) && authorizeObjId != null){
            String jpql = "select t from PfAuthorize t where t.undertakerId=?0 and " +
                    " t.authorizeObjId=?1 and t.authorizeObjType=?2 and rownum=1";
            return (PfAuthorize)baseDao.getUniqueResultByJpql(jpql,undertakerId,authorizeObjId,authorizeObjType);
        }
        return null;
    }

    /**根据角色id、权限对象id、权限类型获取权限对象
     * @param undertakerId
     * @param authorizeObjId
     * @param authorizeObjType
     * @return
     */
    @Transactional
    public void delAuthByRoleAndObjId(String undertakerId,String authorizeObjId,Integer authorizeObjType){
        PfAuthorize obj = getAuthByRoleAndObjId(undertakerId,authorizeObjId,authorizeObjType);
        if (obj != null && StringUtils.isNotBlank(obj.getAuthorizeId())){
            delete(obj);
        }
    }

    public List<ZtreeChanged> getAuthorizeRoleTreeListByMenuId(String menuId){
        List<PfRole> roleList = getAuthorizeRoleListByMenuId(menuId);
        List<ZtreeChanged> treeList = new ArrayList<ZtreeChanged>();
        if (roleList != null && roleList.size() > 0){
            for (int i = 0; i < roleList.size(); i++) {
                ZtreeChanged tree = toZtreeByRole(roleList.get(i));
                treeList.add(tree);
            }
        }
        return treeList;
    }

    /**
     * 更新菜单、角色是否可见，操作菜单权限功能
     * @param roleId
     * @param menuId
     * @param menuVisible
     */
    @Transactional
    public void updateRoleMenuVisible(String roleId,String menuId,Integer menuVisible,Integer authorizeObjType){
        if (StringUtils.isNotBlank(roleId) && StringUtils.isNotBlank(menuId) && menuVisible != null){
            String jpql = "update from PfAuthorize t set t.menuVisible=?0 where t.undertakerId=?1 and t.authorizeObjId=?2 and t.authorizeObjType=?3 ";
            baseDao.executeJpql(jpql,menuVisible,roleId,menuId,authorizeObjType);
        }
    }
    @Transactional
    public void updateMenuRoleRel(String menuId,List<ZtreeChanged> changeList){
        if (changeList != null && changeList.size() > 0 && StringUtils.isNotBlank(menuId)){
            //根据menuid查找器上级菜单，一直查找到根路径菜单，对这些父级菜单逐一赋值
            List<PfMenu> parentMenuList = menuService.getAllParentMenuListByMenuId(menuId);
            if (parentMenuList != null && parentMenuList.size() > 0){
                for (PfMenu menu : parentMenuList) {
                    String tmpMenuId = menu.getMenuId();
                    for (int i = 0; i < changeList.size(); i++) {
                        ZtreeChanged change = changeList.get(i);
                        Integer visible = change.isChecked()?1:0;
                        //查找该菜单和角色下是否有权限记录，如果没有则增加权限记录，如果有则修改操作类型
                        //此处的权限类型为1
                        boolean hasRole = checkHasAuth(change.getId(), tmpMenuId,1);
                        if (visible == 1){
                            if (!StringUtils.equalsIgnoreCase(menuId,tmpMenuId)){
                                visible = 2;
                            }
                        }
                        if (!hasRole){
                            PfAuthorize authorize = new PfAuthorize();
                            authorize.setAuthorizeId(UUIDGenerator.generate18());
                            authorize.setAuthorizeObjType(Constants.AuthorizeObjType.MENU.getBm());
                            authorize.setUndertakerId(change.getId());
                            authorize.setAuthorizeObjId(tmpMenuId);
                            authorize.setMenuVisible(visible);
                            insert(authorize);
                        }else {
                            updateRoleMenuVisible(change.getId(), tmpMenuId,visible,Constants.AuthorizeObjType.MENU.getBm());
                        }
                    }
                }
            }
        }
    }

    @Transactional
    public void updateMenuRoleAuthorizePart(String roleId,String menuId,Integer operateType){
        if (StringUtils.isNotBlank(roleId) && StringUtils.isNotBlank(menuId) && operateType != null){
            String jpql = "update from PfAuthorize t set t.operateType=?0 where t.undertakerId=?1 and t.authorizeObjId=?2 and t.authorizeObjType=0";
            baseDao.executeJpql(jpql,operateType,roleId,menuId);
        }
    }

    /**
     * 实现思路：
     * 1、查询出该菜单对应的资源的功能分区
     * 2、解析前台传递过来的功能分区配置
     * 3、查询该菜单、角色下，是否存在该功能分区，如果存在则修改，如果不存在则新增
     */
    @Transactional
    public void updatePartOperType(String menuId,String roleId,List<ZtreeChanged> changeList){
        if (changeList != null && changeList.size() > 0 && StringUtils.isNotBlank(menuId) && StringUtils.isNotBlank(roleId)){
            PfMenu menu = menuService.findById(menuId);
            if (menu != null && StringUtils.isNotBlank(menu.getResourceId())){
                for (int i = 0; i < changeList.size(); i++) {
                    ZtreeChanged change = changeList.get(i);
                    if (StringUtils.isNotBlank(change.getName())){
                        Integer operateType = Integer.parseInt(StringUtils.trim(change.getName()));
                        //查找该角色和资源的分区下是否有记录，如果没有则增加权限记录，如果有则修改操作类型
                        //此处的权限类型为0
                        boolean hasRole = checkHasAuth(roleId, change.getId(), 0);
                        if (!hasRole){
                            PfAuthorize authorize = new PfAuthorize();
                            authorize.setAuthorizeId(UUIDGenerator.generate18());
                            authorize.setAuthorizeObjType(Constants.AuthorizeObjType.ZYFQ.getBm());
                            authorize.setUndertakerId(roleId);
                            authorize.setAuthorizeObjId(change.getId());
                            authorize.setOperateType(operateType);
                            insert(authorize);
                        }else {
                            updateMenuRoleAuthorizePart(roleId,change.getId(),operateType);
                        }
                    }
                }
            }
        }
    }

    @Transactional
    public void deleteAuthorizeListByRole(String roleId){
        if (StringUtils.isNotBlank(roleId)){
            String jpql = "delete from PfAuthorize t where t.undertakerId=?0";
            baseDao.executeJpql(jpql,roleId);
        }
    }

    /**
     * 根据权限类型和权限对象id删除权限记录
     * @param authorizeObjId
     * @param authorizeObjType
     */
    @Transactional
    public void delAuthByObjIdAndType(String authorizeObjId,Integer authorizeObjType){
        if (StringUtils.isNotBlank(authorizeObjId) && authorizeObjType != null){
            String jpql = "delete from PfAuthorize t where t.authorizeObjId=?0 and t.authorizeObjType=?1";
            baseDao.executeJpql(jpql,authorizeObjId,authorizeObjType);
        }
    }

    @Transactional
    public void deleteAuthorizeListByMenu(String menuId){
        if (StringUtils.isNotBlank(menuId)){
            PfMenu menu = menuService.findById(menuId);
            if (menu != null && StringUtils.isNotBlank(menu.getResourceId())){
                deleteAuthorizeListByResource(menu.getResourceId());
                String jpql = "delete from PfAuthorize t where t.authorizeObjId=?0 and t.authorizeObjType=1";
                baseDao.executeJpql(jpql,menuId);
            }
        }
    }
    @Transactional
    public void deleteAuthorizeListByResource(String resourceId){
        if (StringUtils.isNotBlank(resourceId)){
            List<PfResourcePartition> partList = resourcePartitionService.getListByRid(resourceId);
            if (partList != null && partList.size() > 0){
                List<String> idList = new ArrayList<String>();
                for (int i = 0; i < partList.size(); i++) {
                    idList.add(partList.get(i).getPartitionId());
                }
                if (idList.size() > 0){
                    String jpql = "delete from PfAuthorize t where t.authorizeObjId in ?0 and t.authorizeObjType=0";
                    baseDao.executeJpql(jpql,idList);
                }
            }
        }
    }

    @Transactional
    public void deleteAuthorizeListByPartitionId(String partitionId){
        if (StringUtils.isNotBlank(partitionId)){
            String jpql = "delete from PfAuthorize t where t.authorizeObjId = ?0 and t.authorizeObjType=0";
            baseDao.executeJpql(jpql,partitionId);
        }
    }

    private ZtreeChanged toZtreeByRole(PfRole role) {
        ZtreeChanged tree = new ZtreeChanged();
        tree.setId(role.getRoleId());
        tree.setName(role.getRoleName());
        return tree;
    }
}
