package com.gtis.portal.service.impl;


import com.gtis.config.AppConfig;
import com.gtis.plat.vo.UserInfo;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.PfDistrictService;
import com.gtis.portal.service.PfInstanceAuthorizeService;
import com.gtis.portal.service.PfRoleService;
import com.gtis.portal.service.PfUserService;
import com.gtis.portal.util.Constants;
import com.gtis.portal.util.RequestUtils;
import com.gtis.web.SessionUtil;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PfRoleServiceImpl extends BaseServiceImpl<PfRole, String> implements PfRoleService {

    @Autowired
    PfUserService userService;
    @Autowired
    PfAuthorizeServiceImpl authorizeService;
    @Autowired
    PfInstanceAuthorizeService instanceAuthorizeService;
    @Autowired
    PfDistrictService districtService;

    /**
     * 1、获取行政区列表、获取角色列表
     * 2、处理角色结构，用行政区代码进行分组，组织成map，针对有无行政区代码分别组织，还要处理该行政区的父节点，保证完整的树结构
     * 3、根据map中拥有的行政区代码来过滤行政区列表，保留能对应上的
     * 4、将行政区和角色转换为树节点对象，进而组织成树
     * @param regionCode
     */
    public Ztree getRoleRegionTree(String regionCode){
        List<PfRole> roleList = getListByXzqdm(regionCode);
        return getRoleRegionTree(regionCode,roleList);
    }

    /**
     * 将角色列表和行政区代码组成行政区角色树
     * @param regionCode
     * @param roleList
     * @return
     */
    public Ztree getRoleRegionTree(String regionCode,List<PfRole> roleList){
        List<PfDistrict> districtList = districtService.getDistrictList(regionCode);
        LinkedHashMap<String, PfDistrict> districtVoMap = new LinkedHashMap<String, PfDistrict>();
        for (PfDistrict districtVo : districtList) {
            districtVoMap.put(districtVo.getDistrictId(),districtVo);
        }
        //角色中的行政区树，采用行政区代码进行组织
        LinkedHashMap<String, Ztree> districtMap = new LinkedHashMap<String, Ztree>();
        for (PfDistrict districtVo : districtList) {
            Ztree tree = toZtreeByXzq(districtVo,districtVoMap);
            districtMap.put(tree.getId(), tree);
        }
        //有匹配行政区代码的角色map
        LinkedHashMap<String,List<Ztree>> roleTreeMap = new LinkedHashMap<String, List<Ztree>>();
        //没有匹配行政区代码的角色map
        LinkedHashMap<String,List<Ztree>> otherRoleTreeMap = new LinkedHashMap<String, List<Ztree>>();
        if (roleList != null && !roleList.isEmpty()){
            for (int i = 0; i < roleList.size(); i++) {
                Ztree roleTree = toRegionZtreeByRole(roleList.get(i));
                String xzqdm = roleTree.getPid();
                if (districtMap.containsKey(xzqdm)){
                    List<Ztree> tmpList = null;
                    if (roleTreeMap.containsKey(xzqdm) && roleTreeMap.get(xzqdm) != null){
                        tmpList = roleTreeMap.get(xzqdm);
                        tmpList.add(roleTree);
                    }else {
                        tmpList = new ArrayList<Ztree>();
                        tmpList.add(roleTree);
                    }
                    roleTreeMap.put(xzqdm,tmpList);

                    //处理是否有上级行政区
                    if (!roleTreeMap.containsKey(xzqdm.substring(0,4)+"00")){
                        roleTreeMap.put(xzqdm.substring(0, 4) + "00", null);
                    }
                    if (!roleTreeMap.containsKey(xzqdm.substring(0,2)+"0000")){
                        roleTreeMap.put(xzqdm.substring(0,2)+"0000",null);
                    }
                    //针对乡镇一级的角色，如果是乡镇一级角色，需要判断是否包含该乡镇所属区县的树节点
                    if (StringUtils.length(xzqdm) > 6 && !roleTreeMap.containsKey(xzqdm.substring(0,6))){
                        roleTreeMap.put(xzqdm.substring(0,6),null);
                    }
                }else {
                    List<Ztree> tmpList = otherRoleTreeMap.get("other");
                    if (tmpList == null){
                        tmpList = new ArrayList<Ztree>();
                    }
                    tmpList.add(roleTree);
                    otherRoleTreeMap.put("other",tmpList);
                }
            }
        }

        //行政区map中，只保留能和角色匹配上的，也就是最终的行政区树中，每个节点都有角色，去掉空的行政区
        Iterator<String> iterator=districtMap.keySet().iterator();
        while(iterator.hasNext()){
            String xzqdm = iterator.next();
            if (!roleTreeMap.containsKey(xzqdm)){
                iterator.remove();
            }
        }

        List<Ztree> treeList=new ArrayList<Ztree>();

        //处理有行政区代码匹配的角色树
        Ztree regionRoleNode = getRegionRoleTreeNode(districtMap,roleTreeMap);
        if (regionRoleNode != null){
            treeList.add(regionRoleNode);
        }

        //处理其他行政区代码配置不正确或者是没有匹配行政区代码的角色
        Ztree otherNode = getOtherTreeNode(otherRoleTreeMap);
        if (otherNode != null){
            treeList.add(otherNode);
        }

        Ztree root = new Ztree();
        root.setName("全局角色");
        root.setId("treeroot");
        root.setNocheck(true);
        root.setNoR(true);
        root.setOpen(true);
        root.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/folder.gif"));

        //避免出现空节点，导致树加载失败
        if (regionRoleNode != null){
            root.addChild(regionRoleNode);
        }
        if (otherNode != null){
            root.addChild(otherNode);
        }
        return root;
    }

    private Ztree getRegionRoleTreeNode(LinkedHashMap<String, Ztree> districtMap,LinkedHashMap<String,List<Ztree>> roleTreeMap){
        //处理有行政区代码匹配的角色树
        Ztree regionNode = null;
        for (Ztree tree : districtMap.values()) {
            //增加角色信息
            List<Ztree> tmpList = roleTreeMap.get(tree.getKz1());
            if (tmpList != null && tmpList.size() > 0){
                for (Ztree roleTree : tmpList) {
                    tree.addChild(roleTree);
                }
            }
            if (StringUtils.isBlank(tree.getPid())){
                tree.setOpen(true);
                regionNode = tree;
            }else {
                Ztree ztree = districtMap.get(tree.getPid());
                if (ztree != null) {
                    ztree.addChild(tree);
                }
            }
        }
        return regionNode;
    }

    private Ztree getOtherTreeNode(LinkedHashMap<String,List<Ztree>> otherRoleTreeMap){
        Ztree otherNode = null;
        //处理其他行政区代码配置不正确或者是没有匹配行政区代码的角色
        UserInfo userInfo = SessionUtil.getCurrentUser();
        if (userInfo.isAdmin()){
            otherNode = new Ztree();
            otherNode.setName("其他角色");
            otherNode.setId("treeother");
            otherNode.setNocheck(true);
            otherNode.setNoR(true);
            otherNode.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/folder.gif"));

            List<Ztree> tmpList = otherRoleTreeMap.get("other");
            if (tmpList != null && tmpList.size() > 0){
                for (Ztree roleTree : tmpList) {
                    otherNode.addChild(roleTree);
                }
            }
        }
        return otherNode;
    }

    private Ztree toZtreeByXzq(PfDistrict districtVo,LinkedHashMap<String, PfDistrict> districtVoMap) {
        Ztree tree = new Ztree();
        tree.setId(districtVo.getDistrictCode());
        tree.setName(districtVo.getDistrictName());
        PfDistrict parentDistrict = districtVoMap.get(districtVo.getDistrictParentId());
        if (parentDistrict != null){
            tree.setPid(parentDistrict.getDistrictCode());
        }
        tree.setKz1(districtVo.getDistrictCode());
        tree.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/folder.gif"));
//        tree.setChkDisabled(true);
        tree.setGroup(true);
//        tree.setNocheck(true);
        return tree;
    }

    private Ztree toRegionZtreeByRole(PfRole role) {
        Ztree tree = new Ztree();
        tree.setId(role.getRoleId());
        tree.setName(role.getRoleName());
        tree.setKz1(role.getRegionCode());
        tree.setKz2(role.getRoleNo());
        tree.setPid(role.getRegionCode());
        tree.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/sup.png"));
        tree.setGroup(false);
        return tree;
    }

    @Override
    public  List<PfRole> getRoleByName(String roleName) {
        QPfRole qPfRole = QPfRole.pfRole;
        JPQLQuery query = new JPAQuery(em);
        List<PfRole> pfRoleList= query.from(qPfRole).where(qPfRole.roleName.eq(roleName)).list(qPfRole);
        return pfRoleList;
    }
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteById(String roleId){
        if (StringUtils.isNotBlank(roleId)){
            //删除菜单权限配置
            authorizeService.deleteAuthorizeListByRole(roleId);

            //删除实例权限配置
            instanceAuthorizeService.deleteAuthorizeListByRole(roleId);

            String jpql = "delete from PfUserRoleRel t where t.roleId=?0";
            baseDao.executeJpql(jpql,roleId);
            super.deleteById(roleId);
        }
    }

    public List<PfRole> getListByXzqdm(String xzqdm){
        QPfRole qPfRole = QPfRole.pfRole;
        JPQLQuery query = new JPAQuery(em);
        if (StringUtils.isNotBlank(xzqdm)){
            return query.from(qPfRole).where(qPfRole.regionCode.eq(xzqdm)).orderBy(qPfRole.roleNo.asc()).list(qPfRole);
        }else {
            return query.from(qPfRole).orderBy(qPfRole.roleNo.asc()).list(qPfRole);
        }
    }

    public List<Ztree> getTreeByXzqdm(String xzqdm){
        List<Ztree> treeList = new ArrayList<Ztree>();
        List<PfRole> roleList = getListByXzqdm(xzqdm);
        if (roleList != null && roleList.size() > 0){
            for (int i = 0; i < roleList.size(); i++) {
                treeList.add(toZtreeByRole(roleList.get(i)));
            }
        }
        return treeList;
    }

    private Ztree toZtreeByRole(PfRole role) {
        Ztree tree = new Ztree();
        tree.setId(role.getRoleId());
        tree.setName(role.getRoleName());
        tree.setKz1(role.getRegionCode());
        tree.setKz2(role.getRoleNo());
        tree.setPid("treeroot");
        return tree;
    }

    /**
     * 查询那些角色拥有该主题的可见权限
     * @param subId
     * @return
     */
    public Ztree getRoleTreeBySubId(String subId,String regionCode){
        List<PfRole> roleList = getRoleListBySubId(subId);
        return getRoleRegionTree(regionCode,roleList);
    }

    /**
     * 根据主题id获取对应权限的角色列表
     * @param subId
     * @return
     */
    public List<PfRole> getRoleListBySubId(String subId) {
        List<PfRole> roleList = new ArrayList<PfRole>();
        if (StringUtils.isNotBlank(subId)){
            String jpql = "select distinct t1 from PfRole t1,PfAuthorize t " +
                    "where t1.roleId=t.undertakerId and t.authorizeObjType=?0 and t.authorizeObjId=?1 and t.menuVisible>0";
            roleList = baseDao.getByJpql(jpql,Constants.AuthorizeObjType.SUB.getBm(),subId);
        }
        return roleList;
    }

    /**
     * 根据主题id获取对应权限的角色类表，用树控件对象返回
     * @param subId
     * @return
     */
    public List<ZtreeChanged> getRoleRelListBySubId(String subId) {
        List<PfRole> roleList = getRoleListBySubId(subId);
        List<ZtreeChanged> treeList = new ArrayList<ZtreeChanged>();
        if (roleList != null && roleList.size() > 0){
            for (int i = 0; i < roleList.size(); i++) {
                ZtreeChanged ztreeChanged = toZtreeChangedByRole(roleList.get(i));
                treeList.add(ztreeChanged);
            }
        }
        return treeList;
    }
    private ZtreeChanged toZtreeChangedByRole(PfRole role) {
        ZtreeChanged tree = new ZtreeChanged();
        tree.setId(role.getRoleId());
        tree.setName(role.getRoleName());
        return tree;
    }

    private Ztree initCheckZtree(HashMap menuMap,Ztree ztree){
        if (menuMap.containsKey(ztree.getId())){
            ztree.setChecked(true);
        }
        checkZtree(menuMap,ztree.getChildren());
        return ztree;
    }
    private void checkZtree(HashMap menuMap,List<Ztree> list){
        if (list != null && list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                if (menuMap.containsKey(list.get(i).getId())){
                    menuMap.remove(list.get(i).getId());
                    list.get(i).setChecked(true);
                    checkZtree(menuMap, list.get(i).getChildren());
                }
            }
        }
    }

    /**
     * 将角色list转换为角色map
     * @param roleList
     * @return
     */
    public LinkedHashMap<String,PfRole> roleList2RoleMap(List<PfRole> roleList) {
        LinkedHashMap<String,PfRole> roleMap = new LinkedHashMap<String, PfRole>();
        if (roleList != null && roleList.size() > 0){
            for (int i = 0; i < roleList.size(); i++) {
                roleMap.put(roleList.get(i).getRoleId(),roleList.get(i));
            }
        }
        return roleMap;
    }

}
