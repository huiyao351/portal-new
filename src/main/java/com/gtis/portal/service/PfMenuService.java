package com.gtis.portal.service;


import com.gtis.portal.entity.PfBusiness;
import com.gtis.portal.entity.PfMenu;
import com.gtis.portal.model.Menu;
import com.gtis.portal.model.Ztree;

import java.util.List;

public interface PfMenuService extends BaseService<PfMenu, String> {

    public PfMenu getMenuHasResNoSub(String menuId);

    /**
     * 删除菜单（包括子菜单)
     * @param menu
     */
    public void delMenu(PfMenu menu);

    public void resetResouceRel(String resourceId);

    public void refreshMenuResouceRel(String menuId,String resourceId);

    /**
     * 获得一个角色下所有的菜单
     * @param roles
     * @return
     */
    public Menu getMenusByRole(String roles);
    public List<PfMenu> getMenuListByRole(String roles);

    public Menu getMenusByRole(String roles,String subsystem);
    public List<PfMenu> getMenuListByRole(String roles, String subsystem);

    /**
     * 获取所有菜单，用于菜单树的组织展示
     * @return
     */
    public Menu getAllMenus();

    public Ztree initZtreeByMenuList(List<PfMenu> menuList);

    /**
     * 获取一级菜单（避免效率太低）
     * @return
     */
    public Menu getFirstLevelMenus();

    public Ztree getAllMenuTree();

    public Ztree getSubMenuTree(String subId);

    public Ztree getMenuTreeByRole(String roleId);

    public Ztree getSubMenuCheckTree(String subId);

    public List<PfMenu> getMenuListByResourceId(String resourceId);

    public List<PfMenu> getAllChildMenuListByMenuId(String menuId);

    public List<PfMenu> getAllParentMenuListByMenuId(String menuId);
}
