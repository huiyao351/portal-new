
package com.gtis.portal.web.config;

import com.alibaba.fastjson.JSON;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.*;
import com.gtis.portal.util.RequestUtils;
import com.gtis.portal.web.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@RequestMapping("config/busi")
public class BusinessController extends BaseController {
    @Autowired
    private PfBusinessGroupService businessGroupService;
    @Autowired
    PfBusinessService businessService;

    @RequestMapping("json")
    @ResponseBody
    public Object json(Model model) {
        //组织菜单树，类似构建平台
        List<Ztree> treeList = businessService.getBusinessTree();
        return treeList;
    }

    @RequestMapping("group")
    public String groupManage(Model model) {
        return "/config/busi/busi-group-manager";
    }

    @RequestMapping("groupjson")
    @ResponseBody
    public Object groupJson(Model model) {
        //组织菜单树，类似构建平台
        List<Ztree> treeList = businessGroupService.getBusinessGroupTree();
        return treeList;
    }

    /*
    查询角色详细信息，以及角色下的用户列表
     */
    @RequestMapping("groupinfo")
    @ResponseBody
    public Object getGroupInfo(@RequestParam(value = "groupId", required = false) String groupId) {
        PfBusinessGroup group = businessGroupService.findById(groupId);
        List<PublicVo> relList= businessGroupService.getBusinessRelList(groupId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("group", group);
        result.put("relList", relList);
        return result;
    }
    /*
    添加或修改角色
     */
    @RequestMapping("savegroup")
    @ResponseBody
    public Object save(HttpServletRequest req,@ModelAttribute("group") PfBusinessGroup group, Model model){
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isBlank(group.getBusinessGroupId())){
           group.setBusinessGroupId(UUIDGenerator.generate18());
           List<PfBusinessGroup> tmpList =  businessGroupService.getGroupListByName(group.getBusinessGroupName());
            if(tmpList!=null&& tmpList.size()>0){
                return handlerErrorJson("要添加的分组名称已经存在！");
            }
        }

        PfBusinessGroup tmpObj = businessGroupService.findById(group.getBusinessGroupId());
        if (tmpObj != null){
            businessGroupService.updateDetail(group);
        }else {
            businessGroupService.insert(group);
        }

        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("group", group);
        return result;
    }
    /*
    删除角色及角色下的用户及用户关系
     */
    @RequestMapping("delgroup")
    @ResponseBody
    public Object delGroup(@RequestParam("groupId") String groupId){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(groupId)){
                businessGroupService.deleteById(groupId);
                return handlerSuccessJson();
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerErrorJson();
    }

    @RequestMapping("saveBusiGruopRel")
    @ResponseBody
    public Object saveBusiGruopRel(HttpServletRequest req,@RequestParam(value = "groupId", required = true)String groupId,String paramString){
        if (StringUtils.isNotBlank(paramString) && StringUtils.isNotBlank(groupId)){
//            System.out.println(paramString);
            List<ZtreeChanged> changeList = JSON.parseArray(paramString,ZtreeChanged.class);
            businessGroupService.updateBusinessIds(groupId,changeList);
        }
        return handlerSuccessJson();
    }
}