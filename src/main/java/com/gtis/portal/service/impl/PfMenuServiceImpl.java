package com.gtis.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.portal.dao.BaseDao;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Menu;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfAuthorizeService;
import com.gtis.portal.service.PfBusinessService;
import com.gtis.portal.service.PfMenuService;
import com.gtis.portal.service.PfResourceService;
import com.gtis.portal.util.Constants;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

import com.mysema.query.jpa.impl.JPAUpdateClause;
import org.apache.commons.collections.MapUtils;
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
public class PfMenuServiceImpl extends BaseServiceImpl<PfMenu, String> implements PfMenuService {
    @Autowired
    PfResourceService resourceService;
    @Autowired
    PfBusinessService businessService;
    @Autowired
    PfAuthorizeService authorizeService;

    public PfMenu getMenuHasResNoSub(String menuId){
        PfMenu menu = findById(menuId);
        if (menu != null){
//            menu.setSubsystems(null);
            if (StringUtils.isNotBlank(menu.getResourceId())){
                PfResource resource = resourceService.findById(menu.getResourceId());
                menu.setResource(resource);
            }
            if (menu.getResource() == null){
                menu.setResource(new PfResource());
            }
            if (StringUtils.isNotBlank(menu.getResource().getBusinessId())){
                PfBusiness business = businessService.findById(menu.getResource().getBusinessId());
                menu.getResource().setBusiness(business);
            }
            if (menu.getResource().getBusiness() == null){
                menu.getResource().setBusiness(new PfBusiness());
            }
        }
        return menu;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delMenu(PfMenu menu) {
        QPfMenu qPfMenu = QPfMenu.pfMenu;
        JPQLQuery query = new JPAQuery(em);
        List<PfMenu> menuList = query.from(qPfMenu).where(qPfMenu.menuParentId.eq(menu.getMenuId())).list(qPfMenu);
        if (menuList == null || menuList.size() == 0) {
            //删除菜单权限配置
            authorizeService.deleteAuthorizeListByMenu(menu.getMenuId());
            new JPADeleteClause(em,qPfMenu).where(qPfMenu.menuId.eq(menu.getMenuId())).execute();
        } else {
            for (PfMenu menuVo : menuList) {
                this.delMenu(menuVo);
            }
        }
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void resetResouceRel(String resourceId) {
        if (StringUtils.isNotBlank(resourceId)){
            String jpql = "update PfMenu t set t.resourceId=?0 where t.resourceId=?1";
            baseDao.executeJpql(jpql,null,resourceId);
        }
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void refreshMenuResouceRel(String menuId,String resourceId) {
        if (StringUtils.isNotBlank(menuId) && StringUtils.isNotBlank(resourceId)){
            String jpql = "update PfMenu t set t.resourceId=?0 where t.menuId=?1 ";
            baseDao.executeJpql(jpql,resourceId,menuId);
        }
    }

    public Menu getMenusByRole(String roles) {
        List<PfMenu> menuList = getMenuListByRole(roles);
        return initMenuByMenuList(menuList);
    }

    public List<PfMenu> getMenuListByRole(String roles) {
        List<PfMenu> menuList = null;
        QPfMenu qPfMenu = QPfMenu.pfMenu;
        JPQLQuery query = new JPAQuery(em);
        if (StringUtils.isBlank(roles))
            menuList = query.from(qPfMenu).orderBy(qPfMenu.menuOrder.asc()).list(qPfMenu);
        else {
            QPfAuthorize qPfAuthorize = QPfAuthorize.pfAuthorize;
            roles = roles.replace("'", "");
            String[] roleList = roles.split(",");
            menuList = query.from(qPfMenu).where(qPfMenu.menuId.in(query.from(qPfAuthorize).where(qPfAuthorize.
                    authorizeObjType.eq(Constants.AuthorizeObjType.MENU.getBm()), qPfAuthorize.menuVisible.gt(0), qPfAuthorize.undertakerId.in(roleList)).
                    distinct().list(qPfAuthorize.authorizeObjId))).orderBy(qPfMenu.menuOrder.asc()).list(qPfMenu);
        }
        return menuList;
    }

    /**
     * 利用jqpl来实现菜单的初始化操作，较为复杂
     * 已经改为下面的sql语句处理方式了
     * @param roles
     * @param subsystem
     * @return
     */
    public Menu getMenusByRoleJqpl(String roles, String subsystem) {
        List<PfMenu> menuList = new ArrayList<PfMenu>();
//        QPfMenu qPfMenu = QPfMenu.pfMenu;
//        JPQLQuery query = new JPAQuery(em);
//        if (StringUtils.isBlank(roles))
//            menuList = query.from(qPfMenu).where(qPfMenu.subsystems.any().subsystemName.eq(subsystem)).orderBy(qPfMenu.menuOrder.asc()).list(qPfMenu);
//        else {
//            QPfAuthorize qPfAuthorize = QPfAuthorize.pfAuthorize;
//            roles = roles.replace("'", "");
//            String[] roleList = roles.split(",");
//            menuList = query.from(qPfMenu).where(qPfMenu.subsystems.any().subsystemName.eq(subsystem)).where(qPfMenu.menuId.in(query.from(qPfAuthorize).where(qPfAuthorize.
//                    authorizeObjType.eq("1"), qPfAuthorize.menuVisible.gt(0), qPfAuthorize.undertakerId.in(roleList)).
//                    distinct().list(qPfAuthorize.authorizeObjId))).orderBy(qPfMenu.menuOrder.asc()).list(qPfMenu);
//        }
        return initMenuByMenuList(menuList);
    }

    public Menu getMenusByRole(String roles, String subsystem) {
        List<PfMenu> menuList = getMenuListByRole(roles,subsystem);
        return initMenuByMenuList(menuList);
    }
    public List<PfMenu> getMenuListByRole(String roles, String subsystem) {
        List<PfMenu> menuList = new ArrayList<PfMenu>();
        List<HashMap> menuMapList = null;
        /*if (StringUtils.isBlank(roles)){
            String sql ="select distinct t.* from pf_menu t,pf_subsystem_menu_rel tr,pf_subsystem ts " +
                    " where t.menu_id=tr.menu_id and tr.subsystem_id=ts.subsystem_id " +
                    " and ts.subsystem_name='"+subsystem+"'" +
                    " order by t.menu_order";
            menuMapList = baseDao.getMapBySql(sql);
        } else {
            String sql = " select distinct t.* from pf_menu t,pf_subsystem_menu_rel tr,PF_AUTHORIZE ta,pf_subsystem ts " +
                    " where t.menu_id=tr.menu_id and t.menu_id=ta.AUTHORIZE_OBJ_ID " +
                    " and ta.AUTHORIZE_OBJ_TYPE=1 and ta.MENU_VISIBLE>0 " +
                    " and tr.subsystem_id=ts.subsystem_id " +
                    " and ta.UNDERTAKER_ID in ("+roles+") " +
                    " and ts.subsystem_name='"+subsystem+"'" +
                    " order by t.menu_order";
            menuMapList = baseDao.getMapBySql(sql);
        }*/
        if (StringUtils.isBlank(roles)){
            String sql ="select distinct t.* from pf_menu t,pf_subsystem_menu_rel tr " +
                    " where t.menu_id=tr.menu_id " +
                    " and tr.subsystem_id='"+subsystem+"'" +
                    " order by t.menu_order";
            menuMapList = baseDao.getMapBySql(sql);
        } else {
            String sql = " select distinct t.* from pf_menu t,pf_subsystem_menu_rel tr,PF_AUTHORIZE ta " +
                    " where t.menu_id=tr.menu_id and t.menu_id=ta.AUTHORIZE_OBJ_ID " +
                    " and ta.AUTHORIZE_OBJ_TYPE=1 and ta.MENU_VISIBLE>0 " +
                    " and ta.UNDERTAKER_ID in ("+roles+") " +
                    " and tr.subsystem_id='"+subsystem+"'" +
                    " order by t.menu_order";
            menuMapList = baseDao.getMapBySql(sql);
        }
        if (menuMapList != null){
            for (int i = 0; i < menuMapList.size(); i++) {
                menuList.add(toMenuByMap(menuMapList.get(i)));
            }
        }
        return menuList;
    }

    @Override
    public Menu getAllMenus() {
        List<PfMenu> menuList = null;
        QPfMenu qPfMenu = QPfMenu.pfMenu;
        JPQLQuery query = new JPAQuery(em);
        menuList = query.from(qPfMenu).orderBy(qPfMenu.menuOrder.asc()).list(qPfMenu);
        return initMenuByMenuList(menuList);
    }

    private Menu initMenuByMenuList(List<PfMenu> menuList){
        Menu root = null;
        if (menuList != null && menuList.size() > 0){
            HashMap<String, Menu> menuMap = new LinkedHashMap<String, Menu>();
            for (PfMenu menuVo : menuList) {
                Menu menu = toMenu(menuVo);
                menuMap.put(menu.getId(), menu);
            }
            for (Menu menu : menuMap.values()) {
                if (menu.getPid() != null) {
                    Menu pMenu = menuMap.get(menu.getPid());
                    if (pMenu != null) {
                        pMenu.addChild(menu);
                    }
                } else {
                    root = menu;
                }
            }
        }

        return root;
    }

    @Override
    public Ztree getAllMenuTree() {
        List<PfMenu> menuList = null;
        QPfMenu qPfMenu = QPfMenu.pfMenu;
        JPQLQuery query = new JPAQuery(em);
        menuList = query.from(qPfMenu).orderBy(qPfMenu.menuOrder.asc()).list(qPfMenu);
        return initZtreeByMenuList(menuList);
    }

    public Ztree getSubMenuTree(String subId) {
//        if (StringUtils.isBlank(subId)){
//            QPfSubsystem qPfSubsystem = QPfSubsystem.pfSubsystem;
//            JPQLQuery query = new JPAQuery(em);
//            List<PfSubsystem> subList = query.from(qPfSubsystem).orderBy(qPfSubsystem.subsystemId.asc()).list(qPfSubsystem);
//            if (subList != null)
//                subId = subList.get(0).getSubsystemId();
//        }
        List<PfMenu> menuList = getSubMenuListBySubId(subId);
        return initZtreeByMenuList(menuList);
    }

    public Ztree getMenuTreeByRole(String roleId) {
        List<PfMenu> menuList = authorizeService.getAuthorizeMenuListByRoleId(roleId);
        return initZtreeByMenuList(menuList);
    }

    private List<HashMap> getSubMenuMapListBySubId(String subId){
        List<PfMenu> menuList = new ArrayList<PfMenu>();
        List<HashMap> menuMapList = null;
        String sql ="select distinct t.* from pf_menu t,pf_subsystem_menu_rel tr" +
                " where t.menu_id=tr.menu_id  " +
                " and tr.subsystem_id='"+subId+"'" +
                " order by t.menu_order";
        menuMapList = baseDao.getMapBySql(sql);
        return menuMapList;
    }

    private List<PfMenu> getSubMenuListBySubId(String subId){
        List<PfMenu> menuList = new ArrayList<PfMenu>();
        List<HashMap> menuMapList = getSubMenuMapListBySubId(subId);
        if (menuMapList != null){
            for (int i = 0; i < menuMapList.size(); i++) {
                menuList.add(toMenuByMap(menuMapList.get(i)));
            }
        }
        return menuList;
    }

    public Ztree getSubMenuCheckTree(String subId) {
        Ztree ztree = getAllMenuTree();
        //获取当前主题的菜单列表
        List<HashMap> menuMapList = getSubMenuMapListBySubId(subId);
        HashMap menuMap = new HashMap();
        if (menuMapList != null && menuMapList.size() > 0){
            for (int i = 0; i < menuMapList.size(); i++) {
                menuMap.put(MapUtils.getString(menuMapList.get(i),"MENU_ID"),true);
            }
        }
        return initCheckZtree(menuMap, ztree);
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

    public Ztree initZtreeByMenuList(List<PfMenu> menuList){
        Ztree root = null;
        if (menuList != null && menuList.size() > 0){
            HashMap<String, Ztree> menuMap = new LinkedHashMap<String, Ztree>();
            for (PfMenu menuVo : menuList) {
                Ztree tree = toZtree(menuVo);
                menuMap.put(tree.getId(), tree);
            }
            for (Ztree tree : menuMap.values()) {
                if (tree.getPid() != null) {
                    Ztree ztree = menuMap.get(tree.getPid());
                    if (ztree != null) {
                        ztree.addChild(tree);
                    }
                } else {
                    tree.setOpen(true);
                    root = tree;
                }
            }
        }
        return root;
    }

    @Override
    public Menu getFirstLevelMenus() {
        QPfMenu qPfMenu = QPfMenu.pfMenu;
        JPQLQuery query = new JPAQuery(em);
        List<PfMenu> menuList = query.from(qPfMenu).where(qPfMenu.menuParentId.eq("1")).orderBy(qPfMenu.menuOrder.asc()).list(qPfMenu);
        HashMap<String, Menu> menuMap = new LinkedHashMap<String, Menu>();
        PfMenu pfMenu = query.from(qPfMenu).where(qPfMenu.menuId.eq("1")).singleResult(qPfMenu);
        Menu root = toMenu(pfMenu);
        for (PfMenu menuVo : menuList) {
            Menu menu = toMenu(menuVo);
            root.addChild(menu);
        }
        return root;
    }


    private Ztree toZtree(PfMenu menuVo) {
        Ztree tree = new Ztree();
        tree.setId(menuVo.getMenuId());
        tree.setName(menuVo.getMenuName());
        tree.setPid(StringUtils.trimToNull(menuVo.getMenuParentId()));
//        tree.setUrl(menuVo.getResourceId());
        return tree;
    }
    private Menu toMenu(PfMenu menuVo) {
        Menu menu = new Menu();
        menu.setId(menuVo.getMenuId());
        menu.setIcon(menuVo.getMenuCode());
        menu.setText(menuVo.getMenuName());
        menu.setPid(StringUtils.trimToNull(menuVo.getMenuParentId()));
        menu.setCls(menuVo.getMenuCss());
        menu.setExpanded(menuVo.isMenuExpanded());
        String rid = menuVo.getResourceId();
        if (rid != null) {
            menu.setLink("r:" + rid);
        }
        menu.setModel(menuVo.getMenuModel());
        return menu;
    }

    public List<PfMenu> getMenuListByResourceId(String resourceId) {
        List<PfMenu> menuList = null;
        if (StringUtils.isBlank(resourceId)){
            QPfMenu qPfMenu = QPfMenu.pfMenu;
            JPQLQuery query = new JPAQuery(em);
            menuList = query.from(qPfMenu).where(qPfMenu.resourceId.eq(resourceId)).list(qPfMenu);
        }
        return menuList;
    }

    private PfMenu toMenuByMap(HashMap map){
        PfMenu menu = new PfMenu();
        menu.setMenuId(MapUtils.getString(map,"MENU_ID"));
        menu.setMenuCode(MapUtils.getString(map, "MENU_CODE"));
        menu.setMenuName(MapUtils.getString(map, "MENU_NAME"));
        menu.setMenuParentId(MapUtils.getString(map, "MENU_PARENT_ID"));
        menu.setResourceId(MapUtils.getString(map, "RESOURCE_ID"));
        menu.setMenuOrder(MapUtils.getIntValue(map, "MENU_ORDER"));
        menu.setMenuCss(MapUtils.getString(map, "MENU_CSS"));
        menu.setMenuExpanded(MapUtils.getBooleanValue(map, "MENU_EXPANDED"));
        menu.setMenuModel(MapUtils.getIntValue(map, "MENU_MODEL"));
        return menu;
    }

    /**
     * 根据菜单id，获取该菜单下所有的字菜单，包含子菜单下的子菜单
     * 包含本身
     * @param menuId
     * @return
     */
    public List<PfMenu> getAllChildMenuListByMenuId(String menuId) {
        List<PfMenu> menuList=new ArrayList<PfMenu>();
        String sql ="select m.* from pf_menu m start with m.menu_id='" +menuId
                + "'connect by m.menu_parent_id= prior m.menu_id "
                + " order by m.menu_order";
        List<HashMap>  mapList= baseDao.getMapBySql(sql);
        for (HashMap tmp :mapList ) {
            menuList.add(toMenuByMap(tmp));
        }
        return menuList;
    }

    /**
     * 根据菜单id，获取该菜单下所有的上级菜单，包含上级菜单之上的迭代菜单
     * 包含本身
     * @param menuId
     * @return
     */
    public List<PfMenu> getAllParentMenuListByMenuId(String menuId) {
        List<PfMenu> menuList=new ArrayList<PfMenu>();
        String sql ="select m.* from pf_menu m start with m.menu_id='" +menuId
                + "'connect by prior m.menu_parent_id= m.menu_id "
                + " order by m.menu_order";
        List<HashMap>  mapList= baseDao.getMapBySql(sql);
        for (HashMap tmp :mapList ) {
            menuList.add(toMenuByMap(tmp));
        }
        return menuList;
    }
}
