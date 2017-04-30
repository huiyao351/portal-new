/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.gtis.portal.web;


import com.gtis.config.AppConfig;
import com.gtis.portal.ex.UserException;
import com.gtis.portal.service.ExceptionService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2014/6/23
 */
public class BaseController extends SimpleFormController {
    @Autowired
    public ExceptionService exceptionService;

    protected final Logger logger = Logger.getLogger(getClass());

    private String version = AppConfig.getProperty("portal.version");

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String, ?> handlerJSONException(final Exception e, final HttpServletRequest request) throws Exception {
        logger.error(exceptionService.getExceptionMsg(e.getLocalizedMessage()));
        Map<String, Object> result=null;
        if(e instanceof UserException){
            result = new HashMap<String, Object>();
            result.put("success", false);
            result.put("msg", exceptionService.getExceptionMsg(e.getLocalizedMessage()));
        }else{
            throw e;
        }

        return result;
    }



    public Object handlerSuccessJson(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        return result;
    }

    public Object handlerErrorJson(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", false);
        result.put("msg", "操作失败！");
        return result;
    }

    public Object handlerErrorJson(String msg){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", false);
        result.put("msg", msg);
        return result;
    }

    public String returnPage(String page){
        if (StringUtils.isNotBlank(version)){
            page = version +"/"+page;
        }
        return page;
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
        binder.registerCustomEditor(Date.class, dateEditor);
        super.initBinder(request, binder);
    }
}
