/*
 * Project:  onemap
 * Module:   server
 * File:     IndexConfigController.java
 * Modifier: xyang
 * Modified: 2013-05-23 08:48:17
 *
 * Copyright (c) 2013 Gtmap Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.gtis.portal.web.config;

import com.alibaba.fastjson.JSON;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.config.AppConfig;
import com.gtis.config.PropertyPlaceholderHelper;
import com.gtis.plat.vo.UserInfo;
import com.gtis.portal.entity.PfBusiness;
import com.gtis.portal.entity.PfMenu;
import com.gtis.portal.entity.PfResource;
import com.gtis.portal.model.EnumObjectHelper;
import com.gtis.portal.model.Menu;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfBusinessService;
import com.gtis.portal.service.PfMenuService;
import com.gtis.portal.service.PfResourceService;
import com.gtis.portal.util.RequestUtils;
import com.gtis.portal.web.BaseController;
import com.gtis.web.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Controller
@RequestMapping("config/menu")
public class MenuController extends BaseController {
    @Autowired
    PfMenuService menuService;
    @Autowired
    PfResourceService resourceService;
    @Autowired
    PfBusinessService businessService;

    @RequestMapping("")
    public String manage(Model model) {
        return "/config/menu/manage";
    }

    @RequestMapping("json")
    @ResponseBody
    public Object menujson(Model model) {
        //组织菜单树，类似构建平台
        Ztree ztree = menuService.getAllMenuTree();
//        System.out.println(JSON.toJSONString(ztree));
        return ztree;
    }

    @RequestMapping("info")
    @ResponseBody
    public PfMenu getMemu(@RequestParam(value = "menuId", required = false) String menuId) {
        PfMenu menu = menuService.getMenuHasResNoSub(menuId);
        return menu == null ? new PfMenu() : menu;
    }

    @RequestMapping("save")
    @ResponseBody
    public Object save(HttpServletRequest req,@ModelAttribute("menu") PfMenu menu, Model model){
        if (StringUtils.isBlank(menu.getMenuId())){
            menu.setMenuId(UUIDGenerator.generate18());
        }
        PfMenu tmpMenu = menuService.getMenuHasResNoSub(menu.getMenuId());
        if (tmpMenu != null){
            tmpMenu.setMenuName(menu.getMenuName());
            tmpMenu.setMenuOrder(menu.getMenuOrder());
            menuService.update(tmpMenu);
        }else {
            tmpMenu = new PfMenu();
            tmpMenu.setMenuId(menu.getMenuId());
            tmpMenu.setMenuName(menu.getMenuName());
            tmpMenu.setMenuOrder(menu.getMenuOrder());
            tmpMenu.setMenuParentId(menu.getMenuParentId());
            tmpMenu.setResourceId(null);
            tmpMenu.setMenuExpanded(false);
            menuService.insert(tmpMenu);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("menu", tmpMenu);
        return result;
    }

    @RequestMapping("del")
    @ResponseBody
    public Object del(@ModelAttribute("menu") PfMenu menu){
        if (RequestUtils.checkIsAdmin()){
            if (menu!=null){
                menuService.delMenu(menu);
                return handlerSuccessJson();
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerSuccessJson();
    }

    @RequestMapping("saveRel")
    @ResponseBody
    public Object saveRel(HttpServletRequest req,@ModelAttribute("menu") PfMenu menu, Model model){
        if (StringUtils.isNotBlank(menu.getMenuId())){
            menuService.refreshMenuResouceRel(menu.getMenuId(),menu.getResourceId());
        }
        PfMenu tmpMenu = menuService.getMenuHasResNoSub(menu.getMenuId());
        if (tmpMenu == null){
            tmpMenu = new PfMenu();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("menu", tmpMenu);
        return result;
    }

    /**
     * 首页的导航
     * 导航菜单
     * @param systemId
     * @return
     */
    @RequestMapping("menunav")
    @ResponseBody
    public Menu menuNav(String systemId) {
        UserInfo userInfo = SessionUtil.getCurrentUser();
        if(userInfo.isAdmin())
            return menuService.getMenusByRole("",systemId);
        else {
            String roleIds = userInfo.getRoleIds();
            if(StringUtils.isNotBlank(roleIds))
                return menuService.getMenusByRole(roleIds, systemId);
            else
                return null;
        }
    }
}