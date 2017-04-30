package com.gtis.portal.web;

import com.gtis.common.http.Utf8PostMethod;
import com.gtis.config.AppConfig;
import com.gtis.fileCenter.service.NodeService;
import com.gtis.plat.service.*;
import com.gtis.plat.vo.PfActivityVo;
import com.gtis.plat.vo.PfUserVo;
import com.gtis.plat.wf.WorkFlowInfo;
import com.gtis.plat.wf.WorkFlowTransInfo;
import com.gtis.plat.wf.model.ActivityModel;
import com.gtis.plat.wf.model.PerformerTaskModel;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 转发
 * @author <a href="mailto:zhangxing@gtmap.cn">zhangxing</a>
 * @version 1.0, 2015/1/8
 */
@Controller
@RequestMapping("/turnBackWorkFlow")
public class TurnBackWorkFlowController {
    private static final Log log = LogFactory.getLog(TurnBackWorkFlowController.class);
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


    @RequestMapping("")
    public String turnBackWorkFlow(Model model,@RequestParam(value = "taskid", required = false) String taskid,HttpServletRequest request) throws Exception {

        String userId= SessionUtil.getUserId(request);
        WorkFlowInfo info=workFlowService.getWorkFlowTurnBackInfo(userId, taskid);
        List<PfActivityVo> backActivitys=info.getTargetActivitys();
        if(backActivitys==null)
            backActivitys=new ArrayList<PfActivityVo>();
        model.addAttribute("taskid",taskid);
        model.addAttribute("backActivitys",backActivitys);

        return "/task/handle/turn-back-workflow";
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
        Document doc = DocumentHelper.createDocument();
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
                    if (vo.getUserId().equals(SessionUtil.getCurrentUserId())){
                        User.addAttribute("DefaultSelected", "true");
                        UserInfo.addAttribute("DefaultSelected", "true");
                    }
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
            HttpClient httpClient = new HttpClient();
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
}
