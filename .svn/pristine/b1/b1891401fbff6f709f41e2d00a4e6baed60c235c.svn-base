package com.gtis.portal.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gtis.common.util.ByteObjectAccess;
import com.gtis.common.util.CommonUtil;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.config.AppConfig;
import com.gtis.plat.service.*;
import com.gtis.plat.vo.*;
import com.gtis.plat.wf.WorkFlowInfo;
import com.gtis.portal.entity.PfBusinessGroup;
import com.gtis.portal.entity.PfMenu;
import com.gtis.portal.entity.PfResource;
import com.gtis.portal.service.*;
import com.gtis.portal.util.CalendarUtil;
import com.gtis.web.SessionUtil;
import com.gtis.web.SplitParamImpl;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 任务中心
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/1/8
 */
@Controller
@RequestMapping("/taskCenter")
public class TaskCenterController extends BaseController {
    @Autowired
    SysWorkFlowDefineService sysWorkFlowDefineService;
    @Autowired
    WorkFlowCoreService workFlowCoreService;
    @Autowired
    SysAuthorService sysAuthorService;
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    IndexService indexService;
    @Autowired
    PfWorkflowDefinitionService workflowDefinitionService;
    @Autowired
    PfBusinessGroupService businessGroupService;
    @Autowired
    SysWorkFlowInstanceRelService workFlowInstanceRelService;
    @Autowired
    WorkflowAutoTurnService workflowAutoTurnService;
    @Autowired
    PfResourceService resourceService;

    @RequestMapping("index")
    public String taskCenter(Model model,@RequestParam(value = "rid", required = false) String rid,HttpServletRequest req) throws Exception {
        model.addAttribute("autoTurnTask",AppConfig.getProperty("portal.tasklist.autoTurnTask"));
        String taskState = req.getParameter("taskState");
        model.addAttribute("taskState",taskState);
        //如果没有传递资源id，则自动获取任务中心的资源id
        if (StringUtils.isBlank(rid)){
            List<PfResource> resourceList = resourceService.getResourceListByUrl("taskCenter/index");
            if (resourceList != null && resourceList.size() > 0){
                rid = resourceList.get(0).getResourceId();
            }
        }
        model.addAttribute("rid",rid);
        if (StringUtils.isNotBlank(rid) && rid.startsWith("r:")){
            rid = rid.replace("r:","");
        }
        UserInfo userInfo = SessionUtil.getCurrentUser();
        Map<String,List<PfWorkFlowDefineVo>> mapWorkFlowDefine = workflowDefinitionService.getWorkFlowDefineMap(userInfo,rid);
        model.addAttribute("workFlowDefineMap",mapWorkFlowDefine);

        String createType = req.getParameter("createType");
        if(StringUtils.isBlank(createType)){
            createType = AppConfig.getProperty("portal.taskcenter.createtask.type");
        }

        model.addAttribute("createType",createType);
        if (StringUtils.isNotBlank(createType) && StringUtils.equalsIgnoreCase(createType,"group")){
            List<PfBusinessGroup> businessGroupList = businessGroupService.getBusinessGroupListByRole(userInfo,rid);
            if (businessGroupList == null){
                businessGroupList = new ArrayList<PfBusinessGroup>();
            }
            model.addAttribute("businessGroupList",businessGroupList);
        }

        List<PfBusinessVo> businessVoList= sysWorkFlowDefineService.getBusinessList();
        if (businessVoList != null && businessVoList.size() > 0) {
            for (int i = 0; i < businessVoList.size(); i++) {
                List tmpList = mapWorkFlowDefine.get(businessVoList.get(i).getBusinessName());
                if (tmpList == null || tmpList.size() < 1) {
                    businessVoList.remove(i);
                    i--;
                }
            }
        }
        model.addAttribute("businessList",businessVoList!=null?businessVoList:Lists.newArrayList());

        boolean hasDel=false;
        boolean hasRestart = false;
        StringBuffer bufferWdids = new StringBuffer("''");
        if (SessionUtil.getCurrentUser().isAdmin()) {
            hasDel=true;
            hasRestart = true;
            bufferWdids = new StringBuffer("");
        }else{
            String roles = SessionUtil.getCurrentUser().getRoleIds();
            List<PfPartitionInfoVo> lstfPartitions=sysAuthorService.getSystemResrouceFunAuthorList(roles,rid);
            if (lstfPartitions!=null){
                for (PfPartitionInfoVo partitionInfoVo:lstfPartitions){
                    if (partitionInfoVo.getElementName().endsWith("删除")
                            || partitionInfoVo.getElementName().equalsIgnoreCase("del")){
                        hasDel=true;
                        break;
                    }
                }
                for (PfPartitionInfoVo partitionInfoVo:lstfPartitions){
                    if (partitionInfoVo.getElementName().endsWith("重办")
                            || partitionInfoVo.getElementName().equalsIgnoreCase("restart")){
                        hasRestart=true;
                        break;
                    }
                }
            }
            List<String> listWdids=sysMenuService.getProjectWorkFlowDefineIds(roles);
            if (listWdids!=null){
                for(int index = 0; index < listWdids.size(); index ++){
                    bufferWdids.append(",");
                    bufferWdids.append("'" + listWdids.get(index) + "'");
                }
            }
            model.addAttribute("excludeWdids",bufferWdids.toString());
        }

        model.addAttribute("hasDel",hasDel);
        model.addAttribute("hasRestart",hasRestart);

        SplitParamImpl splitParam1=new SplitParamImpl();
        splitParam1.setQueryString("getTaskList");
        HashMap mapParam=Maps.newHashMap();
        if (StringUtils.isNotBlank(taskState)){
            mapParam.put("TASK_STATE",taskState);
        }
        mapParam.put("BEGIN_TIME",CalendarUtil.getPreviousYearFirst());
        mapParam.put("userIds",SessionUtil.getCurrentUserId().equals("0") ? null:SessionUtil.getCurrentUserIds());
        splitParam1.setQueryParam(mapParam);
        model.addAttribute("taskList", ByteObjectAccess.objectToString(splitParam1));

        splitParam1.setQueryString("getTaskOverList");
        model.addAttribute("taskOverList",ByteObjectAccess.objectToString(splitParam1));

        //如果当前人员不是管理员，则获取当前人员所属部门的行政区代码组合
        if (!SessionUtil.getCurrentUser().isAdmin()){
            //获取当前人员所属部门的行政区代码
            List<PfOrganVo> organList = SessionUtil.getCurrentUser().getLstOragn();
            if (organList != null && organList.size() > 0){
                String regionCodes = "''";
                HashMap<String,String> regionMap = new HashMap<String, String>();
                for (int i = 0; i < organList.size(); i++) {
                    regionMap.put(organList.get(i).getRegionCode(),organList.get(i).getRegionCode());
                }

                for(Map.Entry<String,String> entry : regionMap.entrySet()){
                    regionCodes += ",'"+entry.getKey()+"'";
                }
                mapParam.put("REGION_CODES",regionCodes);
            }
        }

        //项目列表（按照工作流实例权限来展示数据）
        mapParam.put("userIds",null);
        mapParam.put("wdids",bufferWdids.toString());
        splitParam1.setQueryString("getProjectList");
        model.addAttribute("projectList",ByteObjectAccess.objectToString(splitParam1));

        //参与的项目列表（按照人员参与过的项目展示）
        mapParam.put("wdids",null);
        mapParam.put("USER_ID",SessionUtil.getCurrentUserId());
        mapParam.put("REGION_CODES",null);
        splitParam1.setQueryParam(mapParam);
        splitParam1.setQueryString("getProjectListByPerformer");
        model.addAttribute("projectPerformerList",ByteObjectAccess.objectToString(splitParam1));

        model.addAttribute("currentDate",CommonUtil.getCurrTime());

        model.addAttribute("curOrgan",SessionUtil.getCurrentUser().getLstOragn().get(0));
        return returnPage("/task/task-center");
    }

    /**
     * 获取所有工作流定义信息
     * @param wdid
     * @return
     */
    @RequestMapping("workflowDefinition")
    @ResponseBody
    public Object getWorkflowDefinition(@RequestParam String wdid){
        Map workflowDefinitionMap = Maps.newHashMap();
        PfWorkFlowDefineVo pfWorkFlowDefineVo = null;
        if(StringUtils.isNotBlank(wdid)){
            pfWorkFlowDefineVo = sysWorkFlowDefineService.getWorkFlowDefine(wdid);
        }
        workflowDefinitionMap.put("workflowDefine",pfWorkFlowDefineVo);
        workflowDefinitionMap.put("username",SessionUtil.getCurrentUser().getUsername());
        workflowDefinitionMap.put("createTime", CommonUtil.getCurrStrDate());
        return workflowDefinitionMap;
    }
    /**
     * 创建任务
     * @param pfWorkFlowInstanceVo
     * @return
     * @throws Exception
     */
    @RequestMapping("createDefaultTask")
    @ResponseBody
    public Object createTask(PfWorkFlowInstanceVo pfWorkFlowInstanceVo) throws Exception{
        Map result = Maps.newHashMap();
        result.put("success",false);
        String userId = SessionUtil.getCurrentUserId();
        String proId = UUIDGenerator.generate();
        pfWorkFlowInstanceVo.setWorkflowIntanceId(proId);
        pfWorkFlowInstanceVo.setProId(proId);
        String taskId=null;
        try {
            WorkFlowInfo infoObj = workFlowCoreService.createWorkFlowInstance(pfWorkFlowInstanceVo, userId);

            for (PfTaskVo taskVo : infoObj.getTargetTasks()) {
                if (taskVo.getUserVo().getUserId().equals(userId)) {
                    taskId = taskVo.getTaskId();
                    break;
                }
            }
            if (StringUtils.isBlank(taskId)) {
                for (PfTaskVo taskVo : infoObj.getTargetTasks()) {
                    taskId = taskVo.getTaskId();
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        result.put("success",true);
        result.put("taskId",taskId);
        return result;
    }
    /**
     * 创建任务
     * @param wdid
     * @return
     * @throws Exception
     */
    @RequestMapping("createTask")
    @ResponseBody
    public Object createTask(String wdid) throws Exception{
        Map result = Maps.newHashMap();
        result.put("success",false);
        if(StringUtils.isNotBlank(wdid)){
            PfWorkFlowDefineVo pfWorkFlowDefineVo = sysWorkFlowDefineService.getWorkFlowDefine(wdid);
            String userId = SessionUtil.getCurrentUserId();
            String proId = UUIDGenerator.generate();
            PfWorkFlowInstanceVo pfWorkFlowInstanceVo = new PfWorkFlowInstanceVo();
            StringBuilder workflowInstanceName = new StringBuilder("新建项目");
            workflowInstanceName.append(Calendar.getInstance().getTimeInMillis());
            pfWorkFlowInstanceVo.setWorkflowIntanceName(workflowInstanceName.toString());
            pfWorkFlowInstanceVo.setWorkflowDefinitionId(wdid);
            pfWorkFlowInstanceVo.setCreateTime(Calendar.getInstance().getTime());
            pfWorkFlowInstanceVo.setTimeLimit(pfWorkFlowDefineVo.getTimeLimit());
            pfWorkFlowInstanceVo.setPriority("1");
            pfWorkFlowInstanceVo.setCreateUser(SessionUtil.getCurrentUserId());
            pfWorkFlowInstanceVo.setWorkflowIntanceId(proId);
            pfWorkFlowInstanceVo.setProId(proId);
            WorkFlowInfo infoObj = workFlowCoreService.createWorkFlowInstance(
                    pfWorkFlowInstanceVo, userId);
            String taskId=null;
            for (PfTaskVo taskVo : infoObj.getTargetTasks()) {
                if (taskVo.getUserVo().getUserId().equals(userId)) {
                    taskId = taskVo.getTaskId();
                    break;
                }
            }
            if (StringUtils.isBlank(taskId)) {
                for (PfTaskVo taskVo : infoObj.getTargetTasks()) {
                    taskId = taskVo.getTaskId();
                    break;
                }
            }
            result.put("success",true);
            result.put("taskId",taskId);
        }

        return result;
    }
    @RequestMapping("getWorkflowDefinitions")
    @ResponseBody
    public Object getWorkflowDefinitions(String businessId){
        List<PfWorkFlowDefineVo> workFlowDefineVoList = null;
        if(StringUtils.isBlank(businessId))
            workFlowDefineVoList = sysWorkFlowDefineService.getWorkFlowDefineList();
        else
            workFlowDefineVoList = sysWorkFlowDefineService.getWorkFlowDefineByBusiness(businessId);
        return workFlowDefineVoList;
    }

    @RequestMapping("indextask")
    @ResponseBody
    public Object getIndexTaskList(){
        Map result = Maps.newHashMap();
        result.put("success",true);
        result.put("msg","操作成功");
        //获取十条记录，用于综合资讯首页显示，十条记录分为超期和未超期的待办任务，按照紧急程度排序
        //显示规则：超期最多显示7条，剩余的条数用于显示正常的待办任务
        List<HashMap> mapList = indexService.getIndexTaskList();
        if (mapList == null){
            mapList = new ArrayList<HashMap>();
        }
        List<HashMap> dataList = new ArrayList<HashMap>();
        for (int i=0;i<mapList.size();i++){
            HashMap map = mapList.get(i);
            HashMap dataMap = new HashMap();
            dataMap.put("id",MapUtils.getString(map,"ASSIGNMENT_ID"));
//            dataMap.put("img", MapUtils.getString(map,""));
            dataMap.put("url", AppConfig.getProperty("server.url")+"/platform/taskhandle.action?taskid="+MapUtils.getString(map,"ASSIGNMENT_ID"));
            dataMap.put("title", MapUtils.getString(map,"WORKFLOW_INSTANCE_NAME"));
            dataMap.put("date", MapUtils.getString(map,"BEGIN_TIME"));
//            dataMap.put("content", MapUtils.getString(map,""));

            String sfcq = MapUtils.getString(map,"SFCQ");
            if (StringUtils.equals("cqrw",sfcq)){
                dataMap.put("type", "top");
            }else {
                dataMap.put("type", "down");
            }
//            dataMap.put("date", MapUtils.getString(map,""));
            dataList.add(dataMap);
        }
        result.put("data", dataList);
        return result;
    }

    /**
     * @return
     */
    @RequestMapping("relcount")
    @ResponseBody
    public Object getRelCount(@RequestParam String proid){
        Integer count = 0;
        List<PfWorkFlowInstanceVo> list = workFlowInstanceRelService.getWorkFlowRelList(proid);
        if (list != null){
            count = list.size();
        }
        return count;
    }

    @RequestMapping("autoTurn")
    @ResponseBody
    public Object autoTurn(String paramString) throws Exception{
        Map result = Maps.newHashMap();
        result.put("success",false);
        result.put("msg","操作异常！");
        try {
            if (StringUtils.isNotBlank(paramString)){
                paramString = java.net.URLDecoder.decode(paramString,"utf-8");
                JSONArray jsonArray = JSON.parseArray(paramString);
                String userid = SessionUtil.getCurrentUserId();
                if (jsonArray != null && jsonArray.size() > 0){
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String proid = MapUtils.getString(jsonObject,"proid");
                        String taskid = MapUtils.getString(jsonObject,"taskid");
                        if (StringUtils.isNotBlank(proid) && StringUtils.isNotBlank(taskid)){
                            System.out.println(proid+"-----"+taskid);
                            workflowAutoTurnService.autoTurnWorkflowByAdId(proid,taskid,userid);
                        }
                    }
                }
            }
            result.put("success",true);
            result.put("msg","操作成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
