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
import com.gtis.portal.entity.PfSubsystem;
import com.gtis.portal.entity.PublicVo;
import com.gtis.portal.model.Menu;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.*;
import com.gtis.portal.util.Constants;
import com.gtis.portal.util.RequestUtils;
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
import java.util.*;


@Controller
@RequestMapping("config/sub")
public class SubsystemController extends BaseController {
    @Autowired
    PfMenuService menuService;
    @Autowired
    PfResourceService resourceService;
    @Autowired
    PfSubsystemService subsystemService;
    @Autowired
    PfRoleService roleService;
    @Autowired
    PfAuthorizeService authorizeService;

    @Resource
    @Qualifier("boolListNumber")
    List<PublicVo> boolListNumber;
    @Resource
    @Qualifier("subTypeList")
    List<PublicVo> subTypeList;
    @Resource
    @Qualifier("subMenuTypeList")
    List<PublicVo> subMenuTypeList;

    @RequestMapping("")
    public String manage(Model model) {
        //该功能只针对管理员使用
        if (SessionUtil.getCurrentUser().isAdmin()){
            model.addAttribute("boolListNumber",boolListNumber);
            model.addAttribute("subTypeList",subTypeList);
            model.addAttribute("subMenuTypeList",subMenuTypeList);
            List<PfSubsystem> subList = subsystemService.getAllSubsystemList(false);
            String curSubId = "";
            if (subList != null && subList.size() > 0){
                curSubId = subList.get(0).getSubsystemId();
            }
            model.addAttribute("curSubId",curSubId);
        }
        return "/config/menu/submenu";
    }

    @RequestMapping("json")
    @ResponseBody
    public Object subjson(Model model) {
        //组织菜单树，类似构建平台
        List<Ztree> ztreeList = subsystemService.getSubsystemTree();
//        System.out.println(JSON.toJSONString(ztreeList));
        return ztreeList;
    }

    /**
     * 获取有权限的主题菜单
     * @param model
     * @return
     */
    @RequestMapping("all")
    @ResponseBody
    public Object allSub(Model model) {
        //获取有权限的主题列表
        List<PfSubsystem> subList = subsystemService.getSubsystemAuthorList(SessionUtil.getCurrentUserId());
//        System.out.println(JSON.toJSONString(ztreeList));
        if (subList != null && subList.size() > 0){
            for (int i = 0; i < subList.size(); i++) {
                String url = subList.get(i).getSubUrl();
                if(StringUtils.isNotBlank(url)){
                    subList.get(i).setSubUrl(RequestUtils.initOptProperties(url));
                }
            }
        }
        return subList;
    }

    @RequestMapping("checkjson")
    @ResponseBody
    public Object menuCheckjson(Model model,@RequestParam(value = "subId", required = false) String subId) {
        //组织菜单树，类似构建平台
        Ztree ztree = menuService.getSubMenuCheckTree(subId);
//        System.out.println(JSON.toJSONString(ztree));
        return ztree;
    }

    @RequestMapping("info")
    @ResponseBody
    public PfSubsystem getSubsystem(@RequestParam(value = "subsystemId", required = false) String subsystemId) {
        PfSubsystem subsystem = subsystemService.findById(subsystemId);
        if (subsystem == null){
            subsystem = new PfSubsystem();
            subsystem.setSubsystemId(subsystemId);
        }
        Ztree ztree = menuService.getSubMenuTree(subsystem.getSubsystemId());
        subsystem.setSubmenuTree(ztree);
        return subsystem;
    }

    @RequestMapping("save")
    @ResponseBody
    public Object save(HttpServletRequest req,@ModelAttribute("subsystem") PfSubsystem subsystem, Model model){
        if (StringUtils.isBlank(subsystem.getSubsystemId())){
            subsystem.setSubsystemId(subsystemService.getMaxSubsystemId().toString());
        }
        PfSubsystem tmpSub = subsystemService.findById(subsystem.getSubsystemId());
        if (tmpSub != null){
            subsystemService.update(subsystem);
        }else {
            subsystemService.insert(subsystem);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("sub", subsystem);
        return result;
    }

    @RequestMapping("del")
    @ResponseBody
    public Object del(@RequestParam(value = "keyId", required = false) String keyId){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(keyId)){
                subsystemService.deleteSubAndRelById(keyId);
                return handlerSuccessJson();
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerSuccessJson();
    }

    @RequestMapping("menujson")
    @ResponseBody
    public Object subMenuJson(Model model,@RequestParam(value = "subId", required = false) String subId) {
        //组织菜单树，类似构建平台
        Ztree ztree = menuService.getSubMenuTree(subId);
//        System.out.println(JSON.toJSONString(ztree));
        return ztree;
    }

    @RequestMapping("saveRel")
    @ResponseBody
    public Object saveSubMenuRel(HttpServletRequest req,String paramString,@RequestParam(value = "subId", required = true)String subId, Model model){
        if (paramString != null && StringUtils.isNotBlank(subId)){
            List<ZtreeChanged> changeList = JSON.parseArray(paramString,ZtreeChanged.class);
            subsystemService.updateSubMenuRel(changeList, subId);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        return result;
    }
    @RequestMapping("delRel")
    @ResponseBody
    public Object delSubMenuRel(@RequestParam(value = "subId", required = true)String subId,@RequestParam(value = "menuId", required = true) String menuId){
        if (StringUtils.isNotBlank(subId) && StringUtils.isNotBlank(menuId)){
            subsystemService.deleteSubMenuRel(menuId,subId);
            return handlerSuccessJson();
        }
        return handlerSuccessJson();
    }

    /**
     * 主题菜单权限配置功能
     * @param model
     * @return
     */
    @RequestMapping("author")
    public String author(Model model,@RequestParam(value = "subId", required = false) String subId) {
        if (SessionUtil.getCurrentUser().isAdmin()){
            model.addAttribute("boolListNumber",boolListNumber);
            model.addAttribute("subTypeList",subTypeList);
            model.addAttribute("subMenuTypeList",subMenuTypeList);
            List<PfSubsystem> subList = subsystemService.getAllSubsystemList(false);
            String curSubId = subId;
            if (StringUtils.isBlank(curSubId)){
                if (subList != null && subList.size() > 0){
                    curSubId = subList.get(0).getSubsystemId();
                }
            }
            model.addAttribute("curSubId",curSubId);
        }
        return "/config/menu/submenu-author";
    }

    /**
     * 主题菜单权限配置功能
     * @param model
     * @return
     */
    @RequestMapping("authManage")
    public String authManage(Model model,@RequestParam(value = "subId", required = false) String subId) {
        author(model,subId);
        return "/config/menu/submenu-author-manage";
    }

    /**
     * 获取有该主题的角色权限列表
     * @param model
     * @param subId
     * @return
     */
    @RequestMapping("roleRelList")
    @ResponseBody
    public Object roleCheckjson(Model model,@RequestParam(value = "subId", required = false) String subId) {
        //组织角色
        List<ZtreeChanged> treeList = roleService.getRoleRelListBySubId(subId);
        return treeList;
    }

    /**
     * 获取有该主题的角色树
     * @param model
     * @param subId
     * @return
     */
    @RequestMapping("roleReljson")
    @ResponseBody
    public Object roleReljson(Model model,@RequestParam(value = "subId", required = false) String subId) {
        //组织菜单树，类似构建平台
        Ztree ztree = roleService.getRoleTreeBySubId(subId,null);
//        System.out.println(JSON.toJSONString(ztree));
        return ztree;
    }

    /**
     * 保存主题的权限关系
     * @param paramString
     * @param subId
     * @return
     */
    @RequestMapping("saveRoleRel")
    @ResponseBody
    public Object saveRoleRel(String paramString,@RequestParam(value = "subId", required = true)String subId){
        if (paramString != null && StringUtils.isNotBlank(subId)){
            List<ZtreeChanged> changeList = JSON.parseArray(paramString,ZtreeChanged.class);
            subsystemService.updateSubRoleRel(subId,changeList);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        return result;
    }

    /**
     * 删除主题与角色关系
     * @param subId
     * @param roleId
     * @return
     */
    @RequestMapping("delRoleRel")
    @ResponseBody
    public Object delRoleRel(@RequestParam(value = "subId", required = true)String subId,@RequestParam(value = "roleId", required = true) String roleId){
        if (StringUtils.isNotBlank(subId) && StringUtils.isNotBlank(roleId)){
            authorizeService.delAuthByRoleAndObjId(roleId,subId, Constants.AuthorizeObjType.SUB.getBm());
            return handlerSuccessJson();
        }
        return handlerSuccessJson();
    }


}