package com.gtis.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.gtis.portal.dao.BaseDao;
import com.gtis.portal.service.*;
import com.gtis.portal.util.CommonUtils;
import com.gtis.portal.util.RequestUtils;
import com.gtis.web.SessionUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Value("${egov.conf}/portal/index_zhsw.json")
    private String location;
    @Autowired
    BaseDao baseDao;

    public Map getConfigIndexZhswUrl(){
        try {
            if (CommonUtils.urlMap == null || CommonUtils.urlMap.keySet().size() <=0 ){
                if(StringUtils.isNotBlank(location)){
                    Map resourceConfig = (Map) CommonUtils.readJsonFile(location);
                    Map<String, Object> urlMap = resourceConfig.containsKey("dataUrl")?(Map)resourceConfig.get("dataUrl"):null;
                    //处理url参数
                    for(Map.Entry entry : urlMap.entrySet()){
                        Object obj = entry.getValue();
                        if (obj != null){
                            Map<String,String> valueMap = (Map<String,String>)obj;
                            for (Map.Entry<String, String> ventry : valueMap.entrySet()) {
                                String key = ventry.getKey();
                                if (StringUtils.containsIgnoreCase(key, "url")){
                                    if (StringUtils.isNotBlank(ventry.getValue())){
                                        String url = RequestUtils.initOptProperties(ventry.getValue());
                                        ventry.setValue(url);
                                    }
                                }
                            }
                        }
                    }
//                    System.out.println(JSON.toJSONString(urlMap));
                    CommonUtils.urlMap=urlMap;
                }
            }
            return CommonUtils.urlMap;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取十条记录，用于综合资讯首页显示，十条记录分为超期和未超期的待办任务，按照紧急程度排序
     * 显示规则：超期最多显示7条，剩余的条数用于显示正常的待办任务
     * @return
     */
    public List<HashMap> getIndexTaskList(){
        List<HashMap> mapList = new ArrayList<HashMap>();

        HashMap param = new HashMap();
        String userid = SessionUtil.getCurrentUserId();
        if (StringUtils.equals("0",userid)){
            userid = null;
        }else {
            param.put("userid",userid);
        }
        param.put("cqrw",true);
        List<HashMap> cqList = getPfTaskList(param,7,"cqrw");
        if (cqList != null){
            mapList.addAll(cqList);
            //计算需要查询出正常任务的记录数
            param.remove("cqrw");
            param.put("zcrw",true);
            List<HashMap> zcList = getPfTaskList(param,10-cqList.size(),"zcrw");
            if (zcList != null){
                mapList.addAll(zcList);
            }
        }
        return mapList;
    }

    public List<HashMap> getPfTaskList(HashMap param,Integer rownum,String type){
        try {
            String sql = "select t.* from ( ";
            String tasksql = " select t1.ASSIGNMENT_ID,t1.BEGIN_TIME,t1.OVER_TIME as TASK_OVER_TIME,t2.ACTIVITY_NAME, " +
                    " t3.WORKFLOW_INSTANCE_NAME,t3.WORKFLOW_INSTANCE_ID,t3.PRIORITY,t3.OVER_TIME as OVER_TIME, " +
                    " t4.WORKFLOW_NAME,t6.USER_NAME CREATE_USERNAME, '"+type+"' as sfcq " +
                    " from PF_ASSIGNMENT t1,PF_ACTIVITY t2,Pf_Workflow_Instance t3, PF_WORKFLOW_DEFINITION t4,pf_user t6 " +
                    " where t1.activity_id=t2.activity_id and t2.workflow_instance_id=t3.workflow_instance_id " +
                    " and t3.workflow_definition_id=t4.workflow_definition_id " +
                    " and t3.create_user=t6.user_id and t2.ACTIVITY_STATE=1 and (t3.WORKFLOW_STATE=1 or t3.WORKFLOW_STATE=3) ";
            if (param != null){
                if (param.containsKey("userid")){
                    String userSqlWhere = " and t1.USER_ID = '"+ MapUtils.getString(param,"userid")+"' ";
                    tasksql += userSqlWhere;
                }
                //不超期任务（正常人物）
                if (param.containsKey("zcrw") && MapUtils.getBooleanValue(param,"zcrw")){
                    String bcqSqlWhere = " and (t1.OVER_TIME >= sysdate and t3.OVER_TIME >= sysdate) ";
                    tasksql += bcqSqlWhere;
                }
                //超期任务
                if (param.containsKey("cqrw") && MapUtils.getBooleanValue(param,"cqrw")){
                    String cqSqlWhere = " and (t1.OVER_TIME < sysdate or t3.OVER_TIME < sysdate) ";
                    tasksql += cqSqlWhere;
                }
            }

            tasksql += " order by t1.begin_time desc ";
            sql += tasksql + " ) t ";
            if (rownum != null){
                sql += "  where  rownum<= " + rownum;
            }
//            System.out.println(sql);
            List<HashMap> queryResults = baseDao.getMapBySql(sql);
            return  queryResults;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
