package com.gtis.portal.web;

import com.gtis.fileCenter.model.Node;
import com.gtis.fileCenter.model.Space;
import com.gtis.fileCenter.service.NodeService;
import com.gtis.plat.service.SysMenuService;
import com.gtis.plat.service.SysTaskService;
import com.gtis.plat.service.SysWorkFlowDefineService;
import com.gtis.plat.service.SysWorkFlowInstanceService;
import com.gtis.plat.vo.*;
import com.gtis.portal.model.Menu;
import com.gtis.portal.util.Constants;
import com.gtis.portal.util.WorkFlowXml;
import com.gtis.portal.util.WorkFlowXmlUtil;
import com.gtis.web.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 任务办理中心
 * @author <a href="mailto:zhangxing@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/1/8
 */
@Controller
@RequestMapping("/projectHandle")
public class ProjectHandleController {
    @Autowired
    SysWorkFlowDefineService workFlowDefineService;
    @Autowired
    NodeService nodeService;
    @Autowired
    SysWorkFlowInstanceService workFlowIntanceService;
    @Autowired
    SysTaskService taskService;
    @Autowired
    SysMenuService menuService;


    @RequestMapping("")
    public String taskHandle(Model model,@RequestParam(value = "wiid", required = false) String wiid,@RequestParam(value = "proid", required = false) String proid,@RequestParam(value = "wdno", required = false) String wdno,HttpServletRequest request) throws Exception {
        String fileTokenId= "";
        int fileCenterNodeId=0;
        String defaultName="";
        String kcdjTypeConfig ="";
        String busiType ="";
        String wfName ="";
        String readOnly ="true";
        boolean disable=false;
        int menuCount=0;
        PfTaskVo taskVo=null;
        PfWorkFlowInstanceVo workFlowInstanceVo=null;
        PfWorkFlowDefineVo workFlowDefineVo=null;
        List<Menu> menuList=null;
        if (wiid != null && !wiid.equals("")) {
            workFlowInstanceVo = workFlowIntanceService.getWorkflowInstance(wiid);
        } else {
            workFlowInstanceVo = workFlowIntanceService.getWorkflowInstanceByProId(proid);
        }
        if (workFlowInstanceVo!=null){
            workFlowDefineVo= workFlowDefineService.getWorkFlowDefine(workFlowInstanceVo.getWorkflowDefinitionId());
            wiid = workFlowInstanceVo.getWorkflowIntanceId();
        }else if(StringUtils.isNotBlank(wdno)){
            workFlowDefineVo= workFlowDefineService.getWorkFlowByDefinitionNo(wdno);
        }
        wfName =workFlowDefineVo.getWorkflowName();
        if (workFlowDefineVo!=null){
            WorkFlowXml xmlDao = WorkFlowXmlUtil.getDefineModel(workFlowDefineVo);
            disable = xmlDao.isDisable();
            ///////客户端用来获取附件////////////////////////////////////////
            Space space = nodeService.getWorkSpace(Constants.WORK_FLOW_STUFF, true);
            try {
                Node node=null;
                if (StringUtils.isBlank(wiid))
                    node = nodeService.getNode(space.getId(), proid, true);
                else
                    node = nodeService.getNode(space.getId(), wiid, false);
                if (node != null) {
                    fileTokenId = nodeService.getToken(node);
                    fileCenterNodeId = node.getId();
                }
            } catch (Exception ex) {

            }

            model.addAttribute("taskVo",taskVo);
            model.addAttribute("workFlowDefineVo",workFlowDefineVo);

            ////////////////////////////////////////////////////////////
            //扩展属性中关于勘测定界管理模块的配置属性获取
            kcdjTypeConfig = xmlDao.getExtendedAttribute("KcdjTypeConfig")+"";
            if (StringUtils.isBlank(kcdjTypeConfig)) {
                kcdjTypeConfig = xmlDao.getExtendedAttribute("KcdjTypeConfig");
            }
            busiType = xmlDao.getExtendedAttribute("BusiType")+"";
            if (StringUtils.isBlank(busiType)) {
                busiType = xmlDao.getExtendedAttribute("busiType")+"";
            }

            //取办理菜单
            String roles = SessionUtil.getUserInfo(
                    request).getRoleIds();
            if (wiid != null && !wiid.equals("")) {
                workFlowInstanceVo = workFlowIntanceService.getWorkflowInstance(wiid);
            } else {
                workFlowInstanceVo = workFlowIntanceService.getWorkflowInstanceByProId(proid);
            }
            if (workFlowInstanceVo!=null){
                workFlowDefineVo= workFlowDefineService.getWorkFlowDefine(workFlowInstanceVo.getWorkflowDefinitionId());
            }else if(StringUtils.isNotBlank(wdno)){
                workFlowDefineVo= workFlowDefineService.getWorkFlowByDefinitionNo(wdno);
            }
            List<PfResourceVo> lstResult =null;
//            if(StringUtils.isNotBlank(roles))
                lstResult = menuService.getProjectMenu(roles,
                        workFlowDefineVo.getWorkflowDefinitionId());
            List<PfActivityVo> activityVoList= taskService.getWorkFlowInstanceActivityList(wiid);
            int size=0;
            String showActivityName="";
            /**
             * 此处主要是存放已经插入的菜单，如果存在id和name相同的资源，则不再重复插入
             */
            HashMap<String, String> insertedMenuMap = new HashMap<String, String>();
            if (lstResult != null) {
                menuList=new ArrayList<Menu>();
                for (int i =0; i< lstResult.size(); i++) {
                    PfResourceVo menuVo = lstResult.get(i);
                    String tempKey = menuVo.getResourceId() + "_" + menuVo.getResourceName();
                    if (insertedMenuMap.get(tempKey) != null) {
                        //System.out.println(")^^^^^^^^^^^^^^^^menuVo="+menuVo.getResourceName());
                        continue;
                    }
                    insertedMenuMap.put(tempKey, tempKey);
                        Menu menu=new Menu();
                        menu.setId(menuVo.getResourceId());
                        String name=menuVo.getResourceName();
                        menu.setText(name);
                        if(disable)
                            menu.setLink( "/SysResource.action?from=pro&disable=true&proid=" + workFlowInstanceVo.getProId() + "&wiid=" + wiid + "&rid=" + menuVo.getResourceId()+"&__showtoolbar__=false");
                        else
                            menu.setLink( "/SysResource.action?proid=" + workFlowInstanceVo.getProId() + "&wiid=" + wiid + "&rid=" + menuVo.getResourceId()+"&__showtoolbar__=false");
                        menuList.add(menu);
                }


            }
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
        model.addAttribute("workFlowInstanceVo",workFlowInstanceVo);
        model.addAttribute("menuCount",menuCount);
        model.addAttribute("menuList",menuList);
        return "/task/handle/task-projectHandle";
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
