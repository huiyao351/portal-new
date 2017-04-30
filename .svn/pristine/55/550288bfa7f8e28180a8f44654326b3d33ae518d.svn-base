package com.gtis.portal.service.impl;

import com.gtis.common.util.UUIDGenerator;
import com.gtis.config.AppConfig;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.PfInstanceAuthorizeService;
import com.gtis.portal.service.PfResourcePartitionService;
import com.gtis.portal.service.PfUserOrganService;
import com.gtis.portal.util.RequestUtils;
import com.mysema.query.jpa.impl.JPADeleteClause;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class PfInstanceAuthorizeServiceImpl extends BaseServiceImpl<PfInstanceAuthorize, String> implements PfInstanceAuthorizeService {
    @Autowired
    PfResourcePartitionService resourcePartitionService;

    public List<PfInstanceAuthorize> getAuthorizeListByWdId(String wdid){
        String jpql = "select t from PfInstanceAuthorize t where t.workflowDefinitionId=?0";
        return baseDao.getByJpql(jpql,wdid);
    }
    public PfInstanceAuthorize getAuthorizeListByParam(String wdid,String roleId,String resourceId){
        if (StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(roleId) && StringUtils.isNotBlank(resourceId)){
            String jpql = "select t from PfInstanceAuthorize t where t.workflowDefinitionId=?0 and  t.roleId=?1 and  t.resourceId=?2";
            return (PfInstanceAuthorize)baseDao.getUniqueResultByJpql(jpql,wdid,roleId,resourceId);
        }
        return null;
    }

    public boolean checkHasRole(String wdid,String roleId){
        if (StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(roleId)){
            String jpql = "select t from PfInstanceAuthorize t where t.workflowDefinitionId=?0 and  t.roleId=?1 and rownum=1";
            PfInstanceAuthorize obj = (PfInstanceAuthorize)baseDao.getUniqueResultByJpql(jpql,wdid,roleId);
            if (obj != null && StringUtils.isNotBlank(obj.getWauthorizeId())){
                return true;
            }
        }
        return false;
    }

    public boolean checkHasResource(String wdid,String resourceId){
        if (StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(resourceId)){
            String jpql = "select t from PfInstanceAuthorize t where t.workflowDefinitionId=?0 and  t.resourceId=?1 and rownum=1";
            PfInstanceAuthorize obj = (PfInstanceAuthorize)baseDao.getUniqueResultByJpql(jpql,wdid,resourceId);
            if (obj != null && StringUtils.isNotBlank(obj.getWauthorizeId())){
                return true;
            }
        }
        return false;
    }
    public List<PfRole> getAuthorizeRoleListByWdId(String wdid){
        String jpql = "select distinct t1 from PfRole t1,PfInstanceAuthorize t " +
                " where t.roleId=t1.roleId and t.workflowDefinitionId=?0 order by t1.roleNo";
        return baseDao.getByJpql(jpql,wdid);
    }
    public List<PfResource> getAuthorizeResourceListByWdId(String wdid,String roleId,boolean ischeck){
        if (StringUtils.isNotBlank(wdid)){
            String jpql = "select distinct t1 from PfResource t1,PfInstanceAuthorize t " +
                    " where t.resourceId=t1.resourceId and t.workflowDefinitionId=?0 ";
            if (StringUtils.isNotBlank(roleId)){
                jpql += " and t.roleId=?1 ";
            }
            if (ischeck){
                jpql += " and t.visible=1 ";
            }
            jpql += " order by t1.resourceNo ";
            return baseDao.getByJpql(jpql,wdid,roleId);
        }
        return null;


//        List<PfInstanceAuthorize> objList = null;
//        String sql = "select distinct t.wauthorize_id,t.workflow_definition_id,t.role_id,t.resource_id,t1.resource_name,t1.resource_no " +
//            " from pf_instance_authorize t ,pf_resource t1 " +
//            " where t.resource_id=t1.resource_id " +
//            " and t.workflow_definition_id=?0 ";
//        if (StringUtils.isNotBlank(roleId)){
//            sql += " and t.role_id=?1 ";
//        }
//        if (ischeck){
//            sql += " and t.visible=1 ";
//        }
//        sql += " order by t1.resource_no ";
//        List<HashMap> mapList = baseDao.getMapBySql(sql, wdid, roleId);
//        if (mapList != null && mapList.size() > 0){
//            objList = new ArrayList<PfInstanceAuthorize>();
//            for (int i = 0; i < mapList.size(); i++) {
//                HashMap map = mapList.get(i);
//                PfInstanceAuthorize obj = new PfInstanceAuthorize();
//                obj.setWauthorizeId(MapUtils.getString(map,"WAUTHORIZE_ID"));
//                obj.setWorkflowDefinitionId(MapUtils.getString(map, "WORKFLOW_DEFINITION_ID"));
//                obj.setRoleId(MapUtils.getString(map, "ROLE_ID"));
//                obj.setResourceId(MapUtils.getString(map, "RESOURCE_ID"));
//                obj.setResourceName(MapUtils.getString(map, "RESOURCE_NAME"));
//                objList.add(obj);
//            }
//        }
//        return baseDao.getByJpql(jpql,wdid,roleId);
    }

    public List<Ztree> getAuthorizeRoleTreeListByWdId(String wdid){
        List<PfRole> roleList = getAuthorizeRoleListByWdId(wdid);
        List<Ztree> treeList = new ArrayList<Ztree>();
        if (roleList != null && roleList.size() > 0){
            for (int i = 0; i < roleList.size(); i++) {
                Ztree tree = toZtreeByRole(roleList.get(i));
                treeList.add(tree);
            }
        }
        return treeList;
    }
    public List<Ztree> getAuthorizeResourceTreeListByWdId(String wdid,String roleId,boolean ischeck){
        List<PfResource> objList = getAuthorizeResourceListByWdId(wdid,roleId,ischeck);
        List<Ztree> treeList = new ArrayList<Ztree>();
        if (objList != null && objList.size() > 0){
            for (int i = 0; i < objList.size(); i++) {
                Ztree tree = toZtreeByResource(objList.get(i));
                treeList.add(tree);
            }
        }
        return treeList;
    }

    public HashMap<String,PfResourcePartition> getInstancePartMap(String wdid,String roleId,String resourceId){
        HashMap<String,PfResourcePartition> partMap = new HashMap<String, PfResourcePartition>();
        try {
            PfInstanceAuthorize instanceAuthorize = getAuthorizeListByParam(wdid, roleId, resourceId);
            if (instanceAuthorize != null && StringUtils.isNotBlank(instanceAuthorize.getAuthorizeInfo())){
                String infoXml = instanceAuthorize.getAuthorizeInfo();
                Document document = DocumentHelper.parseText(infoXml);
                //<Partitions><Partition Id="06O90137AXG5W301" OperType="1" PartitionType="0"/><Partition Id="4C77602678BA44E38A07318E7BBE004A" OperType="2" PartitionType="0"/></Partitions>
                List eleList = document.selectNodes("/Partitions/Partition");
                if (eleList != null && eleList.size() > 0){
                    for (int i = 0; i < eleList.size(); i++) {
                        Element element = (Element)eleList.get(i);
                        PfResourcePartition part = new PfResourcePartition();
                        part.setPartitionId(element.valueOf("@Id"));
                        String partType = element.valueOf("@PartitionType");
                        if (StringUtils.isNotBlank(partType)){
                            part.setPartitionType(Integer.valueOf(partType));
                        }
                        String operType = element.valueOf("@OperType");
                        if (StringUtils.isNotBlank(operType)){
                            part.setOperType(Integer.valueOf(operType));
                        }
                        partMap.put(part.getPartitionId(),part);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return partMap;
    }

    @Transactional
    public void updateVisible(String wauthorizeId,Integer visible){
        if (StringUtils.isNotBlank(wauthorizeId) && visible != null){
            String jpql = "update from PfInstanceAuthorize t set t.visible=?0 where t.wauthorizeId=?1";
            baseDao.executeJpql(jpql,visible,wauthorizeId);
        }
    }
    @Transactional
    public void updatePartAuthorizeInfo(String wdid,String roleId,String resourceId,String authorizeInfo){
        if (StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(roleId) && StringUtils.isNotBlank(resourceId)){
            String jpql = "update from PfInstanceAuthorize t set t.authorizeInfo=?0 where t.workflowDefinitionId=?1 and  t.roleId=?2 and  t.resourceId=?3";
            baseDao.executeJpql(jpql,authorizeInfo,wdid,roleId,resourceId);
        }
    }

    @Transactional
    public void addRoleRel(String wdid,List<ZtreeChanged> changeList){
        if (changeList != null && changeList.size() > 0 && StringUtils.isNotBlank(wdid)){
            List<PfResource> resouceList = getAuthorizeResourceListByWdId(wdid,null,false);
            if (resouceList == null || resouceList.size() == 0){
                resouceList = new ArrayList<PfResource>();
                PfResource resouce = new PfResource();
                resouce.setResourceId("FIRST_ROLE_AUTH_RESOUCE");
                resouceList.add(resouce);
            }
            if (resouceList != null && resouceList.size() > 0){
                for (int i = 0; i < changeList.size(); i++) {
                    ZtreeChanged change = changeList.get(i);
                    if (change.isLeaf()){
                        boolean hasRole = checkHasRole(wdid,change.getId());
                        if (!hasRole){
                            for (int j = 0; j < resouceList.size(); j++) {
                                PfInstanceAuthorize authorize = new PfInstanceAuthorize();
                                authorize.setWauthorizeId(UUIDGenerator.generate18());
                                authorize.setWorkflowDefinitionId(wdid);
                                authorize.setRoleId(change.getId());
                                authorize.setResourceId(resouceList.get(j).getResourceId());
                                insert(authorize);
                            }
                        }
                    }
                }
            }
        }
    }

    @Transactional
    public void addResourceRel(String wdid,List<ZtreeChanged> changeList){
        if (changeList != null && changeList.size() > 0 && StringUtils.isNotBlank(wdid)){
            List<PfRole> roleList = getAuthorizeRoleListByWdId(wdid);
            if (roleList != null && roleList.size() > 0){
                for (int i = 0; i < changeList.size(); i++) {
                    ZtreeChanged change = changeList.get(i);
                    if (change.isLeaf()){
                        boolean hasRole = checkHasResource(wdid, change.getId());
                        if (!hasRole){
                            for (int j = 0; j < roleList.size(); j++) {
                                PfInstanceAuthorize authorize = new PfInstanceAuthorize();
                                authorize.setWauthorizeId(UUIDGenerator.generate18());
                                authorize.setWorkflowDefinitionId(wdid);
                                authorize.setRoleId(roleList.get(j).getRoleId());
                                authorize.setResourceId(change.getId());
                                insert(authorize);
                            }
                        }
                    }
                }
            }
        }
    }

    @Transactional
    public void updateResourceRel(String wdid,String roleId,List<ZtreeChanged> changeList){
        if (changeList != null && changeList.size() > 0 && StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(roleId)){
            for (int i = 0; i < changeList.size(); i++) {
                ZtreeChanged change = changeList.get(i);
                PfInstanceAuthorize authorize = getAuthorizeListByParam(wdid,roleId,change.getId());
                Integer visible = change.isChecked()?1:0;
                if (authorize != null){
                    //更新选择状态
                    String wauthorizeId = authorize.getWauthorizeId();
                    updateVisible(wauthorizeId, visible);
                }else {
                    authorize = new PfInstanceAuthorize();
                    authorize.setWauthorizeId(UUIDGenerator.generate18());
                    authorize.setWorkflowDefinitionId(wdid);
                    authorize.setRoleId(roleId);
                    authorize.setResourceId(change.getId());
                    authorize.setVisible(visible);
                    insert(authorize);
                }
            }
        }
    }

    @Transactional
    public void updatePartOperType(String wdid,String roleId,String resourceId,List<ZtreeChanged> changeList){
        if (changeList != null && changeList.size() > 0 && StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(roleId) && StringUtils.isNotBlank(resourceId)){
            List<PfResourcePartition> partList = resourcePartitionService.getListByRid(resourceId);
            HashMap<String,Integer> partMap = new HashMap<String, Integer>();
            if (partList != null && partList.size() > 0){
                for (int i = 0; i < partList.size(); i++) {
                    partMap.put(partList.get(i).getPartitionId(),partList.get(i).getPartitionType());
                }
            }
            try {
                Document document = DocumentHelper.createDocument();
                Element rootE = document.addElement("Partitions");
                for (int i = 0; i < changeList.size(); i++) {
                    ZtreeChanged change = changeList.get(i);
                    Element childE = rootE.addElement("Partition");
                    childE.addAttribute("Id",change.getId());
                    childE.addAttribute("OperType",change.getName());
                    childE.addAttribute("PartitionType",MapUtils.getString(partMap,change.getId()));
                }
                //创建字符串缓冲区
                StringWriter stringWriter = new StringWriter();
                //设置文件编码
                OutputFormat xmlFormat = new OutputFormat();
                xmlFormat.setEncoding("UTF-8");
                // 设置换行
                xmlFormat.setNewlines(true);
                // 生成缩进
                xmlFormat.setIndent(true);
                // 使用4个空格进行缩进, 可以兼容文本编辑器
//                xmlFormat.setIndent("    ");
                XMLWriter xmlWriter = new XMLWriter(stringWriter,xmlFormat);
                xmlWriter.write(document);
                xmlWriter.close();
//                System.out.println(stringWriter.toString());
                updatePartAuthorizeInfo(wdid,roleId,resourceId,stringWriter.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public void deleteAuthorizeListByWdIdAndRole(String wdid,String roleId){
        if (StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(roleId)){
            String jpql = "delete from PfInstanceAuthorize t where t.workflowDefinitionId=?0 and t.roleId=?1";
            baseDao.executeJpql(jpql,wdid,roleId);
        }
    }

    @Transactional
    public void deleteAuthorizeListByRole(String roleId){
        if (StringUtils.isNotBlank(roleId)){
            String jpql = "delete from PfInstanceAuthorize t where t.roleId=?0 ";
            baseDao.executeJpql(jpql,roleId);
        }
    }

    @Transactional
    public void deleteAuthorizeListByWdIdAndResource(String wdid,String resourceId){
        if (StringUtils.isNotBlank(wdid) && StringUtils.isNotBlank(resourceId)){
            String[] ids = StringUtils.split(resourceId,",");
            List<String> idList = Arrays.asList(ids);
            String jpql = "delete from PfInstanceAuthorize t where t.workflowDefinitionId=?0 and t.resourceId in ?1";
            baseDao.executeJpql(jpql,wdid,idList);
        }
    }

    @Transactional
    public void deleteAuthorizeListByResource(String resourceId){
        if (StringUtils.isNotBlank(resourceId)){
            String jpql = "delete from PfInstanceAuthorize t where t.resourceId=?0 ";
            baseDao.executeJpql(jpql,resourceId);
        }
    }

    private Ztree toZtreeByRole(PfRole role) {
        Ztree tree = new Ztree();
        tree.setId(role.getRoleId());
        tree.setName(role.getRoleName());
        tree.setPid("treeroot");
        tree.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url")+"/static/images/sup.png"));
        return tree;
    }

    private Ztree toZtreeByResource(PfResource obj) {
        Ztree tree = new Ztree();
        tree.setId(obj.getResourceId());
        tree.setName(obj.getResourceName());
        tree.setPid("treeroot");
        return tree;
    }

    private Ztree toZtreeByInstanceAuthorize(PfInstanceAuthorize obj) {
        Ztree tree = new Ztree();
        tree.setId(obj.getWauthorizeId());
        tree.setName(obj.getResourceName());
        tree.setKz1(obj.getResourceId());
        tree.setPid("treeroot");
        return tree;
    }
}
