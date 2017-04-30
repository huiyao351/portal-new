package com.gtis.portal.web.config;

import com.alibaba.fastjson.JSON;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.config.AppConfig;
import com.gtis.config.PropertyPlaceholderHelper;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfBusinessService;
import com.gtis.portal.service.PfResourceGroupService;
import com.gtis.portal.service.PfResourceService;
import com.gtis.portal.service.PfWorkflowDefinitionService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
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
@RequestMapping("config/workflow")
public class WorkflowController extends BaseController {
    @Autowired
    PfResourceService resourceService;
    @Autowired
    PfResourceGroupService groupService;
    @Autowired
    PfWorkflowDefinitionService workflowDefinitionService;
    @Autowired
    PfBusinessService businessService;

    @Resource
    @Qualifier("boolListNumber")
    List<PublicVo> boolListNumber;
    @Resource
    @Qualifier("priorityList")
    List<PublicVo> priorityList;
    @Resource
    @Qualifier("databaseList")
    List<PublicVo> databaseList;

    @RequestMapping("")
    public String manage(Model model) {
        model.addAttribute("boolListNumber",boolListNumber);
        model.addAttribute("priorityList",priorityList);
        model.addAttribute("databaseList",databaseList);
        return "/config/workflow/manage";
    }

    @RequestMapping("json")
    @ResponseBody
    public Object resourcejson(Model model) {
        if (SessionUtil.getCurrentUser().isAdmin()){
            //组织菜单树，类似构建平台
            Ztree ztree = workflowDefinitionService.getAllWfdTree();
            return ztree;
        }
        return null;
    }

    @RequestMapping("info")
    @ResponseBody
    public PfWorkflowDefinition getWorkflowDefinition(@RequestParam(value = "keyId", required = false) String keyId) {
        PfWorkflowDefinition wfd = workflowDefinitionService.findById(keyId);
        return wfd == null ? new PfWorkflowDefinition() : wfd;
    }

    @RequestMapping("image")
    @ResponseBody
    public void image(@RequestParam(value = "keyId", required = false) String keyId, HttpServletResponse response) {
        PfWorkflowDefinition wfd = workflowDefinitionService.findById(keyId);
        if (wfd != null && wfd.getWorkflowImage() != null){
            Blob blob = wfd.getWorkflowImage();
            InputStream in = null;
            try {
                in = blob.getBinaryStream();
                OutputStream out = response.getOutputStream();
                byte[] data = new byte[(int)blob.length()];
                int i = 0;
                while ((i=in.read(data))!=-1){
                    out.write(data);
                }
                out.close();
                in.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("save")
    @ResponseBody
    public Object save(HttpServletRequest req,@ModelAttribute("wfd") PfWorkflowDefinition wfd, Model model){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(wfd.getWorkflowDefinitionId())){
                workflowDefinitionService.update(wfd);
            }
            wfd = workflowDefinitionService.findById(wfd.getWorkflowDefinitionId());
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("success", true);
            result.put("msg", "操作成功！");
            result.put("wfd", wfd);
            return result;
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
    }

    @RequestMapping("infoBs")
    @ResponseBody
    public PfBusiness getBusiness(@RequestParam(value = "keyId", required = false) String keyId) {
        PfBusiness bs = businessService.findById(keyId);
        return bs == null ? new PfBusiness() : bs;
    }

    @RequestMapping("saveBs")
    @ResponseBody
    public Object saveBusiness(HttpServletRequest req,PfBusiness business, Model model){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(business.getBusinessId())){
                businessService.update(business);
            }
            business = businessService.findById(business.getBusinessId());
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("success", true);
            result.put("msg", "操作成功！");
            result.put("business", business);
            return result;
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
    }
}
