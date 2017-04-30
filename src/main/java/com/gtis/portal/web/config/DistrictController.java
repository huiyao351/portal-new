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

import com.gtis.common.util.UUIDGenerator;
import com.gtis.plat.vo.UserInfo;
import com.gtis.portal.entity.PfDistrict;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfDistrictService;
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
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("config/district")
public class DistrictController extends BaseController {
    @Autowired
   private  PfDistrictService districtService;

    @RequestMapping("")
    public String manage(Model model) {
        return "/config/district/manager";
    }
    /*
    地区树
     */
    @RequestMapping("json")
    @ResponseBody
    public Object districtjson(Model model) {
        //组织菜单树，类似构建平台
        UserInfo userInfo = SessionUtil.getCurrentUser();
        String regionCode = SessionUtil.getCurrentUser().getRegionCode();
        if (userInfo.isAdmin()){
            regionCode = null;
        }
        Ztree ztree = districtService.getAllDistrictTree(regionCode);
        return ztree;
    }

    /**
     * 行政区下拉框树
     */
    @RequestMapping("districtList")
    @ResponseBody
    public Object getDistrictList(Model model) {
        //组织菜单树，类似构建平台
        UserInfo userInfo = SessionUtil.getCurrentUser();
        String regionCode = SessionUtil.getCurrentUser().getRegionCode();
        if (userInfo.isAdmin()){
            regionCode = null;
        }
        List<PfDistrict> districtList = districtService.getAllDistrictList(regionCode);
        return districtList;
    }

    /*
    行政区详细信息
     */
    @RequestMapping("info")
    @ResponseBody
    public PfDistrict getDistrict(@RequestParam(value = "districtId", required = false) String districtId) {
        PfDistrict district = districtService.findById(districtId);
        return district == null ? new PfDistrict() : district;
    }

    @RequestMapping("save")
    @ResponseBody
    public Object save(HttpServletRequest req,@ModelAttribute("district") PfDistrict district, Model model){
        if (StringUtils.isBlank(district.getDistrictId())){
            district.setDistrictId(UUIDGenerator.generate18());
        }
        PfDistrict districttmp = districtService.findById(district.getDistrictId());
        if (districttmp != null){
            districtService.update(district);
        }else {
            districtService.insert(district);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("district", district);
        return result;
    }

    @RequestMapping("del")
    @ResponseBody
    public Object del(@ModelAttribute("district") PfDistrict district){
        if (RequestUtils.checkIsAdmin()){
            if (district!=null){
                districtService.deleteDistrict(district);
                return handlerSuccessJson();
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerErrorJson();
    }
}