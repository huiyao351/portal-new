package com.gtis.portal.web.config;

import com.gtis.config.AppConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.*;

/**
 * @文件说明
 * @作者 deery
 * @创建日期 15:34
 * @版本号 V 1.0
 */
@Controller
@RequestMapping("config/reloadProp")
public class ReloadPropertiesController{

    @RequestMapping("")
    @ResponseBody
    public Object execute(Model model) throws Exception{
        String[] projects = {"egov","platform","portal"};
        Properties properties = new Properties();
        Map appProperties = AppConfig.getProperties();
        for (int i = 0; i < projects.length; i++) {
            String project = projects[i];
            if (StringUtils.isNotBlank(project)){
                try {
                    String filePath = getEgovhomeConfigFilePath(project);
                    File file = new File(filePath);
                    if (file.exists() && file.isFile()){
                        FileInputStream inputStream = new FileInputStream(file);
                        Properties prop = new Properties();
                        prop.load(inputStream);
                        properties.putAll(prop);
                        inputStream.close();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        Enumeration en = properties.propertyNames(); //得到配置文件的名字
        while(en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String newValue = initOptProperties(properties.getProperty(key),properties);
            String oldValue = initOptProperties(AppConfig.getProperty(key),properties);
            if (!StringUtils.equals(newValue,oldValue)){
                System.setProperty(key, newValue);
                appProperties.put(key, newValue);
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        return result;
    }

    /**
     * 获取系统对应的egovhome配置文件路径
     * @param project
     * @return
     * @throws Exception
     */
    private String getEgovhomeConfigFilePath(String project)throws Exception{
        String path = AppConfig.getProperty("egov.conf");
        path = java.net.URLDecoder.decode(path, "UTF-8");
        path = path.replace("file:/", "");
        String filePath = path;
        if (StringUtils.equalsIgnoreCase("egov",project)){
            filePath += "egov.properties";
        }else if (StringUtils.equalsIgnoreCase("gis",project)){
            filePath += "gis.properties";
        }else {
            filePath += project+"/application.properties";
        }
        return filePath;
    }

    /**
     * 格式化url地址为全路径地址
     * @param url
     * @return
     */
    private String initOptProperties(String url,Properties properties) {
        if (url != null) {
            PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("${", "}");
            url = propertyPlaceholderHelper.replacePlaceholders(url, properties);
        }

        return url;
    }
}