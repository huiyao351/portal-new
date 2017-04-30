package com.gtis.portal.web;

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
import com.gtis.portal.model.Menu;
import com.gtis.portal.service.PfResourceService;
import com.gtis.portal.util.Constants;
import com.gtis.portal.util.WorkFlowXml;
import com.gtis.portal.util.WorkFlowXmlUtil;
import com.gtis.web.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务办理中心
 * @author <a href="mailto:zhangxing@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/1/8
 */
@Controller
@RequestMapping("/taskOverHandle")
public class TaskOverHandleController {
    @Autowired
    SysWorkFlowDefineService workFlowDefineService;
    @Autowired
    NodeService nodeService;
    @Autowired
    SysWorkFlowInstanceService workFlowIntanceService;
    @Autowired
    SysTaskService taskService;
    @Autowired
    PfResourceService resourceService;

    @RequestMapping("")
    public String taskHandle(Model model,@RequestParam(value = "taskid", required = false) String taskid,HttpServletRequest request) throws Exception {
        String fileTokenId= "";
        int fileCenterNodeId=0;
        String defaultName="";
        String kcdjTypeConfig ="";
        String busiType ="";
        String readOnly ="true";
        boolean canDelOthers=false;
        int menuCount=0;
        PfTaskVo taskVo=null;
        PfActivityVo activityVo=null;
        PfWorkFlowInstanceVo workFlowInstanceVo=null;
        PfWorkFlowDefineVo workFlowDefineVo=null;
        List<Menu> menuList=null;
        if (StringUtils.isNotBlank(taskid)) {
            taskVo=taskService.getHistoryTask(taskid);
            activityVo=taskService.getActivity(taskVo.getActivityId());
            workFlowInstanceVo=workFlowIntanceService.getWorkflowInstance(activityVo.getWorkflowInstanceId());
            workFlowDefineVo=workFlowDefineService.getWorkFlowDefine(workFlowInstanceVo.getWorkflowDefinitionId());

            Space space = nodeService.getWorkSpace(Constants.WORK_FLOW_STUFF, true);
            try{
                Node node = nodeService.getNode(space.getId(), activityVo.getWorkflowInstanceId(), false);
                if (node!=null){
                    fileTokenId=nodeService.getToken(node);
                    fileCenterNodeId=node.getId();
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

            WorkFlowXml xmlDao = WorkFlowXmlUtil.getDefineModel(workFlowDefineVo);
            ////////////////////////////////////////////////////////////
            //扩展属性中关于勘测定界管理模块的配置属性获取
            busiType = xmlDao.getExtendedAttribute("BusiType") + "";
            if (StringUtils.isBlank(busiType)) {
                busiType = xmlDao.getExtendedAttribute("busiType") + "";
            }

            WorkFlowXml xmlModel = WorkFlowXmlUtil.getInstanceModel(workFlowInstanceVo);
            //WorkFlowXml xmlModel=workFlowInstanceVo.getXmlModel();
            ActivityModel aModel=xmlModel.getActivity(activityVo.getActivityDefinitionId());
            defaultName=aModel.getExtendedAttribute("DefaultName");

            //扩展属性中关于勘测定界管理模块的配置属性获取
            kcdjTypeConfig = aModel.getExtendedAttribute("KcdjTypeConfig")+"";
            if (StringUtils.isBlank(kcdjTypeConfig)) {
                kcdjTypeConfig = xmlModel.getExtendedAttribute("KcdjTypeConfig");
            }
            busiType = xmlModel.getExtendedAttribute("BusiType")+"";
            if (StringUtils.isBlank(busiType)) {
                busiType = xmlModel.getExtendedAttribute("busiType")+"";
            }

            //取办理菜单
            String menuXml=aModel.getResources();
            menuList = WorkFlowXmlUtil.getWorkFlowHandleMenu(menuXml,taskid,workFlowInstanceVo,resourceService);
        }
        if(workFlowInstanceVo==null)
            workFlowInstanceVo=new PfWorkFlowInstanceVo();
        if(menuList==null)
            menuList=new ArrayList<Menu>();
        model.addAttribute("fileTokenId",fileTokenId);
        model.addAttribute("fileCenterNodeId",fileCenterNodeId);
        model.addAttribute("defaultName",defaultName);
        model.addAttribute("kcdjTypeConfig",kcdjTypeConfig);
        model.addAttribute("busiType",busiType);
        model.addAttribute("readOnly",readOnly);
        model.addAttribute("canDelOthers",canDelOthers);
        model.addAttribute("workFlowInstanceVo",workFlowInstanceVo);
        model.addAttribute("taskid",taskid);
        model.addAttribute("menuCount",menuCount);
        model.addAttribute("menuList",menuList);
        return "/task/handle/task-overHandle";
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

}
