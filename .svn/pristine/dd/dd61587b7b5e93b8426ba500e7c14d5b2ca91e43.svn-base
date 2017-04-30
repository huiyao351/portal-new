package com.gtis.portal.web.config;

import com.gtis.common.util.UUIDGenerator;
import com.gtis.portal.entity.PfBusiness;
import com.gtis.portal.entity.PfStuffConfig;
import com.gtis.portal.entity.PfWorkflowDefinition;
import com.gtis.portal.entity.PublicVo;
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
@RequestMapping("config/stuff")
public class StuffConfigController extends BaseController {
    @Autowired
    PfStuffConfigService stuffConfigService;

    @Resource
    @Qualifier("boolListNumber")
    List<PublicVo> boolListNumber;
    @Resource
    @Qualifier("meterialList")
    List<PublicVo> meterialList;

    /**
     * 树结构页面
     * @param model
     * @param wfdId
     * @return
     */
    @RequestMapping("")
    public String manage(Model model,@RequestParam(value = "wfdId", required = true)String wfdId) {
        model.addAttribute("meterialList",meterialList);
        model.addAttribute("wfdId", wfdId);
        return "/config/stuff/stuff-tree";
    }

    /**
     * 列表结构
     * @param model
     * @param wfdId
     * @return
     */
    @RequestMapping("list")
    public String list(Model model,@RequestParam(value = "wfdId", required = true)String wfdId) {
        model.addAttribute("meterialList",meterialList);
        List<PfStuffConfig> stuffList = stuffConfigService.getListByWfdId(wfdId);
        if (stuffList == null){
            stuffList = new ArrayList<PfStuffConfig>();
        }
        model.addAttribute("stuffList", stuffList);
        model.addAttribute("wfdId", wfdId);
        return "/config/stuff/stuff-list";
    }

    @RequestMapping("json")
    @ResponseBody
    public Object stuffjson(Model model,@RequestParam(value = "wfdId", required = true)String wfdId) {
        //组织附件树
        Ztree ztree = stuffConfigService.getZtreeByWfdId(wfdId);
        return ztree;
    }

    @RequestMapping("info")
    @ResponseBody
    public PfStuffConfig getWorkflowDefinition(@RequestParam(value = "keyId", required = false) String keyId) {
        PfStuffConfig stuffConfig = stuffConfigService.findById(keyId);
        return stuffConfig == null ? new PfStuffConfig() : stuffConfig;
    }

    @RequestMapping("save")
    @ResponseBody
    public Object save(HttpServletRequest req,@ModelAttribute("stuffConfig") PfStuffConfig stuffConfig,@RequestParam(value = "wfdId", required = false)String wfdId, Model model){
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isBlank(stuffConfig.getStuffId())){
            stuffConfig.setStuffId(UUIDGenerator.generate18());
        }
        PfStuffConfig tmpStuff = stuffConfigService.findById(stuffConfig.getStuffId());
        if (tmpStuff == null){
            stuffConfigService.insert(stuffConfig);
            result.put("insert", true);
        }else {
            //stuffConfig.setProId(tmpStuff.getProId());
            stuffConfigService.update(stuffConfig);
        }
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("stuffConfig", stuffConfig);
        return result;
    }

    @RequestMapping("del")
    @ResponseBody
    public Object del(@RequestParam(value = "keyId", required = false) String keyId){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(keyId)){
                stuffConfigService.deleteStuffAndChildById(keyId);
                return handlerSuccessJson();
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerSuccessJson();
    }
}
