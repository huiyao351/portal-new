package com.gtis.portal.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gtis.common.http.Utf8PostMethod;
import com.gtis.config.AppConfig;
import com.gtis.fileCenter.service.NodeService;
import com.gtis.plat.service.*;
import com.gtis.plat.vo.PfActivityVo;
import com.gtis.plat.vo.PfTaskVo;
import com.gtis.plat.vo.PfUserVo;
import com.gtis.plat.vo.PfWorkFlowInstanceVo;
import com.gtis.plat.wf.WorkFlowInfo;
import com.gtis.plat.wf.WorkFlowTransInfo;
import com.gtis.plat.wf.model.ActivityModel;
import com.gtis.plat.wf.model.PerformerModel;
import com.gtis.plat.wf.model.PerformerTaskModel;
import com.gtis.portal.entity.PfRole;
import com.gtis.portal.entity.PfUser;
import com.gtis.portal.service.PfRoleService;
import com.gtis.portal.service.PfUserRoleService;
import com.gtis.portal.service.PfUserService;
import com.gtis.portal.util.RequestUtils;
import com.gtis.portal.util.WorkFlowUtil;
import com.gtis.portal.util.WorkFlowXml;
import com.gtis.portal.util.WorkFlowXmlUtil;
import com.gtis.web.SessionUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 转发
 * @author <a href="mailto:zhangxing@gtmap.cn">zhangxing</a>
 * @version 1.0, 2015/1/8
 */
@Controller
@RequestMapping("/turnWorkFlow")
public class TurnWorkFlowController {
    private static final Log log = LogFactory.getLog(TurnWorkFlowController.class);
    @Autowired
    SysWorkFlowDefineService workFlowDefineService;
    @Autowired
    NodeService nodeService;
    @Autowired
    SysWorkFlowInstanceService workFlowIntanceService;
    @Autowired
    SysTaskService taskService;
    @Autowired
    WorkFlowCoreService workFlowService;
    @Autowired
    SysOpinionService opinionService;
    /**子流程服务*/
    @Autowired
    private SysSubProcessService sysSubProcessService;
    @Autowired
    PfUserRoleService userRoleService;
    @Autowired
    PfUserService userService;

    @RequestMapping("")
    public String turnWorkFlow(Model model,@RequestParam(value = "taskid", required = false) String taskid,HttpServletRequest request) throws Exception {

        String userId = SessionUtil.getCurrentUserId();
        WorkFlowInfo info = null;

        //info = workFlowService.getWorkFlowTurnInfo(userId,taskid);
        String proid="";
        if(sysSubProcessService.isSubProcessFinishing(taskid)){
            model.addAttribute("subProcessFinish",true);
            info = workFlowService.getWorkFlowTurnInfo("-1", taskService.getActivity(taskService.getTask(taskid).getActivityId()).getWorkflowInstanceId());
        }else{
            model.addAttribute("subProcessFinish",false);
            info = workFlowService.getWorkFlowTurnInfo(userId,taskid);
        }

        if(info!=null && info.getWorkFlowIntanceVo()!=null && StringUtils.isNotBlank(info.getWorkFlowIntanceVo().getProId()))
            proid= info.getWorkFlowIntanceVo().getProId();
        String turnXml = turnInfo(info,request);

        //
        WorkFlowXml workFlowXml= WorkFlowXmlUtil.getInstanceModel(info.getWorkFlowIntanceVo());
        ActivityModel activityModel= workFlowXml.getActivity(info.getSourceActivity().getActivityDefinitionId());
        String confirmTurnInfo="false";
        if ("true".equals(activityModel.getExtendedAttribute("ConfirmTurnInfo")))
            confirmTurnInfo="true";
        String version= AppConfig.getProperty("portal.version");

        String opinions = JSONArray.toJSONString(opinionService.getOpinionList(userId, info.getWorkFlowDefineVo().getWorkflowName(),info.getSourceActivity().getActivityName()));

        model.addAttribute("taskid",taskid);
        model.addAttribute("userId",userId);
        model.addAttribute("proid",proid);
        model.addAttribute("bdcdjUrl", RequestUtils.initOptProperties(AppConfig.getProperty("bdcdj.url")));
        model.addAttribute("version",version);
        model.addAttribute("turnXml",turnXml);
        model.addAttribute("opinions",opinions);
        model.addAttribute("confirmTurnInfo",confirmTurnInfo);
        return "/task/handle/turn-workFlow";
    }
    /**
     *
     * <Activitys RelType="or">
     *  <Activity Name="转发活动一" Id="a1" MutiSelect="true" DefaultSelected="true">
     *      <User Name="××部门" Id="1">
     *          <UserInfo Name="u1" Id="u1" DefaultSelected="true"/>
     *          <UserInfo Name="u11" Id="u11"/>
     *      </User>
     *      <User Name="××部门" Id="2">
     *          <UserInfo Name="au2" Id="u2"/>
     *          <UserInfo Name="au21" Id="u21"/>
     *      </User>
     *      <User Name="××角色" Id="3">
     *          <UserInfo Name="au3" Id="u3" DefaultSelected="true"/>
     *          <UserInfo Name="au31" Id="u31"/>
     *          <UserInfo  Name="au32" Id="u32"/>
     *      </User>
     *  </Activity>
     *  <Activity Name="转发活动二" Id="a2" MutiSelect="false">
     *   <User Name="××部门" Id="11">
     *      <UserInfo Name="u1" Id="u1"/>
     *      <UserInfo Name="u11" Id="u11"/>
     *   </User>
     * </Activitys>
     *
     *
     */

    private String turnInfo(WorkFlowInfo info,HttpServletRequest request) throws Exception {

        // 获取工作流实例模型
        WorkFlowXml xmlDao = WorkFlowXmlUtil.getInstanceModel(info.getWorkFlowIntanceVo());
        // 获取当前活动定义
        ActivityModel activityModel = xmlDao.getActivity(info.getSourceActivity().getActivityDefinitionId());

        String filterType = null;
        List<String> filterStr = null;
        if(StringUtils.isNotBlank(activityModel.getFilterInfo())){
            //过滤信息对象
            Document fileDoc = DocumentHelper.parseText(activityModel.getFilterInfo());
            //过滤类型：User,Organ,Role
            String user = fileDoc.getRootElement().valueOf("@type");
            org.dom4j.Node corNode = fileDoc.selectSingleNode("//Filter/" + user + "[@Id='" + SessionUtil.getUserId(request) + "']/Correspondence");
            if(corNode != null){
                filterType = corNode.valueOf("@type");
                List<DefaultText> organList = corNode.selectNodes(filterType + "/text()");
                if(organList != null && !organList.isEmpty()){
                    filterStr = new ArrayList<String>();
                    for(DefaultText text : organList){
                        filterStr.add(text.getText());
                    }
                }
            }
        }

        WorkFlowTransInfo transInfo=info.getTransInfo();
        Document doc = org.dom4j.DocumentHelper.createDocument();
        //doc.setXMLEncoding("GBK");
        Element root = doc.addElement("Activitys");
        if (transInfo.getTransType().equalsIgnoreCase("and"))
            root.addAttribute("RelType", "and");
        else
            root.addAttribute("RelType", "or");

        if(activityModel.getIsRequiredOpinion().equalsIgnoreCase("true")){
            root.addAttribute("ReqOpinion", "true");
        }else{
            root.addAttribute("ReqOpinion", "false");
        }
        if(activityModel.getIsSendSMS().equalsIgnoreCase("true")){
            root.addAttribute("SendSMS","true");
        }else{
            root.addAttribute("SendSMS","false");
        }
        if(StringUtils.isNotBlank(activityModel.getCooperRootId())){
            root.addAttribute("cooperRootId",activityModel.getCooperRootId());
        }

        List<ActivityModel> lstTrans = transInfo.getTranActivitys();
        if (lstTrans.size()<2)
            root.addAttribute("RelType", "or");
        for (ActivityModel aModel : lstTrans) {
            Element ActivityEle = root.addElement("Activity");
            ActivityEle.addAttribute("Name", aModel.getActivityDefineName());
            ActivityEle.addAttribute("Id", aModel.getDefineId());
            if (aModel.isMutiSelect())
                ActivityEle.addAttribute("MutiSelect", "true");

            if(!aModel.isSelectAll())
                ActivityEle.addAttribute("SelectAll","false");
            else
                ActivityEle.addAttribute("SelectAll","true");

            if(aModel.getActivityDefineName().equalsIgnoreCase(activityModel.getDefaultSelectName()))
                ActivityEle.addAttribute("DefaultSelected", "true");
            else
                ActivityEle.addAttribute("DefaultSelected", "false");
            String userName=null;
            if(StringUtils.isNotBlank(aModel.getExtendedAttribute("DefaultSelectedUser"))){
                userName=aModel.getExtendedAttribute("DefaultSelectedUser");
            }
            for (PerformerTaskModel userModel : aModel.getPerformerModelList()) {
                if(filterStr != null && userModel.getType().equals(filterType)){
                    if(!filterStr.contains(userModel.getId())){
                        continue;
                    }
                }
                Element User = ActivityEle.addElement("User");
                User.addAttribute("Name", userModel.getName());
                User.addAttribute("Id", userModel.getId());
                for (PfUserVo vo : userModel.getUserList()) {
                    Element UserInfo = User.addElement("UserInfo");
                    UserInfo.addAttribute("Name", vo.getUserName());
                    UserInfo.addAttribute("Id", vo.getUserId());
                    if (StringUtils.isNotBlank(userName) && vo.getUserName().equalsIgnoreCase(userName)){
                        User.addAttribute("DefaultSelected", "true");
                        UserInfo.addAttribute("DefaultSelected", "true");
                    }
//                    if (vo.getUserId().equals(SessionUtil.getCurrentUserId())){
//                        User.addAttribute("DefaultSelected", "true");
//                        UserInfo.addAttribute("DefaultSelected", "true");
//                    }
                }
            }
        }
        // 加入是否可以办结
        if (transInfo.isCanFinish()) {
            Element ActivityEle = root.addElement("Activity");
            ActivityEle.addAttribute("Name", "任务办结");
            ActivityEle.addAttribute("Id", "-1");
        }

        doc = httpFilterXml(doc,activityModel,info);

        return doc.getRootElement().asXML();
    }

    /**
     * 通过TurnInfo处理url
     * @param doc
     * @return
     */
    private Document httpFilterXml(Document doc,ActivityModel actModel,WorkFlowInfo info){
        if(StringUtils.isNotBlank(actModel.getTurnInfoUrl())){
            String url = actModel.getTurnInfoUrl();
            if(!url.startsWith("http")){
                url = AppConfig.getPlaceholderValue(url);
            }
            HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
            PostMethod postMethod = new Utf8PostMethod(url);
            NameValuePair[] data = {
                    new NameValuePair("turnInfo",doc.asXML()),
                    new NameValuePair("userId",info.getUserId()),
                    new NameValuePair("proId",(info.getWorkFlowIntanceVo() == null)? "":info.getWorkFlowIntanceVo().getProId())
            };

            postMethod.setRequestBody(data);
            int status = 0;
            try {
                status = httpClient.executeMethod(postMethod);
            } catch (IOException e) {
                log.error("TurnInfo filter url request failer{}",e);
            }
            if (status == HttpStatus.SC_OK){
                try {
                    String xml = postMethod.getResponseBodyAsString();
                    if(StringUtils.isNotBlank(xml)){
                        doc = DocumentHelper.parseText(xml);
                    }
                } catch (Exception e) {
                    log.error("TurnInfo filter url request success,prase failure{}",e);
                }
            }else{
                log.error("TurnInfo filter url request failer");
            }
        }
        return doc;
    }
    @ResponseBody
    @RequestMapping(value = "/getTurnWorkFlowInfo", method = RequestMethod.GET)
    public String getTurnWorkFlowInfo(Model model,@RequestParam(value = "taskid", required = false) String taskid,HttpServletRequest request) throws Exception {
        HashMap map=new HashMap();
        String userId = SessionUtil.getCurrentUserId();
        WorkFlowInfo info = null;

        info = workFlowService.getWorkFlowTurnInfo(userId,taskid);
        String proid="";
        if(info!=null && info.getWorkFlowIntanceVo()!=null && StringUtils.isNotBlank(info.getWorkFlowIntanceVo().getProId()))
            proid= info.getWorkFlowIntanceVo().getProId();
        return proid;
    }

    @ResponseBody
    @RequestMapping(value = "/filterJoinTransUser")
    public Object filterJoinTransUser(String proid,String taskid,String targetAdIds){
        /**
         * 该功能目前只处理多转一，并且一要求多必须办理完成的情况
         * 首先根据目标节点定义id，查找该节点是否存在临时任务
         * 1、根据流程id和定义id查询节点表记录，
         * 2、如果存在，则根据节点记录id，组合临时字符串，之后用新的id查找待办任务表是否存在记录
         * 3、如果存在，则获取该记录的人员信息
         * 4、获取该节点定义的转发人员角色、人员列表，提取其中一个符合第3步的人员记录返回即可
         */
        HashMap<String,HashMap<String,String>> filterUserMapList = new HashMap<String,HashMap<String, String>>();

        //#流程多转一转发时，是否按照第一个转发的进行人员的过滤，后面的分支节点转过来的时候，都固定为第一个转发所选择的人员
        String firstTurnFilter = AppConfig.getProperty("portal.mutil2one.filter.firstturn");
        if (!StringUtils.equalsIgnoreCase("true",firstTurnFilter)){
            return filterUserMapList;
        }

        if (StringUtils.isNotBlank(proid) && StringUtils.isNotBlank(targetAdIds)){
            String[] targetAdIdAry = StringUtils.split(targetAdIds,";");
            try {
                //解析每个节点数据
                for (int i = 0; i < targetAdIdAry.length; i++) {
                    String targetAdId = targetAdIdAry[i];
                    //获取工作流实例，并获取对应的工作流定义模型
                    PfWorkFlowInstanceVo workFlowInstanceVo = workFlowIntanceService.getWorkflowInstance(proid);
                    //根据流程id和工作流定义id获取是否存在该节点的实例记录
                    PfActivityVo activityVo = taskService.getActivityBywIdandadId(proid,targetAdId);
                    if (activityVo != null){
                        String activityId = activityVo.getActivityId();
                        //获取临时任务的节点id
                        String tmpAcId = WorkFlowUtil.buildTEMPActivityId(activityId);

                        //根据临时任务节点id获取临时任务记录，获取第一个即可
                        List<PfTaskVo> tmpTaskList = taskService.getTaskListByActivity(tmpAcId);
                        if (tmpTaskList != null && tmpTaskList.size() > 0){
                            PfTaskVo tmpTaskVo = tmpTaskList.get(0);
                            if (tmpTaskVo != null){

                                //获取临时任务的参与人员，这个地方用于确定其他节点转过来，需要固定的人员信息（和第一个转过来的保持一致）
                                String targetUserid = tmpTaskVo.getUserVo().getUserId();
                                PfUser user = userService.findById(targetUserid);

                                //根据用户id，获取对应的角色map
                                LinkedHashMap<String,PfRole> roleMap = userRoleService.getRoleMapByUserid(targetUserid);

                                //根据工作流模型，获取当前节点定义模型，进而获取到模型中的转发人员配置
                                WorkFlowXml workXml = WorkFlowXmlUtil.getInstanceModel(workFlowInstanceVo);

                                //获取该节点的源节点，也就是上一级，判断是否大于一，否则不进行处理
                                //List<String> fromAcId = workXml.getToActivitys(targetAdId);

                                ActivityModel activityModel = workXml.getActivity(targetAdId);
                                //该方法只处理分支接收的and情况
                                if (activityModel.getJoinType().equalsIgnoreCase("and")) {
                                    //获取定义中人员转发列表，只处理角色类型的，不处理部门和人员
                                    List<PerformerModel> performerList = activityModel.getPerformerList();
                                    for (PerformerModel pModel : performerList) {
                                        if (roleMap.containsKey(pModel.getRoleId())){
                                            //定义单个节点的角色、人员过滤对象
                                            HashMap<String,String> filterUserMap = new HashMap<String, String>();
                                            filterUserMap.put("activityDefinitionId",targetAdId);
                                            filterUserMap.put("userId",targetUserid);
                                            filterUserMap.put("userName",user.getUserName());
                                            filterUserMap.put("roleId",pModel.getRoleId());
                                            filterUserMapList.put(targetAdId,filterUserMap);
                                            //查找到第一个符合条件的，退出当前节点，继续下一个节点的过滤
                                            break;
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
        }
        System.out.println(JSON.toJSONString(filterUserMapList));
        return filterUserMapList;
    }
}
