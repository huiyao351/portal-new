package com.gtis.portal.web.config;

import com.alibaba.fastjson.JSON;
import com.gtis.config.AppConfig;
import com.gtis.config.AppConfigPlaceholderConfigurer;
import com.gtis.config.EgovConfigLoader;
import com.gtis.portal.properties.PropertyEntry;
import com.gtis.portal.properties.SafeProperties;
import com.gtis.portal.util.CommonUtils;
import com.gtis.portal.util.RequestUtils;
import com.gtis.portal.web.BaseController;
import com.gtis.web.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


@Controller
@RequestMapping("config/pro")
public class PropertiesController extends BaseController {

    @RequestMapping("")
    public String manage(Model model, String paramString,String project) throws Exception{
        List<String> fileList = CommonUtils.getEgovhomeConfigFileList();
        if (StringUtils.isBlank(project)){
            project = fileList.get(0);
        }

        LinkedHashMap<String,String> propMap = new LinkedHashMap<String, String>();
        for (int i = 0; i < fileList.size(); i++) {
            String value = AppConfig.getProperty("config.reload."+fileList.get(i)+".url");
            if (StringUtils.isBlank(value)){
                //value = fileList.get(i);
            }else {
                value = RequestUtils.initOptProperties(value);
            }
            propMap.put(fileList.get(i), value);
        }

        List<PropertyEntry> proList = new ArrayList<PropertyEntry>();
        LinkedHashMap<String,PropertyEntry> entryMap = new LinkedHashMap<String, PropertyEntry>();
        if (SessionUtil.getCurrentUser().isAdmin() && StringUtils.isNotBlank(project)){
            try {
                String filePath = CommonUtils.getEgovhomeConfigFilePath(project);
                File file = new File(filePath);
                if (file.exists() && file.isFile()){
                    FileInputStream inputStream = new FileInputStream(file);
                    SafeProperties prop = new SafeProperties();
                    prop.load(inputStream);
                    prop.initConfigCommentList();
                    proList = prop.getProList();
                    entryMap = prop.getEntryMap();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        model.addAttribute("project",project);
        model.addAttribute("entryMap",entryMap);
        model.addAttribute("propMap",propMap);
        model.addAttribute("fileList",fileList);
        return "/config/properties/manage";
    }

    @RequestMapping("save")
    @ResponseBody
    public Object save(HttpServletRequest req, PropertyEntry propertyEntry,String project,String lastKey){
        if (SessionUtil.getCurrentUser().isAdmin() && propertyEntry != null && StringUtils.isNotBlank(propertyEntry.getKey()) && StringUtils.isNotBlank(project)){
            try {
                String comment = initComment(propertyEntry.getComment());
                propertyEntry.setComment(comment);

                String filePath = CommonUtils.getEgovhomeConfigFilePath(project);
                File file = new File(filePath);
                if (file.exists() && file.isFile()){
                    FileInputStream inputStream = new FileInputStream(file);
                    SafeProperties prop = new SafeProperties();
                    prop.load(inputStream);
                    prop.initConfigCommentList();
                    LinkedHashMap<String,PropertyEntry> entryMap = prop.getEntryMap();

                    //如果是采用插入的方式，则进行判断，当前配置项所在行，之后在行下面插入配置项，并且插入对应的注释
                    if (StringUtils.isNotBlank(lastKey)){
                        if (entryMap.containsKey(propertyEntry.getKey())){
                            return handlerErrorJson("已经存在该配置项："+propertyEntry.getKey()+"，请重新检查");
                        }
                        if (entryMap.containsKey(lastKey)){
                            PropertyEntry lastEntry = entryMap.get(lastKey);
                            PropertyEntry entry = new PropertyEntry(propertyEntry.getKey(),propertyEntry.getValue(),null,propertyEntry.getComment());
                            prop.getContext().getCommentOrEntrys().add(lastEntry.getIndex()+1,entry);
                            if (StringUtils.isNotBlank(propertyEntry.getComment())){
                                prop.getContext().getCommentOrEntrys().add(lastEntry.getIndex()+1,"");
                                prop.getContext().getCommentOrEntrys().add(lastEntry.getIndex()+2,propertyEntry.getComment());
                            }
                            FileOutputStream output = new FileOutputStream(filePath);
                            prop.store(output, null);
                            output.close();
                        }
                    }else {
                        if (entryMap.containsKey(propertyEntry.getKey())){
                            PropertyEntry entry = entryMap.get(propertyEntry.getKey());
                            entry.setValue(propertyEntry.getValue());
                            entry.setComment(propertyEntry.getComment());
                            int length = entry.getLineNum();
                            if (length > 0){
                                prop.getContext().getCommentOrEntrys().add(entry.getLastIndex(),"");
                                prop.getContext().getCommentOrEntrys().add(entry.getIndex()+1,entry.getComment());
                            }else{
                                if (StringUtils.isNotBlank(entry.getComment())){
                                    prop.getContext().getCommentOrEntrys().add(entry.getLastIndex(),"");
                                    prop.getContext().getCommentOrEntrys().add(entry.getIndex()+1,entry.getComment());
                                }
                            }
                            for (int i = 0; i < length; i++) {
                                prop.getContext().getCommentOrEntrys().remove(entry.getLastIndex()+1);
                            }
                            prop.put(entry.getKey(), entry.getValue());
                            FileOutputStream output = new FileOutputStream(filePath);
                            prop.store(output, null);
                            output.close();
                        }
                    }
                    //更新当前配置项（只针对当前系统运行所加载的配置文件有效，其余系统则需要实现下面的refresh方法）
                    Map appProperties = AppConfig.getProperties();//new HashMap();
                    appProperties.put(propertyEntry.getKey(), propertyEntry.getValue());
                    AppConfig.setConfiguration(appProperties);

                    System.setProperty(propertyEntry.getKey(), propertyEntry.getValue());
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }


        return handlerSuccessJson();
    }

    /**
     * 该方法实现了后台请求url地址来实现配置的刷新，功能由业务系统实现，
     * 业务系统需要添加cas过滤，以便于当前方法能够通过url请求调用到
     * @param req
     * @param project
     * @return
     */
    @RequestMapping("refresh")
    @ResponseBody
    public Object refresh(HttpServletRequest req,String project){
        if (StringUtils.isNotBlank(project)){
            final String projectUrl = RequestUtils.initOptProperties(AppConfig.getProperty("config.reload."+project+".url"));
            System.out.println(projectUrl);
            if (StringUtils.isNotBlank(projectUrl)){
                try {
                    URL url = new URL(projectUrl);
                    HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
                    urlcon.connect();         //获取连接
                    InputStream is = urlcon.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
                    StringBuffer bs = new StringBuffer();
                    String l = null;
                    while((l=buffer.readLine())!=null){
                        bs.append(l).append("/n");
                    }
                    System.out.println(bs.toString());
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                //采用线程的方式调用
                /*
                Thread t = new Thread(new Runnable(){
                    public void run(){
                        try {
                            URL url = new URL(projectUrl);
                            HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
                            urlcon.connect();         //获取连接
                            InputStream is = urlcon.getInputStream();
                            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
                            StringBuffer bs = new StringBuffer();
                            String l = null;
                            while((l=buffer.readLine())!=null){
                                bs.append(l).append("/n");
                            }
                            System.out.println(bs.toString());
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }});
                t.start();*/
            }else {
                return handlerErrorJson("请配置该业务系统【"+project+"】对应的配置加载地址：config.reload."+project+".url！");
            }
        }
        return handlerSuccessJson();
    }

    private String initComment(String oldCom){
        String comment = "";
        if (StringUtils.isNotBlank(oldCom)){
            oldCom = StringUtils.removeEnd(oldCom,"\n");
            oldCom = StringUtils.replace(oldCom," ","");
            oldCom = StringUtils.replace(oldCom,"　","");
            String[] coms = StringUtils.split(oldCom,"\n");
            for (int i = 0; i < coms.length; i++) {
                if (StringUtils.isNotBlank(coms[i])){
                    if (!StringUtils.startsWith(coms[i],"#")){
                        comment += "#"+coms[i];
                    }else {
                        comment += coms[i];
                    }
                    comment += "\r\n";
                }
            }
        }
        comment = StringUtils.removeEnd(comment,"\r\n");
        comment = StringUtils.removeStart(comment,"\r\n");
        return comment;
    }
}