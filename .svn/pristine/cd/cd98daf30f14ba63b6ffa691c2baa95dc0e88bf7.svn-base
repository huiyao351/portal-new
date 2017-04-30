package com.gtis.portal.web;

import com.alibaba.fastjson.JSON;
import com.gtis.config.AppConfig;
import com.gtis.fileCenter.model.Node;
import com.gtis.fileCenter.model.Space;
import com.gtis.fileCenter.service.NodeService;
import com.gtis.plat.service.*;
import com.gtis.plat.vo.*;
import com.gtis.plat.wf.model.ActivityModel;
import com.gtis.plat.wf.model.RiskModel;
import com.gtis.portal.model.Menu;
import com.gtis.portal.util.*;
import com.gtis.web.SessionUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 任务办理中心
 * @author <a href="mailto:zhangxing@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/1/8
 */
@Controller
@RequestMapping("/sign")
public class SignController {
    /** 签名服务 */
    @Autowired
    private SysSignService signService;
    /** 授权服务 */
    @Autowired
    private SysAuthorService authorService;
    /** 默认意见服务 */
    @Autowired
    private SysOpinionService opinionService;
    /** 工作流定义服务 */
    @Autowired
    private SysWorkFlowDefineService workFlowDefineService;
    /** 工作流服务 */
    @Autowired
    private SysWorkFlowInstanceService workFlowInstanceService;
    /**工作流任务服务*/
    @Autowired
    private SysTaskService sysTaskService;

    @RequestMapping("tag")
    public String signtag(Model model,String taskid,HttpServletRequest request) throws Exception {
        /** 显示意见的字体大小 */
        String optinionSize="18";
        /** 图片地址   */
        String imagesUrl=null;
        /**  图片宽度  */
        String imageWidth="52";
        /** 图片高度  */
        String imageHeight="24";

        Map parameterMap = request.getParameterMap();
        /** 是否新建签名 */
        boolean createNew = true;
        /** 是否可操作 */
        boolean disabled = MapUtils.getBooleanValue(parameterMap,"disabled");//false;
        /** 意见类型 */
        String opinionType=null;
        /** 是否填写意见 */
        boolean isOpinion = true;
        if (StringUtils.equalsIgnoreCase("false",request.getParameter("opinion"))){
            isOpinion=false;
        }
        /** 是否按用户编号排序 */
        boolean orderByUserNo = MapUtils.getBooleanValue(parameterMap,"orderByUserNo");//false;

        boolean showSignDate=true;
        if (StringUtils.equalsIgnoreCase("false",MapUtils.getString(parameterMap,"showSignDate"))){
            showSignDate=false;
        }
        /**是否进行签名前用户密码确认*/
        boolean signUserCheck = MapUtils.getBooleanValue(parameterMap,"signUserCheck");

        PfSignVo signVo = initSignByRequest(request);
        List<PfSignVo> lstSign = new ArrayList<PfSignVo>();
        if(StringUtils.isNotBlank(signVo.getSignId())){
            lstSign.add(signService.getSign(signVo.getSignId()));
        } else {
            HashMap map=new HashMap();
            map.put("signKey",signVo.getSignKey()) ;
            map.put("proId",signVo.getProId()) ;
            if(orderByUserNo)
                map.put("_orderfield_","t2.USER_NO");
            lstSign = signService.getSignListOrderfield(map);
        }
        UserInfo userInfo = SessionUtil.getCurrentUser();
        signVo.setUserId(userInfo.getId());
        signVo.setSignName(userInfo.getUsername());
        if (disabled) {
            createNew = false;
        }else {
            for (PfSignVo sign : lstSign) {
                if (signVo.getUserId().equals(sign.getUserId())) {
                    createNew = false;
                    signVo = sign;
                    break;
                }
            }
        }
        signUserCheck = AppConfig.getBooleanProperty("signUserCheck.enable",false);
        disabled = btnDisable(request,disabled);

        model.addAttribute("optinionSize",optinionSize);
        model.addAttribute("imagesUrl",imagesUrl);
        model.addAttribute("imageWidth",imageWidth);
        model.addAttribute("imageHeight",imageHeight);
        model.addAttribute("opinionType",opinionType);
        model.addAttribute("showSignDate",showSignDate);
        model.addAttribute("orderByUserNo",orderByUserNo);
        model.addAttribute("isOpinion",isOpinion);
        model.addAttribute("lstSign",lstSign);
        model.addAttribute("createNew",createNew);
        model.addAttribute("signUserCheck",signUserCheck);
        model.addAttribute("disabled",disabled);

        model.addAttribute("signVo",signVo);
        if (isOpinion) {
            return "/template/sign/sign";
        }else
            return "/template/sign/sign-nooptinion";
    }

    /**
     * 签名图片
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/image",method = RequestMethod.GET)
    public void image(Model model,HttpServletResponse response,HttpServletRequest request) throws Exception {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        PfSignVo signVo = new PfSignVo();
        if (response != null){
            signVo.setSignId(request.getParameter("signVo.signId"));
        }
        if (StringUtils.isNotBlank(signVo.getSignId())) {
            PfSignVo signBean = signService.getSign(signVo.getSignId());
            PfSignVo sign = signService.getSignImage(signVo.getSignId());
            if(sign == null){
                IOUtils.write("", response.getOutputStream());
            }else{
                if (sign.getSignImage()!=null && sign.getSignImage().length>1000){
                    IOUtils.write(sign.getSignImage(),response.getOutputStream());
                }else if (signBean!=null){
                    //如果签名图片不存在就生成一个
                    SignUitl.BuildSignImage(signBean.getSignName(), response.getOutputStream());
                }
            }
        }
    }

    /**
     * 开始进入签名
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sign",method = RequestMethod.GET)
    public String opensign(Model model,HttpServletRequest request,String opinionType,String signNoOptinion) throws Exception {
        PfSignVo signVo = initSignByRequest(request);
        if (signVo != null){
            UserInfo userInfo = SessionUtil.getCurrentUser();
            String taskid = request.getParameter("taskid");
            if (StringUtils.isNotBlank(signVo.getSignId())){
                signVo = signService.getSign(signVo.getSignId());
            }
            if (signVo == null){
                signVo = new PfSignVo();
            }
            if (StringUtils.isNotBlank(signVo.getSignId())){
                signVo.setSignId(signVo.getSignId());
            }
            if (StringUtils.isNotBlank(signVo.getProId())){
                signVo.setProId(request.getParameter("signVo.proId"));
            }
            if (StringUtils.isNotBlank(signVo.getSignOpinion())){
                signVo.setSignOpinion(request.getParameter("signVo.signOpinion"));
            }
            if (StringUtils.isNotBlank(signVo.getSignKey())){
                signVo.setSignKey(request.getParameter("signVo.signKey"));
            }
            if (signVo.getSignDate() == null) {
                String signDate = request.getParameter("signVo.signDate");
                if (StringUtils.isNotBlank(signDate)){
                    signVo.setSignDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(signDate));
                }
                //signVo.setSignDate(Calendar.getInstance().getTime());
            }

            model.addAttribute("signVo",signVo);
            model.addAttribute("userId",userInfo.getId());
            model.addAttribute("taskid",taskid);
        }
        //获取签名意见类型
        if (StringUtils.isBlank(opinionType) && StringUtils.isNotBlank(signVo.getProId())) {
            PfWorkFlowInstanceVo workflowInstance = workFlowInstanceService.getWorkflowInstanceByProId(signVo.getProId());
            if (workflowInstance!=null){
                PfWorkFlowDefineVo workFlowDefineVo = workFlowDefineService.getWorkFlowDefine(workflowInstance.getWorkflowDefinitionId());
                opinionType = workFlowDefineVo.getWorkflowName();
            }
        }
        model.addAttribute("opinionType",opinionType);
        model.addAttribute("signNoOptinion",signNoOptinion);
        if (StringUtils.equalsIgnoreCase("true",signNoOptinion)){
            return "/sign/signNo";
        }
        return "/sign/sign";
    }

    /**
     * 获得默认意见
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/menu",method = RequestMethod.GET)
    public List<PfOpinionVo> menu(Model model,HttpServletResponse response,HttpServletRequest request) throws Exception{
        List<PfOpinionVo> lstOpinions= null;
        if (request != null){
            String taskid = request.getParameter("taskid");
            String opinionType = request.getParameter("opinionType");
            UserInfo userInfo = SessionUtil.getCurrentUser();
            String UserId = userInfo.getId();
            String activityName="";
            if(StringUtils.isNotBlank(taskid)){
                activityName = sysTaskService.getActivity(sysTaskService.getTask(taskid).getActivityId()).getActivityName();
                String wiid = sysTaskService.getActivity(sysTaskService.getTask(taskid).getActivityId()).getWorkflowInstanceId();
                opinionType = workFlowDefineService.getWorkFlowDefine(workFlowInstanceService.getWorkflowInstance(wiid).getWorkflowDefinitionId()).getWorkflowName();
            }
            if(StringUtils.isNotBlank(activityName))
                lstOpinions=opinionService.getOpinionList(UserId,opinionType,activityName);
            else
                lstOpinions=opinionService.getOpinionList(UserId,opinionType);
        }
        return lstOpinions;
    }

    /**
     * 自动签名
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/autosign",method = RequestMethod.GET)
    public Object autosign(Model model,HttpServletResponse response,HttpServletRequest request) throws Exception {
        PfSignVo signVo = initSignByRequest(request);
        if (signVo != null){
            PfSignVo sign = signService.getSign(signVo.getSignId());
            if (sign != null) {
                sign.setSignOpinion(signVo.getSignOpinion());
                sign.setSignDate(signVo.getSignDate());
                signService.updateAutoSign(sign);
                signVo = sign;
            } else {
                signService.insertAutoSign(signVo);
            }
        }
        return signVo;
    }

    /**
     * 签名保存
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/savesign",method = RequestMethod.GET)
    public Object savesign(Model model,HttpServletResponse response,HttpServletRequest request,String signPoints) throws Exception {
        PfSignVo signVo = initSignByRequest(request);
        if (signVo != null){
            PfSignVo sign = signService.getSign(signVo.getSignId());
            if (sign != null) {
                sign.setSignOpinion(signVo.getSignOpinion());
                if (signVo.getSignDate() == null) {
                    signVo.setSignDate(new Date(System.currentTimeMillis()));
                }
                sign.setSignDate(signVo.getSignDate());
                sign.setSignType("0");
                signService.updateSign(sign);  //更新属性
                signService.UpdateCustomSignPic(signVo.getSignId(),signPoints);
                signVo = sign;
            } else {
                String signId=signService.AddCustomSign(SessionUtil.getCurrentUserId(),signVo.getProId(),signVo.getSignKey(),signPoints);
                signVo.setSignId(signId);
                signVo.setSignType("0");
                signService.updateSign(signVo);  //更新属性
            }
        }
        return signVo;
    }

    /**
     * 仅保存意见
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveSignOpinion",method = RequestMethod.GET)
    public Object saveSignOpinion(Model model,HttpServletResponse response,HttpServletRequest request) throws Exception {
        PfSignVo signVo = initSignByRequest(request);
        if (signVo != null){
            PfSignVo sign = signService.getSign(signVo.getSignId());
            if (sign == null) {
                signService.insertAutoSign(signVo);
            }
            sign.setSignOpinion(signVo.getSignOpinion());
            if (signVo.getSignDate() == null) {
                signVo.setSignDate(Calendar.getInstance().getTime());
            }
            sign.setSignDate(signVo.getSignDate());
            signService.updateSign(signVo);
        }
        return signVo;
    }

    /**
     * 仅保存签名日期
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveSignDate",method = RequestMethod.GET)
    public Object saveSignDate(Model model,HttpServletResponse response,HttpServletRequest request) throws Exception {
        PfSignVo signVo = initSignByRequest(request);
        if (signVo != null){
            PfSignVo sign = signService.getSign(signVo.getSignId());
            if (sign == null) {
                signService.insertAutoSign(signVo);
            }
            if (signVo.getSignDate() == null) {
                signVo.setSignDate(Calendar.getInstance().getTime());
            }
            sign.setSignDate(signVo.getSignDate());
            signService.updateSign(sign);
        }
        return signVo;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteSign",method = RequestMethod.GET)
    public void deleteSign(Model model,HttpServletResponse response,HttpServletRequest request) throws Exception {
        PfSignVo signVo = initSignByRequest(request);
        if (signVo != null){
            if (StringUtils.isNotBlank(signVo.getSignId())){
                signService.deleteSign(signVo.getSignId());
            }
        }
    }

    /**
     * 判断签名按钮是否有效,需要根据权限
     */
    private boolean btnDisable(HttpServletRequest request,boolean disabled) {
        PfSignVo signVo = initSignByRequest(request);
        String disable = request.getParameter("disable");
        if(!"true".equals(disable)){
            if (!disabled) {
                String from = request.getParameter("from");
                String rid = request.getParameter("rid");
                String wiid = request.getParameter("wiid");
                if (StringUtils.isBlank(wiid)){
                    PfWorkFlowInstanceVo workFlowInstanceVo=workFlowInstanceService.getWorkflowInstanceByProId(signVo.getProId());
                    if(workFlowInstanceVo != null){
                        wiid=   workFlowInstanceVo.getWorkflowIntanceId();
                    }
                }
                if (!StringUtils.isBlank(from) && !StringUtils.isBlank(rid) && !StringUtils.isBlank(wiid)) {
                    if ("task".equalsIgnoreCase(from)){
                        String taskid= request.getParameter("taskid");
                        disabled= authorService.queryTaskElement(wiid,taskid,rid,signVo.getSignKey());
                    }else if ("pro".equalsIgnoreCase(from)){
                        String roles = SessionUtil.getUserInfo(request).getRoleIds();
                        disabled=true;
                    }else{
                    }
                }
            }
        }else{
            disabled = true;
        }
        return disabled;
    }

    private PfSignVo initSignByRequest(HttpServletRequest request){
        PfSignVo signVo = null;
        try {
            if (request != null){
                signVo = new PfSignVo();
                signVo.setSignId(request.getParameter("signVo.signId"));
                signVo.setProId(request.getParameter("signVo.proId"));
                signVo.setSignOpinion(request.getParameter("signVo.signOpinion"));
                signVo.setSignKey(request.getParameter("signVo.signKey"));
                String signDate = request.getParameter("signVo.signDate");
                if (StringUtils.isNotBlank(signDate)){
                    signVo.setSignDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(signDate));
                }
                if (signVo.getSignDate() == null) {
                    signVo.setSignDate(Calendar.getInstance().getTime());
                }
            }
        }catch (Exception e){

        }
        return signVo;
    }


    @RequestMapping("signCheck")
    public String openSignCheck(Model model,HttpServletResponse response,HttpServletRequest request){
        return "/sign/sign-check";
    }

}
