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
import com.gtis.plat.vo.UserInfo;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Menu;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.*;
import com.gtis.portal.web.BaseController;
import com.gtis.web.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("config/menuAuth")
public class MenuAuthorController extends BaseController {
    @Autowired
    PfMenuService menuService;
    @Autowired
    PfResourceService resourceService;
    @Autowired
    PfAuthorizeService authorizeService;
    @Autowired
    PfResourcePartitionService resourcePartitionService;

    @Resource
    @Qualifier("partitionTypeList")
    List<PublicVo> partitionTypeList;
    @Resource
    @Qualifier("partitionOperatType")
    List<PublicVo> partitionOperatType;

    @RequestMapping("")
    public String author(Model model) {
        model.addAttribute("partitionTypeJson", JSON.toJSONString(partitionTypeList));
        model.addAttribute("partitionOperatTypeJson",JSON.toJSONString(partitionOperatType));
        return "/config/menu/menu-author";
    }

    @RequestMapping("reljson")
    @ResponseBody
    public Object roleRelJson(Model model,String menuId) {
        //组织角色
        List<ZtreeChanged> treeList = authorizeService.getAuthorizeRoleTreeListByMenuId(menuId);//instanceAuthorizeService.getAuthorizeRoleTreeListByWdId(menuId);
        return treeList;
    }

    /**
     * 打开角色下有多少菜单权限的页面
     * @param model
     * @param roleId
     * @return
     */
    @RequestMapping("relmenu")
    public String relMenu(Model model,String roleId) {
        model.addAttribute("partitionTypeJson", JSON.toJSONString(partitionTypeList));
        model.addAttribute("partitionOperatTypeJson",JSON.toJSONString(partitionOperatType));

        model.addAttribute("roleId", roleId);
        return "/config/role/role-menu-author";
    }

    /**
     * 获取角色下有权限的菜单json数据
     * @param model
     * @param roleId
     * @return
     */
    @RequestMapping("menujson")
    @ResponseBody
    public Object menuRelJson(Model model,String roleId) {
        Ztree ztree = menuService.getMenuTreeByRole(roleId);//instanceAuthorizeService.getAuthorizeRoleTreeListByWdId(menuId);
        return ztree;
    }

    /**
     * 获取菜单对应的角色的所以功能分区
     * @param menuId
     * @return
     */
    @RequestMapping("partiInfo")
    @ResponseBody
    public List<PfResourcePartition> getPartiInfo(@RequestParam(value = "menuId", required = true) String menuId) {
        //获取实例权限中配置的功能分区操作类型
        List<PfResourcePartition> rpList = null;
        if (StringUtils.isNotBlank(menuId)){
            PfMenu menu = menuService.getMenuHasResNoSub(menuId);
            if (menu != null && StringUtils.isNotBlank(menu.getResourceId())){
                rpList = resourcePartitionService.getListByRid(menu.getResourceId());
            }
        }
        return rpList == null ? new ArrayList<PfResourcePartition>() : rpList;
    }

    /**
     * 获取角色下，菜单对应资源的功能分区配置
     * @param roleId
     * @param menuId
     * @return
     */
    @RequestMapping("partiOperInfo")
    @ResponseBody
    public List<HashMap> getPartiOperInfo(@RequestParam(value = "roleId", required = true) String roleId,@RequestParam(value = "menuId", required = true) String menuId) {
        //获取实例权限中配置的功能分区操作类型
        List<PfAuthorize> authPartList = null;
        if (StringUtils.isNotBlank(menuId) && StringUtils.isNotBlank(roleId)){
            authPartList = authorizeService.getAuthPartListByRoleId(roleId,menuId);
        }
        if (authPartList == null){
            authPartList = new ArrayList<PfAuthorize>();
        }

        List<HashMap> mapList = new ArrayList<HashMap>();
        for (int i = 0; i < authPartList.size(); i++) {
            PfAuthorize authorize = authPartList.get(i);
            if (authorize != null){
                HashMap map = new HashMap();
                map.putAll(JSON.parseObject(JSON.toJSONString(authorize),HashMap.class));
                PfResourcePartition parttion = resourcePartitionService.findById(authorize.getAuthorizeObjId());
                if (parttion != null){
                    map.putAll(JSON.parseObject(JSON.toJSONString(parttion),HashMap.class));
                }
                mapList.add(map);
            }
        }
        return mapList;
    }

    @RequestMapping("saveRel")
    @ResponseBody
    public Object saveRoleRel(HttpServletRequest req,@RequestParam(value = "menuId", required = true)String menuId,String paramString){
        //主菜单授权功能说明
        //1、保存时，需要将菜单与所选角色进行关联操作，表中有几个字段简要说明下：
        /*
        UNDERTAKER_ID	用于存储角色ID
        AUTHORIZE_OBJ_TYPE	如果是之关联了菜单，该菜单没有功能分区，或者没有编辑功能分区，则保存为1，如果设置了功能分区则赋值为0
        UNDERTAKE_WORKFLOW_ID	VARCHAR2(32)	Y			授权工作流ID
        UNDERTAKE_ACTIVITY_ID	VARCHAR2(32)	Y			授权活动ID
        OPERATE_TYPE	操作类型：完全控制0，只读1，不可见2
        AUTHORIZE_OBJ_ID 存储菜单或者资源分区id，如果有资源分配配置，则在保存菜单记录基础上，增加资源分区的记录
        MENU_VISIBLE  菜单是否可见
        */
        if (StringUtils.isNotBlank(paramString) && StringUtils.isNotBlank(menuId)){
            System.out.println(paramString);
            List<ZtreeChanged> changeList = JSON.parseArray(StringUtils.trim(paramString),ZtreeChanged.class);
            authorizeService.updateMenuRoleRel(menuId, changeList);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        return result;
    }

    @RequestMapping("updatePartOperType")
    @ResponseBody
    public Object updatePartOperType(HttpServletRequest req,@RequestParam(value = "menuId", required = true)String menuId,@RequestParam(value = "roleId", required = true) String roleId,String paramString){
        if (StringUtils.isNotBlank(menuId) && StringUtils.isNotBlank(roleId) && StringUtils.isNotBlank(paramString)){
            List<ZtreeChanged> changeList = JSON.parseArray(StringUtils.trim(paramString), ZtreeChanged.class);
            authorizeService.updatePartOperType(menuId,roleId,changeList);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        return result;
    }
}