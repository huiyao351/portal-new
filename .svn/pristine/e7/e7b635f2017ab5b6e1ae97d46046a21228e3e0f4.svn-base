package com.gtis.portal.web;

import com.alibaba.fastjson.JSON;
import com.gtis.config.AppConfig;
import com.gtis.fileCenter.model.Node;
import com.gtis.fileCenter.model.Space;
import com.gtis.fileCenter.service.NodeService;
import com.gtis.plat.service.SysTaskService;
import com.gtis.plat.service.SysWorkFlowDefineService;
import com.gtis.plat.service.SysWorkFlowInstanceService;
import com.gtis.plat.vo.PfActivityVo;
import com.gtis.plat.vo.PfTaskVo;
import com.gtis.plat.vo.PfWorkFlowDefineVo;
import com.gtis.plat.vo.PfWorkFlowInstanceVo;
import com.gtis.plat.wf.model.ActivityModel;
import com.gtis.plat.wf.model.RiskModel;
import com.gtis.portal.entity.PfStuffConfig;
import com.gtis.portal.model.Menu;
import com.gtis.portal.service.PfResourceService;
import com.gtis.portal.service.PfStuffConfigService;
import com.gtis.portal.util.*;
import com.gtis.web.SessionUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 任务办理中心
 */
@Controller
@RequestMapping("/taskHandle")
public class TaskHandleController extends BaseController {
    @Autowired
    SysWorkFlowDefineService workFlowDefineService;
    @Autowired
    NodeService nodeService;
    @Autowired
    SysWorkFlowInstanceService workFlowIntanceService;
    @Autowired
    SysTaskService taskService;
    @Autowired
    PfStuffConfigService stuffConfigService;
    @Autowired
    PfResourceService resourceService;

    @RequestMapping("")
    public String taskHandle(Model model,@RequestParam(value = "taskid", required = false) String taskid,HttpServletRequest request) throws Exception {
        boolean  hasFinish=false;
        String fileTokenId= "";
        int fileCenterNodeId=0;
        boolean quickTurn=false;
        boolean canTrustTask=false;
        String defaultName="";
        String kcdjTypeConfig ="";
        String busiType ="";
        String readOnly ="";
        boolean hasDel=false;
        boolean canDelOthers=false;
        int menuCount=0;
        PfWorkFlowInstanceVo workFlowInstanceVo=null;
        List<Menu> menuList=null;
        String taskBackRemark="";
        boolean backState = false;
        if (StringUtils.isNotBlank(taskid)) {
            taskService.updateTaskStadus(taskid);
            PfTaskVo taskVo = taskService.getTask(taskid);
            PfActivityVo activityVo = taskService.getActivity(taskVo.getActivityId());
            workFlowInstanceVo = workFlowIntanceService.getWorkflowInstance(activityVo.getWorkflowInstanceId());

            PfWorkFlowDefineVo workFlowDefineVo = workFlowDefineService.getWorkFlowDefine(workFlowInstanceVo.getWorkflowDefinitionId());
            //是否退回过
            if(activityVo!=null && activityVo.isBackState()){
                String beforeTaskId = taskVo.getTaskBefore();
                PfTaskVo pfTaskVo=taskService.getHistoryTask(beforeTaskId);
                if(pfTaskVo!=null){
                    taskBackRemark = pfTaskVo.getRemark();
                }
                backState = true;
            }

            ///////客户端用来获取附件////////////////////////////////////////
            Space space = nodeService.getWorkSpace(Constants.WORK_FLOW_STUFF,true);

            try{
                Node node = nodeService.getNode(space.getId(), activityVo.getWorkflowInstanceId(), true);
                if (node!=null){
                    fileTokenId=nodeService.getToken(node);
                    fileCenterNodeId=node.getId();

                    //获得工作流定义中的附件类型，用于待办任务打开时，初始化待办任务的附件管理页面
                    CommonUtils.initWorkflowFileCenter(nodeService,stuffConfigService,workFlowInstanceVo.getWorkflowDefinitionId(),fileCenterNodeId);
                }
            }catch (Exception ex){

            }
            List<PfTaskVo> beforeTasks=taskService.getHistoryTaskByBefore(taskVo);
            if (beforeTasks == null){
                beforeTasks = new ArrayList<PfTaskVo>();
            }

            model.addAttribute("taskVo",taskVo);
            model.addAttribute("activityVo",activityVo);
            model.addAttribute("beforeTasks",beforeTasks);
            model.addAttribute("workFlowDefineVo",workFlowDefineVo);

            // 菜单
            WorkFlowXml xmlModel = WorkFlowXmlUtil.getInstanceModel(workFlowInstanceVo);
            ActivityModel aModel = xmlModel.getActivity(activityVo.getActivityDefinitionId());
            quickTurn = aModel.isQuickTurn();
            List<RiskModel> riskList = aModel.getRiskModelList();
            defaultName=aModel.getExtendedAttribute("DefaultName");

//            defaultName="补充耕地方案";
//            defaultName="用地申请批准汇总表";
//            defaultName="二级菜单1";
            if ("true".equals(aModel.getExtendedAttribute("CanEnTrustTask")))
                canTrustTask=true;
            //扩展属性中关于勘测定界管理模块的配置属性获取
            kcdjTypeConfig = aModel.getExtendedAttribute("KcdjTypeConfig")+"";
            if (StringUtils.isBlank(kcdjTypeConfig)) {
                kcdjTypeConfig = xmlModel.getExtendedAttribute("KcdjTypeConfig");
            }
            busiType = xmlModel.getExtendedAttribute("BusiType")+"";
            if (StringUtils.isBlank(busiType)) {
                busiType = xmlModel.getExtendedAttribute("busiType")+"";
            }

            //默认赋予文件中心可编辑权限，只有在工作流扩展属性中增加了“FileCenterReadOnly”属性，且赋值为“true”时，不可编辑
            readOnly = aModel.getExtendedAttribute("FileCenterReadOnly")+"";
            readOnly = readOnly.toLowerCase();
            if (!readOnly.equals("true")) {
                readOnly = "false";
            }
            org.dom4j.Node node = aModel.getResourcesNode();

            if (node!=null){
                List resList=node.selectNodes(".//Resource");
                menuCount = resList.size();
            }
             hasDel = permitDel(taskVo,request);

            if ("结束活动".equals(aModel.getActivityDescription())){
                hasFinish=true;
            }else{
                if (aModel.getDefineId().equals(xmlModel.getEndActivityDefine()) && aModel.getTransitionsList().size()==0)
                    hasFinish=true;
            }

             canDelOthers= aModel.isCanDelOthers();
            //取办理菜单
            String menuXml=aModel.getResources();
            menuList = WorkFlowXmlUtil.getWorkFlowHandleMenu(menuXml,taskid,workFlowInstanceVo,resourceService);
        }
        if(workFlowInstanceVo==null)
            workFlowInstanceVo=new PfWorkFlowInstanceVo();
        if(menuList==null)
            menuList=new ArrayList<Menu>();
        model.addAttribute("hasFinish",hasFinish);
        model.addAttribute("fileTokenId",fileTokenId);
        model.addAttribute("fileCenterNodeId",fileCenterNodeId);
        model.addAttribute("quickTurn",quickTurn);
        model.addAttribute("canTrustTask",canTrustTask);
        model.addAttribute("defaultName",defaultName);
        model.addAttribute("kcdjTypeConfig",kcdjTypeConfig);
        model.addAttribute("busiType",busiType);
        model.addAttribute("readOnly",readOnly);
        model.addAttribute("hasDel",hasDel);
        model.addAttribute("canDelOthers",canDelOthers);
        model.addAttribute("workFlowInstanceVo",workFlowInstanceVo);
        model.addAttribute("taskid",taskid);
        model.addAttribute("menuCount",menuCount);
        model.addAttribute("menuList",menuList);
//        System.out.println(JSON.toJSONString(menuList));
        model.addAttribute("bdcdjUrl", RequestUtils.initOptProperties(AppConfig.getProperty("bdcdj.url")));
        model.addAttribute("version", AppConfig.getProperty("portal.version"));
        model.addAttribute("taskBackRemark",taskBackRemark);
        model.addAttribute("taskBackState",backState);
        return "/task/handle/task-handle";
    }
    private boolean permitDel(PfTaskVo taskVo,HttpServletRequest request) {
        if (!SessionUtil.getUserInfo(request)
                .isAdmin()) {
            PfActivityVo activityVo = taskService.getActivity(taskVo
                    .getActivityId());
            PfWorkFlowInstanceVo workFlowInstanceVo = workFlowIntanceService
                    .getWorkflowInstance(activityVo.getWorkflowInstanceId());
            WorkFlowXml workXml = WorkFlowXmlUtil
                    .getInstanceModel(workFlowInstanceVo);
            // WorkFlowXml workXml
            // =workFlowIntanceService.getWorkflowInstanceXmlModel(workFlowInstanceVo);
            if (!workXml.getBeginActivityDefine().equals(
                    activityVo.getActivityDefinitionId())) {
                return false;
            }
        }
        return true;
    }

    @RequestMapping("modifyWf")
    @ResponseBody
    public Object modifyWf(HttpServletRequest req,@RequestParam(value = "proid", required = true)String proid,String workflowIntanceName) throws Exception{
        if (StringUtils.isNotBlank(proid) && StringUtils.isNotBlank(workflowIntanceName)){
            PfWorkFlowInstanceVo workFlowInstanceVo = workFlowIntanceService.getWorkflowInstance(proid);
            if (workFlowInstanceVo != null){
                workFlowInstanceVo.setWorkflowIntanceName(java.net.URLDecoder.decode(workflowIntanceName,"UTF-8"));
                workFlowIntanceService.updateWorkFlowIntanceName(workFlowInstanceVo);
                return handlerSuccessJson();
            }
        }
        return handlerErrorJson();
    }

}
