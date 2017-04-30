package com.gtis.portal.web.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.*;
import com.gtis.portal.util.RequestUtils;
import com.gtis.portal.web.BaseController;
import org.apache.commons.collections.MapUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jibo
 * Date: 14-4-14
 * Time: 上午10:50
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("config/instAuth")
public class InstanceAuthController extends BaseController {
    @Autowired
    PfInstanceAuthorizeService instanceAuthorizeService;
    @Autowired
    PfResourcePartitionService resourcePartitionService;

    @Resource
    @Qualifier("partitionTypeList")
    List<PublicVo> partitionTypeList;
    @Resource
    @Qualifier("partitionOperatType")
    List<PublicVo> partitionOperatType;

    @RequestMapping("")
    public String manage(Model model,String wdid) {
        model.addAttribute("wdid",wdid);
        model.addAttribute("partitionTypeJson", JSON.toJSONString(partitionTypeList));
        model.addAttribute("partitionOperatTypeJson",JSON.toJSONString(partitionOperatType));
        return "/config/workflow/wfd-inst-auth";
    }

    @RequestMapping("rolejson")
    @ResponseBody
    public Object rolejson(Model model,String wdid) {
        //组织菜单树，类似构建平台
        List<Ztree> treeList = instanceAuthorizeService.getAuthorizeRoleTreeListByWdId(wdid);
        return treeList;
    }
    @RequestMapping("resourcejson")
    @ResponseBody
    public Object resourcejson(Model model,String wdid,String roleId,boolean ischeck) {
        //组织菜单树，类似构建平台
        List<Ztree> treeList = instanceAuthorizeService.getAuthorizeResourceTreeListByWdId(wdid, roleId,ischeck);
        return treeList;
    }

    @RequestMapping("partiInfo")
    @ResponseBody
    public List<PfResourcePartition> getPartiInfo(@RequestParam(value = "wdid", required = true)String wdid,@RequestParam(value = "roleId", required = true) String roleId,@RequestParam(value = "resourceId", required = true) String resourceId) {
        //获取实例权限中配置的功能分区操作类型
        List<PfResourcePartition> rpList = null;
        if (StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(roleId) && StringUtils.isNotBlank(resourceId)){
            HashMap<String,PfResourcePartition> partMap = instanceAuthorizeService.getInstancePartMap(wdid, roleId, resourceId);
            rpList = resourcePartitionService.getListByRid(resourceId);
            if (rpList != null && rpList.size() > 0){
                for (int i = 0; i < rpList.size(); i++) {
                    PfResourcePartition rp = rpList.get(i);
                    if (partMap.containsKey(rp.getPartitionId())){
                        //获取对应的功能分区，将操作类型赋值到临时字段
                        rp.setOperType(partMap.get(rp.getPartitionId()).getOperType());
                        rpList.set(i,rp);
                    }
                }
            }
        }
        return rpList == null ? new ArrayList<PfResourcePartition>() : rpList;
    }

    @RequestMapping("saveRel")
    @ResponseBody
    public Object saveRel(HttpServletRequest req,@RequestParam(value = "wdid", required = true)String wdid,String paramString){
        if (StringUtils.isNotBlank(paramString) && StringUtils.isNotBlank(wdid)){
            System.out.println(paramString);
            List<ZtreeChanged> changeList = JSON.parseArray(paramString,ZtreeChanged.class);
            instanceAuthorizeService.addRoleRel(wdid, changeList);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
//        result.put("wfd", wfd);
        return result;
    }

    @RequestMapping("delRel")
    @ResponseBody
    public Object delRel(@RequestParam(value = "wdid", required = true)String wdid,@RequestParam(value = "roleId", required = true) String roleId){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(roleId)){
                instanceAuthorizeService.deleteAuthorizeListByWdIdAndRole(wdid,roleId);
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerSuccessJson();
    }

    @RequestMapping("saveResourceRel")
    @ResponseBody
    public Object saveResourceRel(HttpServletRequest req,@RequestParam(value = "wdid", required = true)String wdid,String paramString){
        if (StringUtils.isNotBlank(paramString) && StringUtils.isNotBlank(wdid)){
            List<ZtreeChanged> changeList = JSON.parseArray(paramString,ZtreeChanged.class);
            instanceAuthorizeService.addResourceRel(wdid, changeList);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        return result;
    }

    @RequestMapping("updateResourceVisible")
    @ResponseBody
    public Object updateResourceVisible(HttpServletRequest req,@RequestParam(value = "wdid", required = true)String wdid,@RequestParam(value = "roleId", required = true) String roleId,String paramString){
        if (StringUtils.isNotBlank(paramString) && StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(roleId)){
            List<ZtreeChanged> changeList = JSON.parseArray(paramString,ZtreeChanged.class);
            instanceAuthorizeService.updateResourceRel(wdid, roleId, changeList);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        return result;
    }

    @RequestMapping("delResourceRel")
    @ResponseBody
    public Object delResourceRel(@RequestParam(value = "wdid", required = true)String wdid,@RequestParam(value = "roleId", required = true) String roleId,@RequestParam(value = "resourceId", required = true) String resourceId){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(roleId) && StringUtils.isNotBlank(resourceId)){
                instanceAuthorizeService.deleteAuthorizeListByWdIdAndResource(wdid, resourceId);
                return handlerSuccessJson();
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerSuccessJson();
    }

    @RequestMapping("updatePartOperType")
    @ResponseBody
    public Object updatePartOperType(HttpServletRequest req,@RequestParam(value = "wdid", required = true)String wdid,@RequestParam(value = "roleId", required = true) String roleId,@RequestParam(value = "resourceId", required = true) String resourceId,String paramString){
        if (StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(roleId) && StringUtils.isNotBlank(resourceId) && StringUtils.isNotBlank(paramString)){
            List<ZtreeChanged> changeList = JSON.parseArray(paramString,ZtreeChanged.class);
            instanceAuthorizeService.updatePartOperType(wdid,roleId,resourceId,changeList);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
//        result.put("wfd", wfd);
        return result;
    }
}
