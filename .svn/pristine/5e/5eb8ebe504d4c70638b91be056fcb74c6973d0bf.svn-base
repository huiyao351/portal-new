package com.gtis.portal.util;

import com.alibaba.fastjson.JSON;
import com.gtis.config.AppConfig;
import com.gtis.fileCenter.model.Node;
import com.gtis.fileCenter.service.NodeService;
import com.gtis.plat.service.SysStuffService;
import com.gtis.portal.entity.PfStuffConfig;
import com.gtis.portal.service.PfStuffConfigService;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @文件说明
 * @作者 deery
 * @创建日期 上午 9:58
 * @版本号 V 1.0
 */
public class CommonUtils {
    public static Map urlMap;
    public static Object readJsonFile(String path) throws Exception {
        if(StringUtils.isNotBlank(path)){
            if(path.indexOf("file:/")>-1)
                path = path.substring(path.indexOf("file:/")+6);
            File file = new File(path);
            if(!file.exists())
                throw new FileNotFoundException();
            BufferedReader bufferedReader=null;
            StringBuffer config = new StringBuffer();
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                String tmp=null;
                while ((tmp=bufferedReader.readLine())!=null){
                    config.append(tmp);
                }
            }finally {
                if(bufferedReader!=null)
                    bufferedReader.close();
            }
            if(StringUtils.isNotBlank(config.toString())){
                return JSON.parse(config.toString());
            }
        }
        return null;
    }

    public static String getEgovhomeConfigFilePath(String project)throws Exception{
        //System.out.println(AppConfig.getEgovHome());
        //System.out.println(AppConfig.getProperty("egov.conf"));
        //file:/D:/tomcat/apache-tomcat-onemap/tomcat-7.0.23-x32-std/egov-home/std/
        //file:/D:/tomcat/apache-tomcat-onemap/tomcat-7.0.23-x32-std/egov-home/std/conf/
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

    public static File getEgovhomeConfigFile(String project)throws Exception{
        String filePath = getEgovhomeConfigFilePath(project);
        File file = new File(filePath);
        if (file.exists() && file.isFile()){
            return file;
        }
        return null;
    }

    /**
     * 获取egovhome下所有的系统配置文件夹及根目录下的配置文件，只处理properties配置文件
     * @return
     * @throws Exception
     */
    public static List<String> getEgovhomeConfigFileList()throws Exception{
        List<String> fileList = new ArrayList<String>();
        String path = AppConfig.getProperty("egov.conf");
        path = java.net.URLDecoder.decode(path, "UTF-8");
        path = path.replace("file:/", "");
        File dir = new File(path);
        if (dir.exists()){
            dir.listFiles();
            //如果是目录，则：
            if(dir.isDirectory()){
                //打印当前目录的路径
                //System.out.println(dir);
                //获取该目录下的所有文件和目录组成的File数组
                File[] files = dir.listFiles();
                //递归遍历每一个子文件
                for(File file : files){
                    String fileName = file.getName();
                    if (file.isDirectory()){
                        if(fileName.matches("^[A-Za-z]+$")){
                            fileList.add(fileName);
                        }
                    }else {
                        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
                        if (StringUtils.equalsIgnoreCase(prefix,"properties")){
                            fileName = StringUtils.split(fileName,".")[0];
                            if(fileName.matches("^[A-Za-z]+$")){
                                fileList.add(0,fileName);
                            }
                        }
                    }
                }
            }
        }
        int egovIndex = fileList.indexOf("egov");
        fileList.remove(egovIndex);
        fileList.add(0,"egov");
        return fileList;
    }

    /**
     * 获得工作流定义中的附件类型(初始化文件中心附件文件夹)
     * @param nodeService
     * @param stuffConfigService
     * @param workflowDefinitionId
     * @param fileCenterNodeId
     */
    public static void initWorkflowFileCenter(NodeService nodeService, PfStuffConfigService stuffConfigService, String workflowDefinitionId, int fileCenterNodeId){
        //获得工作流定义中的附件类型，用于待办任务打开时，初始化待办任务的附件管理页面
        List<PfStuffConfig> configList = stuffConfigService.getListByWfdId(workflowDefinitionId);
        if (configList != null && configList.size() > 0){
            HashMap<String,PfStuffConfig> stuffMap = new HashMap<String, PfStuffConfig>();
            for (int i = 0; i < configList.size(); i++) {
                PfStuffConfig configTmp =  configList.get(i);

                PfStuffConfig stuff = new PfStuffConfig();
                stuff.setStuffId(configTmp.getStuffId());
                stuff.setProId(configTmp.getProId());
                stuff.setStuffName(configTmp.getStuffName());
                stuffMap.put(stuff.getStuffId(),stuff);

                if (StringUtils.isBlank(stuff.getProId())){
                    Node stuffNode = nodeService.getNode(fileCenterNodeId,stuff.getStuffName(),true);
                    //用xh字段临时代替文件中心节点id，用于下面获取父节点id的调用
                    stuff.setStuffXh(stuffNode.getId());
                    stuffMap.put(stuff.getStuffId(),stuff);
                }else {
                    PfStuffConfig parentVo = stuffMap.get(stuff.getProId());
                    if (parentVo != null && parentVo.getStuffXh() != null){
                        //此处父节点id使用的就是上面的临时赋值字段stuffxh
                        Node stuffNode = nodeService.getNode(parentVo.getStuffXh(),stuff.getStuffName(),true);
                        stuff.setStuffXh(stuffNode.getId());
                    }else {
                        Node stuffNode = nodeService.getNode(fileCenterNodeId,stuff.getStuffName(),true);
                        stuff.setStuffXh(stuffNode.getId());
                        stuffMap.put(stuff.getStuffId(),stuff);
                    }
                }
            }
        }
    }
}
