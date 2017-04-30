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
import com.gtis.plat.vo.UserInfo;
import com.gtis.portal.entity.PfCalendar;
import com.gtis.portal.entity.PfMenu;
import com.gtis.portal.entity.PublicVo;
import com.gtis.portal.model.Menu;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfBusinessService;
import com.gtis.portal.service.PfCalendarService;
import com.gtis.portal.service.PfMenuService;
import com.gtis.portal.service.PfResourceService;
import com.gtis.portal.util.CalendarUtil;
import com.gtis.portal.util.RequestUtils;
import com.gtis.portal.web.BaseController;
import com.gtis.web.SessionUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@RequestMapping("config/cal")
public class CalendarController extends BaseController {
    @Autowired
    PfCalendarService pfCalendarService;

    @Resource
    @Qualifier("calTypeList")
    List<PublicVo> calTypeList;
    @Resource
    @Qualifier("calWeekList")
    List<PublicVo> calWeekList;

    List<PublicVo> yearList;
    @Resource
    @Qualifier("monthList")
    List<PublicVo> monthList;

    @RequestMapping("")
    public String manage(Model model, Pageable page, String paramString) {
        Integer year = CalendarUtil.getYear();
        Integer month = CalendarUtil.getMonth();
        if (StringUtils.isNotBlank(paramString)){
            HashMap param = JSON.parseObject(paramString,HashMap.class);
            if (param.containsKey("year") && MapUtils.getInteger(param,"year") != null){
                year = MapUtils.getInteger(param,"year");
            }
            if (param.containsKey("month") && MapUtils.getInteger(param,"month") != null){
                month = MapUtils.getInteger(param,"month");
            }
        }
        List<PfCalendar> calendarList = pfCalendarService.getCalendarListByYM(year,month);
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        model.addAttribute("calendarList",calendarList);
        model.addAttribute("calendar",new PfCalendar());

        model.addAttribute("calTypeList",calTypeList);
        model.addAttribute("calWeekList",calWeekList);

        yearList = new ArrayList<PublicVo>();
        PublicVo publicVo = new PublicVo("","---年份---");
        yearList.add(publicVo);
        for (int i = year-10; i < year + 10; i++) {
            publicVo = new PublicVo(String.valueOf(i),String.valueOf(i)+"年");
            yearList.add(publicVo);
        }

        model.addAttribute("yearList",yearList);
        model.addAttribute("monthList",monthList);
        return "/config/cal/manage";
    }

    @RequestMapping("open")
    @ResponseBody
    public PfCalendar open1(@RequestParam(value = "calId", required = false) String calId) {
        PfCalendar calendar = pfCalendarService.findInitCalendar(calId);
        return calendar == null ? new PfCalendar() : calendar;
    }

    @RequestMapping("save")
     @ResponseBody
     public Object save(HttpServletRequest req,@ModelAttribute("calendar") PfCalendar calendar, Model model){
        if (StringUtils.isBlank(calendar.getCalId())){
            calendar.setCalId(UUIDGenerator.generate18());
        }
        PfCalendar tmpCalendar = pfCalendarService.findById(calendar.getCalId());
        if (tmpCalendar != null){
            calendar.setAmBegin(CalendarUtil.formateDateByHMDate(CalendarUtil.formateDatetoStr(calendar.getCalDate())+" "+calendar.getAmBeginStr()));
            calendar.setAmEnd(CalendarUtil.formateDateByHMDate(CalendarUtil.formateDatetoStr(calendar.getCalDate())+" "+calendar.getAmEndStr()));
            calendar.setPmBegin(CalendarUtil.formateDateByHMDate(CalendarUtil.formateDatetoStr(calendar.getCalDate())+" "+calendar.getPmBeginStr()));
            calendar.setPmEnd(CalendarUtil.formateDateByHMDate(CalendarUtil.formateDatetoStr(calendar.getCalDate())+" "+calendar.getPmEndStr()));
            pfCalendarService.update(calendar);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("calendar", calendar);
        return result;
    }

    @RequestMapping("create")
    @ResponseBody
    public Object createCal(@RequestParam(value = "year", required = true) Integer year,@RequestParam(value = "month", required = false) Integer month){
        String msg = "";
        if (RequestUtils.checkIsAdmin()){
            msg = pfCalendarService.createCalByYear(year);
        }else{
            msg = exceptionService.getExceptionMsg("9003");
        }
        if (StringUtils.isBlank(msg)){
            return handlerSuccessJson();
        }
        return handlerErrorJson(msg);
    }

    @RequestMapping("del")
    @ResponseBody
    public Object del(@RequestParam(value = "year", required = true) Integer year,@RequestParam(value = "month", required = false) Integer month){
        if (RequestUtils.checkIsAdmin()){
            pfCalendarService.deleteCalByYearMonth(year);
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerSuccessJson();
    }
}