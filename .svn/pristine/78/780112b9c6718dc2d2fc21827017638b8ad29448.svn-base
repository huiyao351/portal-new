package com.gtis.portal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.plat.service.*;
import com.gtis.plat.vo.PfActivityVo;
import com.gtis.plat.vo.PfRoleVo;
import com.gtis.plat.vo.PfSignVo;
import com.gtis.plat.vo.PfTaskVo;
import com.gtis.plat.wf.WorkFlowInfo;
import com.gtis.plat.wf.WorkFlowTransInfo;
import com.gtis.plat.wf.model.ActivityModel;
import com.gtis.plat.wf.model.PerformerModel;
import com.gtis.portal.dao.BaseDao;
import com.gtis.portal.service.*;
import com.gtis.portal.util.*;
import com.gtis.web.SessionUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowAutoTurnServiceImpl implements WorkflowAutoTurnService {
    private static final Log log = LogFactory.getLog(WorkflowAutoTurnServiceImpl.class);

    //public static Map workflowConfigMap = new HashMap();
    @Value("${egov.conf}/portal/workflow_auto_turn.json")
    private String location;
    @Autowired
    BaseDao baseDao;

    @Autowired
    WorkFlowCoreService workFlowCoreService;
    @Autowired
    SysWorkFlowInstanceService workFlowInstanceService;
    @Autowired
    SysTaskService taskService;
    @Autowired
    SysActivityService activityService;
    @Autowired
    SysSignService signService;
    @Autowired
    SysUserService userService;

    public Map getWorkflowAutoTurnMap(){
        try {
            if(StringUtils.isNotBlank(location)){
                return (Map)CommonUtils.readJsonFile(location);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public Map autoTurnWorkflowByAdId(String proid,String taskId,String userId){
        try {
            if (StringUtils.isNotBlank(proid) && StringUtils.isNotBlank(taskId)){
                PfTaskVo taskVo = taskService.getTask(taskId);
                if (taskVo != null && StringUtils.isNotBlank(taskVo.getActivityId())){
                    String acId = taskVo.getActivityId();
                    PfActivityVo activityVo = activityService.getActivityById(acId);
                    if(activityVo != null && StringUtils.isNotBlank(activityVo.getActivityDefinitionId())){
                        String adId = activityVo.getActivityDefinitionId();
                        Map turnMap = getWorkflowAutoTurnMap();
                        Object obj = turnMap.get(adId);
                        if (obj != null){
                            Map adConfigMap = (Map)obj;
                            String wdid = MapUtils.getString(adConfigMap,"WORKFLOW_DEFINITION_ID");

                            List targetAdList = new ArrayList();
                            Object targetAds = MapUtils.getObject(adConfigMap,"TARGET_ACTIVITY");
                            if (targetAds != null){
                                targetAdList = (List)targetAds;
                            }

                            boolean isok = turnWordkflow(userId,targetAdList,userId,taskId);
                            if (isok){
                                List signMapList = new ArrayList();
                                Object signs = MapUtils.getObject(adConfigMap,"SIGN");
                                if (signs != null){
                                    signMapList = (List)signs;
                                }
                                //如果没有签名，则表示无需签名
                                if (!signMapList.isEmpty()){
                                    for (int i = 0; i < signMapList.size(); i++) {
                                        JSONObject signMap = (JSONObject)signMapList.get(i);
                                        String signKey = MapUtils.getString(signMap,"SIGN_KEY");
                                        String signOpinion = MapUtils.getString(signMap,"SIGN_OPINION");
                                        List<PfSignVo> signList = signService.getSignListByUserId(signKey,proid,userId);
                                        if (signList == null || signList.size() < 1){
                                            PfSignVo signVo = new PfSignVo();
                                            signVo.setProId(proid);
                                            signVo.setSignKey(signKey);
                                            signVo.setSignOpinion(signOpinion);
                                            signVo.setSignId(UUIDGenerator.generate());
                                            signVo.setUserId(userId);
                                            signService.insertAutoSign(signVo);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public boolean turnWordkflow(String proid,List targetAdList,String userId,String taskId){
        try {
            boolean hasConfigNextAc = false;
            //如果没有目标节点，则表示属于办结流程
            if (targetAdList != null && !targetAdList.isEmpty()){
                hasConfigNextAc = true;
            }

            boolean hasNextAc = true;
            WorkFlowInfo info = workFlowCoreService.getWorkFlowTurnInfo(userId, taskId);
            WorkFlowXml workXml =WorkFlowXmlUtil.getInstanceModel(info.getWorkFlowIntanceVo());
            WorkFlowTransInfo transInfo=info.getTransInfo();
            List<ActivityModel> lstTrans = transInfo.getTranActivitys();
            if (lstTrans == null || lstTrans.isEmpty()) {
                //有下一个节点，则进行过滤转发操作
                hasNextAc = false;
            }

            if (!hasNextAc){
                //没有下一个节点，因此判断为办结操作
                finishWorkflow(proid);
            }else {
                if (hasConfigNextAc){
                    if (lstTrans.size() == 1){
                        //工作流定义只有一个目标节点的时候，直接转向该节点,取json中配置的第一个目标节点的用户id
                        String nextUserId = MapUtils.getString((JSONObject)targetAdList.get(0),"USER_ID");
                        String nextAdId = lstTrans.get(0).getDefineId();//下个节点
                        String turnXml = initTurnTaskXml(info,workXml,transInfo,nextUserId,nextAdId);
                        if (StringUtils.isNotBlank(turnXml)){
                            Document doc = DocumentHelper.parseText(turnXml);
                            workFlowCoreService.turnTask(doc,taskId);
                        }
                    }else if (lstTrans.size() > 1){
                        for (int i = 0; i < targetAdList.size(); i++) {
                            JSONObject adMap = (JSONObject)targetAdList.get(i);
                            String nextAdId = MapUtils.getString(adMap,"ACTIVITY_DEFINITION_ID");
                            String nextUserId = MapUtils.getString(adMap,"USER_ID");

                            for (int j = 0; j < lstTrans.size(); j++) {
                                if (StringUtils.equals(nextAdId, lstTrans.get(j).getDefineId())) {
                                    String turnXml = initTurnTaskXml(info,workXml,transInfo,nextUserId,nextAdId);
                                    if (StringUtils.isNotBlank(turnXml)){
                                        Document doc = DocumentHelper.parseText(turnXml);
                                        workFlowCoreService.turnTask(doc,taskId);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }catch (Exception ex){
            log.error("用户："+userId+"转发任务ID："+taskId+"转向目标节点：转发失败！");
        }
        return false;
    }

    private String initTurnTaskXml(String userId,String nextUserId,String taskid,String nextAdId)throws Exception{
        WorkFlowInfo info = workFlowCoreService.getWorkFlowTurnInfo(userId, taskid);
        WorkFlowTransInfo transInfo=info.getTransInfo();
        List<ActivityModel> lstTrans = transInfo.getTranActivitys();
        if (lstTrans != null && lstTrans.size() > 0) {
            for (int i = 0; i < lstTrans.size(); i++) {
                if (StringUtils.equals(nextAdId,lstTrans.get(i).getDefineId())){
                    WorkFlowXml workXml =WorkFlowXmlUtil.getInstanceModel(info.getWorkFlowIntanceVo());
                    ActivityModel nextModel = workXml.getActivity(nextAdId);

                    List<PerformerModel> performerList = nextModel.getPerformerList();
                    if (performerList != null && performerList.size() > 0){
                        String roleId = "";
                        List<PfRoleVo> roleList = userService.getRoleListByUser(nextUserId);
                        for (PerformerModel performerModel : performerList) {
                            if (StringUtils.isNotBlank(performerModel.getRoleId())){
                                boolean isok = false;
                                for (PfRoleVo roleVo : roleList){
                                    if (StringUtils.equals(performerModel.getRoleId(),roleVo.getRoleId())){
                                        roleId = performerModel.getRoleId();
                                        isok = true;
                                        break;
                                    }
                                }
                                if (isok){
                                    break;
                                }
                            }
                        }
                        if (StringUtils.isNotBlank(roleId)){
                            String activityRelType = "";
                            if (transInfo.getTransType().equalsIgnoreCase("and"))
                                activityRelType = "and";
                            else
                                activityRelType = "or";

                            String activitysElement = "<Activitys RelType=\"" + activityRelType + "\"";
                            activitysElement += " SendSMS= \"false\"";
                            activitysElement += ">";
                            String activityElement = "<Activity ";
                            activityElement += " Id=\"" + nextAdId + "\">";
                            String userInfoElement = "<UserInfo ";
                            userInfoElement += " RoleId=\"" + roleId + "\"";
                            userInfoElement += " Id=\"" + nextUserId + "\">";
                            userInfoElement += "</UserInfo>";
                            activityElement += userInfoElement;
                            activityElement += "</Activity>";
                            activitysElement += activityElement;

                            String remarkElement = "<ReMark>";
                            String remarkTextElement = "<text>";
                            remarkTextElement += "</text>";
                            remarkElement += remarkTextElement + "</ReMark>";
                            activitysElement += remarkElement + "</Activitys>";
                            return activitysElement;
                        }
                    }
                }
            }
        }else {
            return "finish";
        }
        return null;
    }

    private String initTurnTaskXml(WorkFlowInfo info,WorkFlowXml workXml,WorkFlowTransInfo transInfo,String nextUserId,String nextAdId)throws Exception{
        ActivityModel nextModel = workXml.getActivity(nextAdId);
        List<PerformerModel> performerList = nextModel.getPerformerList();
        if (performerList != null && performerList.size() > 0){
            String roleId = "";
            List<PfRoleVo> roleList = userService.getRoleListByUser(nextUserId);
            for (PerformerModel performerModel : performerList) {
                if (StringUtils.isNotBlank(performerModel.getRoleId())){
                    boolean isok = false;
                    for (PfRoleVo roleVo : roleList){
                        if (StringUtils.equals(performerModel.getRoleId(),roleVo.getRoleId())){
                            roleId = performerModel.getRoleId();
                            isok = true;
                            break;
                        }
                    }
                    if (isok){
                        break;
                    }
                }
            }
            if (StringUtils.isNotBlank(roleId)){
                String activityRelType = "";
                if (transInfo.getTransType().equalsIgnoreCase("and"))
                    activityRelType = "and";
                else
                    activityRelType = "or";

                String activitysElement = "<Activitys RelType=\"" + activityRelType + "\"";
                activitysElement += " SendSMS= \"false\"";
                activitysElement += ">";
                String activityElement = "<Activity ";
                activityElement += " Id=\"" + nextAdId + "\">";
                String userInfoElement = "<UserInfo ";
                userInfoElement += " RoleId=\"" + roleId + "\"";
                userInfoElement += " Id=\"" + nextUserId + "\">";
                userInfoElement += "</UserInfo>";
                activityElement += userInfoElement;
                activityElement += "</Activity>";
                activitysElement += activityElement;

                String remarkElement = "<ReMark>";
                String remarkTextElement = "<text>";
                remarkTextElement += "</text>";
                remarkElement += remarkTextElement + "</ReMark>";
                activitysElement += remarkElement + "</Activitys>";
                return activitysElement;
            }
        }
        return null;
    }

    public void finishWorkflow(String proid)throws Exception{
        try {
            workFlowCoreService.finishWorkFlow(proid);
            List<PfTaskVo> taskList = taskService.getTaskListByInstance(proid);
            if (taskList != null && taskList.size() > 0){
                List<String> acidList = new ArrayList<String>();
                for (int i = 0; i < taskList.size(); i++) {
                    if (acidList.indexOf(taskList.get(i).getActivityId())<0){
                        acidList.add(taskList.get(i).getActivityId());
                    }
                    taskService.insertTaskHistory(taskList.get(i).getTaskId());
                    taskService.deleteTask(taskList.get(i).getTaskId());
                }
                for (int i = 0; i < acidList.size(); i++) {
                    taskService.updateActivityStadus(acidList.get(i),2);
                }
            }
        }catch (Exception e){}

    }
}
