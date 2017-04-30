package com.gtis.portal.web.config;

import com.alibaba.fastjson.JSON;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.config.AppConfig;
import com.gtis.config.PropertyPlaceholderHelper;
import com.gtis.generic.util.JsonUtils;
import com.gtis.portal.entity.PfMenu;
import com.gtis.portal.entity.PfResource;
import com.gtis.portal.entity.PfResourceGroup;
import com.gtis.portal.entity.PublicVo;
import com.gtis.portal.model.Menu;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfMenuService;
import com.gtis.portal.service.PfResourceGroupService;
import com.gtis.portal.service.PfResourceService;
import com.gtis.portal.util.RequestUtils;
import com.gtis.portal.web.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jibo
 * Date: 14-4-14
 * Time: 上午10:50
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("config/resource")
public class ResourceController extends BaseController {
    @Autowired
    PfResourceService resourceService;
    @Autowired
    PfResourceGroupService groupService;

    @Resource
    @Qualifier("zylxList")
    List<PublicVo> zylxList;

    @Resource
    @Qualifier("zyjzmsList")
    List<PublicVo> zyjzmsList;

    @RequestMapping("")
    public String manage(Model model) {
        model.addAttribute("zylxList",zylxList);
        model.addAttribute("zyjzmsList",zyjzmsList);
        return "/config/resource/manage";
    }

    @RequestMapping("select")
    public String select(Model model,@RequestParam(value = "foreignId", required = true)String foreignId,String paramString) {
        model.addAttribute("foreignId",foreignId);
        model.addAttribute("paramString",paramString);
        return "/config/resource/resource-select";
    }

    @RequestMapping("json")
    @ResponseBody
    public Object resourcejson(Model model,@RequestParam(value = "hascheck", required = false)String hascheck) {
        //组织菜单树，类似构建平台
        Ztree ztree = resourceService.getAllResourceTree(hascheck);
//        System.out.println(JSON.toJSONString(ztree));
        return ztree;
    }

    /**
     * 首页导航菜单点击，获取资源信息
     * @param link
     * @return
     */
    @RequestMapping("open")
    @ResponseBody
    public PfResource openMenu(@RequestParam String link) {
        if (link.startsWith("r:")){
            return handleResourceUrl(resourceService.findById(link.replace("r:", "")));
        }else{
            return handleResourceUrl(resourceService.findById(link));
        }
    }

    private PfResource handleResourceUrl(PfResource pfResource){
        if (pfResource != null){
            if(StringUtils.isNotBlank(pfResource.getResourceUrl())){
                pfResource.setResourceUrl(RequestUtils.initOptProperties(pfResource.getResourceUrl()));
            }
        }
        return pfResource;
    }

    @RequestMapping("info")
    @ResponseBody
    public PfResource getResource(@RequestParam(value = "resourceId", required = false) String resourceId) {
        PfResource resource = resourceService.getResourceHasBs(resourceId);
        return resource == null ? new PfResource() : resource;
    }

    @RequestMapping("save")
    @ResponseBody
    public Object save(HttpServletRequest req,@ModelAttribute("resource") PfResource resource, Model model){
        if (StringUtils.isBlank(resource.getResourceId())){
            resource.setResourceId(UUIDGenerator.generate18());
        }
        PfResource tmpResource = resourceService.getResourceHasBs(resource.getResourceId());
        if (tmpResource != null){
            resourceService.update(resource);
        }else {
            resourceService.insert(resource);
        }
        resource = resourceService.getResourceHasBs(resource.getResourceId());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("resource", resource);
        return result;
    }

    @RequestMapping("del")
    @ResponseBody
    public Object del(@RequestParam(value = "keyId", required = false) String keyId){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(keyId)){
                resourceService.deleteById(keyId);
                return handlerSuccessJson();
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerSuccessJson();
    }

    @RequestMapping("infoGroup")
    @ResponseBody
    public PfResourceGroup getGroup(@RequestParam(value = "groupId", required = false) String groupId) {
        PfResourceGroup group = groupService.getGroupHasBs(groupId);
        return group == null ? new PfResourceGroup() : group;
    }

    @RequestMapping("saveGroup")
    @ResponseBody
    public Object saveGroup(HttpServletRequest req,@ModelAttribute("group") PfResourceGroup group, Model model){
        if (StringUtils.isBlank(group.getGroupId())){
            group.setGroupId(UUIDGenerator.generate18());
        }
        PfResourceGroup tmpGroup = groupService.getGroupHasBs(group.getGroupId());
        if (tmpGroup != null){
            groupService.update(group);
        }else {
            groupService.insert(group);
        }
        group = groupService.getGroupHasBs(group.getGroupId());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("group", group);
        return result;
    }

    @RequestMapping("delGroup")
    @ResponseBody
    public Object delGroup(@RequestParam(value = "keyId", required = false) String keyId,@RequestParam(value = "delResource", required = false) String delResource){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(keyId)){
                groupService.deleteGroup(keyId, StringUtils.equalsIgnoreCase("true", delResource) ? true : false);
                return handlerSuccessJson();
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerSuccessJson();
    }
}
