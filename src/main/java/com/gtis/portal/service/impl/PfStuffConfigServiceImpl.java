package com.gtis.portal.service.impl;

import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfBusinessService;
import com.gtis.portal.service.PfResourceGroupService;
import com.gtis.portal.service.PfResourceService;
import com.gtis.portal.service.PfStuffConfigService;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PfStuffConfigServiceImpl extends BaseServiceImpl<PfStuffConfig, String> implements PfStuffConfigService {

    public List<PfStuffConfig> getListByWfdId(String wfdId){
        if (StringUtils.isNotBlank(wfdId)){
            QPfStuffConfig qPfStuffConfig = QPfStuffConfig.pfStuffConfig;
            JPQLQuery query = new JPAQuery(em);
            return query.from(qPfStuffConfig).where(qPfStuffConfig.workflowDefinitionId.eq(wfdId)).orderBy(qPfStuffConfig.stuffXh.asc()).list(qPfStuffConfig);
        }
        return null;
    }

    /**
     * 附件配置改为树结构展示，用于支持多级附件管理
     * @param wfdId
     * @return
     */
    public Ztree getZtreeByWfdId(String wfdId){
        List<PfStuffConfig> stuffList = getListByWfdId(wfdId);
        if (stuffList == null && stuffList.size() < 1){
            stuffList = new ArrayList<PfStuffConfig>();
        }
        return initZtreeByStuffList(stuffList);
    }

    /**
     * 将附件列表转为树结构，增加根目录用于树结构的完整性
     * @param stuffList
     * @return
     */
    public Ztree initZtreeByStuffList(List<PfStuffConfig> stuffList){
        Ztree ztree = null;
        if (stuffList != null && stuffList.size() > 0){
            HashMap<String, Ztree> menuMap = new LinkedHashMap<String, Ztree>();
            for (PfStuffConfig stuffConfig : stuffList) {
                Ztree tree = toZtree(stuffConfig);
                menuMap.put(tree.getId(), tree);
            }
            Ztree root = new Ztree();
            root.setId("stuffroot");
            root.setName("根目录");
            menuMap.put(root.getId(), root);
            for (Ztree tree : menuMap.values()) {
                if (tree.getPid() != null) {
                    Ztree ztreeParent = menuMap.get(tree.getPid());
                    if (ztreeParent != null) {
                        ztreeParent.addChild(tree);
                    }
                } else {
                    tree.setOpen(true);
                    ztree = tree;
                }
            }
        }
        return ztree;
    }

    private Ztree toZtree(PfStuffConfig stuffConfig) {
        Ztree tree = new Ztree();
        tree.setId(stuffConfig.getStuffId());
        tree.setName(stuffConfig.getStuffName());
        if (StringUtils.isBlank(StringUtils.trimToNull(stuffConfig.getProId()))){
            stuffConfig.setProId("stuffroot");
        }
        tree.setPid(StringUtils.trimToNull(stuffConfig.getProId()));
        return tree;
    }

    /**
     * 根据附件定义id，获取该附件下所有的子附件，包含子附件下的子附件
     * 包含本身
     * @param stuffId
     * @return
     */
    public List<PfStuffConfig> getAllChildStuffListById(String stuffId) {
        List<PfStuffConfig> stuffList=new ArrayList<PfStuffConfig>();
        String sql ="select m.* from pf_stuff_config m start with m.stuff_id='" +stuffId
                + "'connect by m.pro_id = prior m.stuff_id "
                + " order by m.stuff_xh";
        List<HashMap>  mapList= baseDao.getMapBySql(sql);
        for (HashMap tmp :mapList ) {
            stuffList.add(toStuffByMap(tmp));
        }
        return stuffList;
    }

    /**
     * 根据附件id，删除本身和所有子记录
     * @param stuffId
     */
    @Transactional
    public void deleteStuffAndChildById(String stuffId){
        if (StringUtils.isNotBlank(stuffId)){
            List<PfStuffConfig> stuffList = getAllChildStuffListById(stuffId);
            if (stuffList != null && stuffList.size() > 0){
                List<String> idList = new ArrayList<String>();
                for (int i = 0; i < stuffList.size(); i++) {
                    idList.add(stuffList.get(i).getStuffId());
                }
                if (idList.size() > 0){
                    String jpql = "delete from PfStuffConfig t where t.stuffId in ?0";
                    baseDao.executeJpql(jpql,idList);
                }
            }
        }
    }

    private PfStuffConfig toStuffByMap(HashMap map){
        PfStuffConfig stuff = new PfStuffConfig();
        stuff.setStuffId(MapUtils.getString(map,"STUFF_ID"));
        stuff.setProId(MapUtils.getString(map, "PRO_ID"));
        stuff.setStuffXh(MapUtils.getInteger(map, "STUFF_XH"));
        stuff.setStuffName(MapUtils.getString(map, "STUFF_NAME"));
        stuff.setMeterial(MapUtils.getString(map, "METERIAL"));
        stuff.setStuffCount(MapUtils.getInteger(map, "STUFF_COUNT"));
        stuff.setRemark(MapUtils.getString(map, "REMARK"));
        stuff.setWorkflowDefinitionId(MapUtils.getString(map, "WORKFLOW_DEFINITION_ID"));
        stuff.setYsnum(MapUtils.getInteger(map, "YSNUM"));
        stuff.setDbnum(MapUtils.getInteger(map, "DBNUM"));
        return stuff;
    }
}
