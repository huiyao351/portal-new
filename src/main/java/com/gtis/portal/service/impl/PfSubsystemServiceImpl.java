package com.gtis.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.config.AppConfig;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.*;
import com.gtis.portal.util.Constants;
import com.gtis.portal.util.RequestUtils;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PfSubsystemServiceImpl extends BaseServiceImpl<PfSubsystem, String> implements PfSubsystemService {
    @Autowired
    PfBusinessService businessService;
    @Autowired
    PfResourceService resourceService;
    @Autowired
    PfMenuService menuService;
    @Autowired
    PfAuthorizeService authorizeService;

    @Override
    public PfSubsystem findInitUrlById(String systemId){
        PfSubsystem sub = super.findById(systemId);
        if(sub != null && StringUtils.isNotBlank(sub.getSubUrl())){
            sub.setSubUrl(RequestUtils.initOptProperties(sub.getSubUrl()));
        }
        return sub;
    }

    public List<Ztree> getSubsystemTree(){
        List<PfSubsystem> subList = getAllSubsystemList(false);
//        List<PfSubsystem> subList = baseDao.getAll(PfSubsystem.class);
        List<Ztree> treeList = new ArrayList<Ztree>();
        if (subList != null && subList.size() > 0){
            for (int i = 0; i < subList.size(); i++) {
                Ztree tree = toZtreeBySubsystem(subList.get(i));
                treeList.add(tree);
            }
        }
        return treeList;
    }

    public PfSubsystem getSubsystemByName(String name){
        if (StringUtils.isNotBlank(name)){
            QPfSubsystem qPfSubsystem = QPfSubsystem.pfSubsystem;
            JPQLQuery query = new JPAQuery(em);
            List<PfSubsystem> list = query.from(qPfSubsystem).where(qPfSubsystem.subsystemName.eq(name)).orderBy(qPfSubsystem.subNo.asc()).list(qPfSubsystem);
            if (list != null && list.size() > 0){
                if(StringUtils.isNotBlank(list.get(0).getSubUrl())){
                    list.get(0).setSubUrl(RequestUtils.initOptProperties(list.get(0).getSubUrl()));
                }
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 根据主题id，删除相关记录，包括菜单关联、权限配置以及主表
     * @param subId
     */
    @Transactional
    public void deleteSubAndRelById(String subId){
        if(StringUtils.isNotBlank(subId)){
            //删除主题、菜单关联记录
            String jpql = "delete from PfSubsystemMenuRel t where t.subsystemId=?0";
            baseDao.executeJpql(jpql,subId);
            //删除主题、角色关联记录
            authorizeService.delAuthByObjIdAndType(subId,Constants.AuthorizeObjType.SUB.getBm());
            //删除主表
            deleteById(subId);
        }
    }

    public List<PfSubsystem> getAllSubsystemList(boolean enable){
        QPfSubsystem qPfSubsystem = QPfSubsystem.pfSubsystem;
        JPQLQuery query = new JPAQuery(em);
        if (enable){
            return query.from(qPfSubsystem).where(qPfSubsystem.enabled.eq(true)).orderBy(qPfSubsystem.subNo.asc()).list(qPfSubsystem);
        }
        return query.from(qPfSubsystem).orderBy(qPfSubsystem.subNo.asc()).list(qPfSubsystem);
    }

    /**
     * 获取当前用户权限下的主题菜单
     * 1、根据主题、权限表、用户角色关联表来获取主题列表
     * @param userId
     * @return
     */
    public List<PfSubsystem> getSubsystemAuthorList(String userId){
        if (!StringUtils.equals(userId,"0")){
            List<PfSubsystem> subList = new ArrayList<PfSubsystem>();
            String sql = "select distinct t.* from pf_subsystem t,pf_authorize t1,pf_user_role_rel t2 " +
                    " where t.subsystem_id=t1.authorize_obj_id and t1.authorize_obj_type=8 and t1.undertaker_id=t2.role_id " +
                    " and t.enabled=1 and t2.user_id='"+userId+"' " +
                    " order by t.sub_no ";
            List<HashMap> subMapList = baseDao.getMapBySql(sql);
            if (subMapList != null){
                for (int i = 0; i < subMapList.size(); i++) {
                    subList.add(toSubsystemByMap(subMapList.get(i)));
                }
            }
            return subList;
        }
        return getAllSubsystemList(true);
    }
    private PfSubsystem toSubsystemByMap(HashMap map){
        if (map != null && StringUtils.isNotBlank(MapUtils.getString(map,"SUBSYSTEM_ID"))){
            PfSubsystem sub = new PfSubsystem();
            sub.setSubsystemId(MapUtils.getString(map,"SUBSYSTEM_ID"));
            sub.setSubsystemName(MapUtils.getString(map, "SUBSYSTEM_NAME"));
            sub.setSubsystemTitle(MapUtils.getString(map, "SUBSYSTEM_TITLE"));
            sub.setEnabled(true);
            sub.setSubType(MapUtils.getInteger(map, "SUB_TYPE"));
            sub.setSubNo(MapUtils.getIntValue(map, "SUB_NO"));
            sub.setSubUrl(MapUtils.getString(map, "SUB_URL"));
            sub.setSubMenuType(MapUtils.getString(map, "SUB_MENU_TYPE"));
            return sub;
        }
        return null;
    }

    public Integer getMaxSubsystemId(){
        String sql = "select max(to_number(nvl(SUBSYSTEM_ID,0))) as SUBSYSTEM_ID from Pf_Subsystem";
        List<HashMap> mapList = baseDao.getMapBySql(sql);
        if (mapList != null && mapList.size() > 0){
            Integer max = MapUtils.getInteger(mapList.get(0),"SUBSYSTEM_ID");
            if(max == null){
                max = 0;
            }
            max++;
            return max;
        }else {
            return 1;
        }
    }



    //*************************菜单关联表操作*******************************

    public PfSubsystemMenuRel getSubMenuRel(String menuId,String subId){
        List<PfSubsystemMenuRel> relList = getSubMenuRelList(menuId,subId);
        if (relList != null && relList.size() > 0){
            return relList.get(0);
        }
        return null;
    }

    public List<PfSubsystemMenuRel> getSubMenuRelList(String menuId,String subId){
        QPfSubsystemMenuRel qPfSubsystemMenuRel = QPfSubsystemMenuRel.pfSubsystemMenuRel;
        JPQLQuery query = new JPAQuery(em);
        List<PfSubsystemMenuRel> relList = null;
        if (StringUtils.isNotBlank(menuId) && StringUtils.isNotBlank(subId)){
            relList = query.from(qPfSubsystemMenuRel).where(qPfSubsystemMenuRel.menuId.eq(menuId).and(qPfSubsystemMenuRel.subsystemId.eq(subId))).list(qPfSubsystemMenuRel);
        }else if (StringUtils.isNotBlank(menuId) && StringUtils.isBlank(subId)){
            relList = query.from(qPfSubsystemMenuRel).where(qPfSubsystemMenuRel.menuId.eq(menuId)).list(qPfSubsystemMenuRel);
        }else if (StringUtils.isBlank(menuId) && StringUtils.isNotBlank(subId)) {
            relList = query.from(qPfSubsystemMenuRel).where(qPfSubsystemMenuRel.subsystemId.eq(subId)).list(qPfSubsystemMenuRel);
        }
        return relList;
    }

    @Transactional
    public void deleteSubMenuRel(String menuId,String subId){
        if (StringUtils.isNotBlank(menuId) && StringUtils.isNotBlank(subId)){
            // 如果是父节点，则获取该菜单下，所有关联的子菜单，逐一删掉关联关系，需要遍历层级关系
            List<PfMenu> menuList = menuService.getAllChildMenuListByMenuId(menuId);
            if (menuList != null && menuList.size() > 0){
                for (int i = menuList.size()-1; i >= 0; i--) {
                    String jpql = "delete from PfSubsystemMenuRel t where t.menuId=?0 and t.subsystemId=?1";
                    baseDao.executeJpql(jpql,menuList.get(i).getMenuId(),subId);
                }
            }
        }
    }

    /**
     * 插入关联关系
     * 需要判断是否已经存在该关系
     * @param menuId
     * @param subId
     */
    @Transactional
    public void insertMenuRel(String menuId,String subId){
        //查询是否存在该关联关系
        List<PfSubsystemMenuRel> relList = getSubMenuRelList(menuId,subId);
        if (relList == null || relList.size() < 1){
            PfSubsystemMenuRel rel = new PfSubsystemMenuRel();
            rel.setMenuId(menuId);
            rel.setSubsystemId(subId);
            baseDao.save(rel);

        }
    }


    /**
     * 根据选择状态，更新主题菜单关联关系
     * @param changeList
     * @param subId
     */
    @Transactional
    public void updateSubMenuRel(List<ZtreeChanged> changeList,String subId){
        if (changeList != null && StringUtils.isNotBlank(subId)){
            for (int i = 0; i < changeList.size(); i++) {
                ZtreeChanged changed = changeList.get(i);
                if (changed.isChecked()){
                    //保存新的关系
                    insertMenuRel(changed.getId(), subId);
                }else {
                    //删除原有关系
                    deleteSubMenuRel(changed.getId(), subId);
                }
            }
        }
    }

//    /**
//     * 删除主题与菜单的关联关系，需要考虑是否是父节点菜单
//     * @param subId
//     * @param menuId
//     * @param isleaf
//     */
//    @Transactional
//    public void deleteSubMenuRelByMenuId(String subId,String menuId,boolean isleaf){
//        subId = "4";
//        menuId = "1";
//        if (StringUtils.isNotBlank(subId) && StringUtils.isNotBlank(menuId)){
//            //如果是子节点则直接删除关联关系，
//            if (isleaf){
//                deleteSubMenuRel(menuId,subId);
//            }else {
//                // 如果是父节点，则获取该菜单下，所有关联的子菜单，逐一删掉关联关系，需要遍历层级关系
//                List<PfMenu> menuList = menuService.getSubMenuListBySubIdAndMenuId(menuId);
////                System.out.println(JSON.toJSONString(menuList));
////                System.out.println(JSON.toJSONString(menuService.initZtreeByMenuList(menuList)));
//            }
//        }
//    }

    /**
     * 刷新sub菜单，传递过来的menuids来决定是删除原有关系还是增加新的关系
     * 该方法不用了，比较笨重，直接采用前台树控件提供的方法，获取改变选择状态的数据处理即可
     * @param menuIds
     * @param subId
     */
    @Transactional
    public void refreshMenuRel(String menuIds,String subId){
        if (StringUtils.isNotBlank(menuIds) && StringUtils.isNotBlank(subId)){
//            List<String> idList = Arrays.asList(StringUtils.split(menuIds, ","));
            HashMap<String,String> idMap = new HashMap<String, String>();
            String[] idAry = StringUtils.split(menuIds,",");
            for (int i=0;i<idAry.length;i++){
                if (StringUtils.isNotBlank(idAry[i]))
                    idMap.put(idAry[i],idAry[i]);
            }
            //查询是否存在该关联关系
            QPfSubsystemMenuRel qPfSubsystemMenuRel = QPfSubsystemMenuRel.pfSubsystemMenuRel;
            JPQLQuery query = new JPAQuery(em);
            List<PfSubsystemMenuRel> relList = query.from(qPfSubsystemMenuRel).where(qPfSubsystemMenuRel.subsystemId.eq(subId)).list(qPfSubsystemMenuRel);
            if (relList != null && relList.size() > 1){
                for (int i=0;i<relList.size();i++){
                    String menuId = relList.get(i).getMenuId();
                    if (!idMap.containsKey(menuId)){
                        baseDao.delete(relList.get(i));
                        relList.remove(i);
                        i--;
                        break;
                    }else {
                        idMap.remove(menuId);
                    }
                }
                for(Map.Entry<String,String> entry:idMap.entrySet()){
                    String key = entry.getKey();
                    PfSubsystemMenuRel rel = new PfSubsystemMenuRel();
                    rel.setMenuId(key);
                    rel.setSubsystemId(subId);
                    baseDao.save(rel);
                }
            }
        }
    }

    private Ztree toZtreeBySubsystem(PfSubsystem sub) {
        Ztree tree = new Ztree();
        tree.setId(sub.getSubsystemId());
        tree.setName(sub.getSubsystemTitle());
        tree.setNocheck(true);
        tree.setKz1(sub.getSubsystemName());
        tree.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/40x40icon05.png"));
        return tree;
    }

    @Transactional
    public void updateSubRoleRel(String subId,List<ZtreeChanged> changeList){
        if (changeList != null && changeList.size() > 0 && StringUtils.isNotBlank(subId)){
            //根据menuid查找器上级菜单，一直查找到根路径菜单，对这些父级菜单逐一赋值
            PfSubsystem subsystem = findById(subId);
            if (subsystem != null){
                for (int i = 0; i < changeList.size(); i++) {
                    ZtreeChanged change = changeList.get(i);
                    Integer visible = change.isChecked()?1:0;
                    //查找该菜单和角色下是否有权限记录，如果没有则增加权限记录，如果有则修改操作类型
                    //此处的权限类型为1
                    boolean hasRole = authorizeService.checkHasAuth(change.getId(), subsystem.getSubsystemId(),Constants.AuthorizeObjType.SUB.getBm());
                    if (!hasRole){
                        PfAuthorize authorize = new PfAuthorize();
                        authorize.setAuthorizeId(UUIDGenerator.generate18());
                        authorize.setAuthorizeObjType(Constants.AuthorizeObjType.SUB.getBm());
                        authorize.setUndertakerId(change.getId());
                        authorize.setAuthorizeObjId(subsystem.getSubsystemId());
                        authorize.setMenuVisible(visible);
                        authorizeService.insert(authorize);
                    }else {
                        authorizeService.updateRoleMenuVisible(change.getId(), subsystem.getSubsystemId(),visible,Constants.AuthorizeObjType.SUB.getBm());
                    }
                }
            }
        }
    }
}
