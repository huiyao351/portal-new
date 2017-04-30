package com.gtis.portal.web.config;

import com.alibaba.fastjson.JSON;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.*;
import com.gtis.portal.util.RequestUtils;
import com.gtis.portal.web.BaseController;
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
@RequestMapping("config/parti")
public class PartitionController extends BaseController {
    @Autowired
    PfResourceService resourceService;
    @Autowired
    PfResourceGroupService groupService;
    @Autowired
    PfResourcePartitionService resourcePartitionService;
    @Autowired
    PfPartitionInfoService partitionInfoService;

    @Resource
    @Qualifier("boolListNumber")
    List<PublicVo> boolListNumber;
    @Resource
    @Qualifier("priorityList")
    List<PublicVo> priorityList;
    @Resource
    @Qualifier("partitionTypeList")
    List<PublicVo> partitionTypeList;

    @RequestMapping("")
    public String manage(Model model,String rId) {
        model.addAttribute("partitionTypeList",partitionTypeList);
        model.addAttribute("partitionTypeJson", JSON.toJSONString(partitionTypeList));

        List<PfResourcePartition> rpList = resourcePartitionService.getListByRid(rId,0);
        List<PfPartitionInfo> piList = new ArrayList<PfPartitionInfo>();
        if (rpList != null && rpList.size() > 0){
            model.addAttribute("pId",rpList.get(0).getPartitionId());
            piList = partitionInfoService.getListByPid(rpList.get(0).getPartitionId());
        }else {
            model.addAttribute("pId","");
            rpList = new ArrayList<PfResourcePartition>();
        }
        if (piList == null){
            piList = new ArrayList<PfPartitionInfo>();
        }else if(piList.size() > 0){
            model.addAttribute("piId",piList.get(0).getPfPartitionInfoId());
        }
        model.addAttribute("rpList",rpList);
        model.addAttribute("piList",piList);
        model.addAttribute("rId",rId);
        return "/config/parti/parti-list";
    }

    @RequestMapping("info")
    @ResponseBody
    public PfResourcePartition getWorkflowDefinition(@RequestParam(value = "keyId", required = false) String keyId) {
        PfResourcePartition rp = resourcePartitionService.findById(keyId);
        return rp == null ? new PfResourcePartition() : rp;
    }

    @RequestMapping("save")
    @ResponseBody
    public Object save(HttpServletRequest req,@ModelAttribute("rp") PfResourcePartition rp, Model model){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("isadd", true);
        if (StringUtils.isBlank(rp.getPartitionId())){
            rp.setPartitionId(UUIDGenerator.generate18());
        }

        if (StringUtils.isNotBlank(rp.getResourceId())){
            PfResourcePartition tmprp = resourcePartitionService.findById(rp.getPartitionId());
            if (tmprp == null){
                resourcePartitionService.insert(rp);
            }else {
                rp.setPartitionType(tmprp.getPartitionType());
                resourcePartitionService.update(rp);
                result.put("isadd", false);
            }
        }
        rp = resourcePartitionService.findById(rp.getPartitionId());

        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("rp", rp);
        return result;
    }
    @RequestMapping("del")
    @ResponseBody
    public Object del(@RequestParam(value = "keyId", required = false) String keyId){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(keyId)){
                resourcePartitionService.deleteRPAndPIByIds(keyId);
                return handlerSuccessJson();
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerSuccessJson();
    }

    @RequestMapping("jsonPI")
    @ResponseBody
    public Object jsonPartitionInfo(Model model,@RequestParam(value = "pId", required = false) String pId) {
        //组织菜单树，类似构建平台
        List<PfPartitionInfo> piList = partitionInfoService.getListByPid(pId);
        if (piList == null){
            piList = new ArrayList<PfPartitionInfo>();
        }
        return piList;
    }

    @RequestMapping("infoPI")
    @ResponseBody
    public PfPartitionInfo getPartitionInfo(@RequestParam(value = "keyId", required = false) String keyId) {
        PfPartitionInfo pt = partitionInfoService.findById(keyId);
        return pt == null ? new PfPartitionInfo() : pt;
    }

    @RequestMapping("savePI")
    @ResponseBody
    public Object savePartitionInfo(HttpServletRequest req,PfPartitionInfo pi, Model model){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("isadd", true);
        if (StringUtils.isBlank(pi.getPfPartitionInfoId())){
            pi.setPfPartitionInfoId(UUIDGenerator.generate18());
        }
        if (StringUtils.isNotBlank(pi.getPartitionId())){
            PfPartitionInfo tmprp = partitionInfoService.findById(pi.getPfPartitionInfoId());
            if (tmprp == null){
                partitionInfoService.insert(pi);
            }else {
                pi.setElementJs(tmprp.getElementJs());
                pi.setElementIcon(tmprp.getElementIcon());
                partitionInfoService.update(pi);
                result.put("isadd", false);
            }
        }
        pi = partitionInfoService.findById(pi.getPfPartitionInfoId());

        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("pi", pi);
        return result;
    }
    @RequestMapping("delPI")
    @ResponseBody
    public Object delPartitionInfo(@RequestParam(value = "keyId", required = false) String keyId){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(keyId)){
                partitionInfoService.deleteByIds(keyId);
                return handlerSuccessJson();
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerSuccessJson();
    }
}
