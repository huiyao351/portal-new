package com.gtis.portal.service.impl;


import com.gtis.config.AppConfig;
import com.gtis.portal.entity.PfOrgan;
import com.gtis.portal.entity.PfUser;

import com.gtis.portal.entity.QPfOrgan;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfOrganService;

import com.gtis.portal.service.PfUserOrganService;
import com.gtis.portal.service.PfUserService;
import com.gtis.portal.util.RequestUtils;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class PfOrganServiceImpl extends BaseServiceImpl<PfOrgan, String> implements PfOrganService {
    @Autowired
    PfUserService userService;
    @Autowired
    private PfUserOrganService userOrganService;

    @Transactional
    public void deleteById(String organId){
        if (StringUtils.isNotBlank(organId)){
            String jpql = "delete from PfUserOrganRel t where t.organId=?0";
            baseDao.executeJpql(jpql,organId);
            super.deleteById(organId);
        }
    }

    @Transactional
    public void deleteOrganAndUserById(String organId){
        List<String> organIdList=findAllOrgan(organId);
        for (String  tmpId:organIdList) {
            //通过部门ID找到相关用户
            List<PfUser> organ_userList = getUserListByid(tmpId);
            for (PfUser user: organ_userList) {
                //删除用户
                userService.deleteById(user.getUserId());
            }
            //删除部门
            deleteById(tmpId);
        }
    }


    public List<PfOrgan> getOrganList(String regionCode){
        List<PfOrgan> organList = new ArrayList<PfOrgan>();
        QPfOrgan qPforgan = QPfOrgan.pfOrgan;
        JPQLQuery query = new JPAQuery(em);
        if (StringUtils.isNotBlank(regionCode)){
            String xzqdm = regionCode;
            if (StringUtils.length(regionCode)==6){
                //行政区代码六位，去除结尾的0000或者是00，如果是0000代表省级，如果是00，代表市级，如果没有则是区县级
                xzqdm = StringUtils.removeEnd(regionCode,"0000");
                xzqdm = StringUtils.removeEnd(regionCode,"00");
            }
            organList = query.from(qPforgan).where(qPforgan.regionCode.like(xzqdm+"%")).orderBy(qPforgan.regionCode.asc()).orderBy(qPforgan.organNo.asc()).list(qPforgan);
        }else {
            organList = query.from(qPforgan).orderBy(qPforgan.regionCode.asc()).orderBy(qPforgan.organNo.asc()).list(qPforgan);
        }
        return organList;
    }

    public Ztree getAllOrganTree(String regionCode) {
        List<PfOrgan> organList = getOrganList(regionCode);
        return getOrganTreeByOrganList(organList);
    }
    public Ztree getOrganTreeByUserId(String userId) {
        List<PfOrgan> organList = userOrganService.getOrganListByUserId(userId);
        return getOrganTreeByOrganList(organList);
    }

    public Ztree getOrganTreeByOrganList(List<PfOrgan> organList) {
        if (organList != null && organList.size() > 0){
            LinkedHashMap<String, Ztree> organMap = new LinkedHashMap<String, Ztree>();
            Ztree root = null;
            Ztree firstNode = new Ztree();
            firstNode.setId("treeroot");
            firstNode.setName("部门用户");
            firstNode.setNocheck(true);
            firstNode.setKz1("");
            organMap.put(firstNode.getId(),firstNode);

            for (PfOrgan organVo : organList) {
                Ztree tree = toZtree(organVo);
                organMap.put(tree.getId(), tree);

            }
            for (Ztree tree : organMap.values()) {
                if (tree.getPid() != null) {
                    Ztree ztree = organMap.get(tree.getPid());
                    ztree = organMap.get(tree.getPid());
                    if (ztree != null) {
                        ztree.addChild(tree);
                    }else {
                        tree.setPid(firstNode.getId());
                        root.addChild(tree);
                    }
                } else {
                    tree.setOpen(true);
                    root = tree;
                }
            }

            return root;
        }
        return null;
    }
    private  Ztree toZtree(PfOrgan organVo) {
        Ztree tree = new Ztree();
        tree.setId(organVo.getOrganId());
        tree.setName(organVo.getOrganName());
        if(organVo.getSuperOrganId()==null){
            tree.setPid("treeroot");
        }else{
            tree.setPid(StringUtils.trimToNull(organVo.getSuperOrganId()));
        }
        tree.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/folder.gif"));
        tree.setKz1(organVo.getRegionCode());
        return tree;
    }

    private  Ztree toUserZtree(PfUser user) {
        Ztree tree = new Ztree();
        tree.setId(user.getUserId());
        tree.setName(user.getUserName());
        tree.setPid(user.getOrganId());
        tree.setKz1("userId");
        return tree;
    }


    public Ztree getorganUserTree(String organId,String regionCode) {
        Ztree ztree = getAllOrganTree(regionCode);
        //获取当前主题的菜单列表
        HashMap userMap = new HashMap();
        List<PfUser> userList = getUserListByid(organId);
        if (userList != null && userList.size() > 0){
            for (int i = 0; i < userList.size(); i++) {
                userMap.put(userList.get(i).getOrganId(),true);
            }
        }
        return initCheckZtree(userMap, ztree);
    }


    private Ztree initCheckZtree(HashMap userMap,Ztree ztree){
        if (userMap.containsKey(ztree.getId())){
            ztree.setChecked(true);
        }
        checkZtree(userMap,ztree.getChildren());
        return ztree;
    }
    private void checkZtree(HashMap userMap,List<Ztree> list){
        if (list != null && list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                if (userMap.containsKey(list.get(i).getId())){
                    userMap.remove(list.get(i).getId());
                    list.get(i).setChecked(true);
                    checkZtree(userMap, list.get(i).getChildren());
                }
            }
        }
    }

    @Override
    public List<PfUser> getUserListByid(String oragen_id) {
        List<PfUser > userList=new ArrayList<PfUser>();
        List<HashMap> userMapList = new ArrayList<HashMap>();
        String sql ="select t.user_id,t.user_name,tr.ORGAN_ID from pf_user t,pf_user_organ_rel tr" +
                " where t.user_id=tr.user_id  " +
                " and tr.organ_id='"+oragen_id+"'" +
                " order by t.user_no";
        userMapList = baseDao.getMapBySql(sql);
        for (HashMap  hashMap:userMapList) {
            String userid=(String)hashMap.get("USER_ID");
            String user_name=(String)hashMap.get("USER_NAME");
            String organ_id=(String)hashMap.get("ORGAN_ID");
            PfUser user=new PfUser();
            user.setUserId(userid);
            user.setUserName(user_name);
            user.setOrganId(organ_id);
            userList.add(user);
        }
        return userList;
    }

    @Override
    public List<String> findAllOrgan(String organId) {
        List<String> organIdList=new ArrayList<String>();
        String sql ="select  ORGAN_ID from pf_organ m start with m.organ_id='" +organId
                    + "'connect by  m.super_organ_id= prior m.organ_id ";
        List<HashMap>  organidMapList= baseDao.getMapBySql(sql);
        for (HashMap tmp :organidMapList ) {
            String userid=(String)tmp.get("ORGAN_ID");
            organIdList.add(userid);
        }
        return organIdList;
    }

    @Override
    public Ztree getOtherTree(int mark) {
        Ztree otherNode = new Ztree();
        otherNode.setId("treeother");
        otherNode.setName("其他部门");
        otherNode.setNocheck(true);
        otherNode.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/folder.gif"));
        //mark标志位1表示不放入用户2表示放入用户
        if(mark==1){
            return otherNode;
        }else if(mark==2){
        List<Ztree> children = new ArrayList<Ztree>();
        List<PfUser>   userList= findOtherUser();
        for (PfUser user:userList) {
            Ztree treetmp =   toUserZtree(user);
            children.add(treetmp);
        }
        otherNode.setChildren(children);
        }
        return otherNode;
    }

    @Override
    public List<PfUser> findOtherUser() {
        List<PfUser> userList=new ArrayList<PfUser>();
        String sql ="select A.user_id,A.user_name from  pf_user A " +
                " where  NOT EXISTS ( SELECT B.USER_ID FROM  pf_user_organ_rel B  " +
                "where A.user_id=B.user_id" +
                ")";
        List<HashMap>  otherMapList= baseDao.getMapBySql(sql);
        for (HashMap tmp :otherMapList ) {
            String userid = (String) tmp.get("USER_ID");
            String user_name = (String) tmp.get("USER_NAME");
            PfUser user = new PfUser();
            user.setUserId(userid);
            user.setUserName(user_name);
            userList.add(user);
        }
        return userList;
    }

    private List<PfUser> findUser() {
        List<PfUser> userList=new ArrayList<PfUser>();
        String sql =" select B.ORGAN_ID, A.USER_ID, A.USER_NAME from pf_user A, pf_user_organ_rel B where  A.USER_ID=B.USER_ID ";
        List<HashMap>  otherMapList= baseDao.getMapBySql(sql);
        for (HashMap tmp :otherMapList ) {
            String userid = (String) tmp.get("USER_ID");
            String user_name = (String) tmp.get("USER_NAME");
            String organ_id=(String)tmp.get("ORGAN_ID");
            PfUser user = new PfUser();
            user.setUserId(userid);
            user.setUserName(user_name);
            user.setOrganId(organ_id);
            userList.add(user);
        }
        return userList;
    }


    @Override
    public Ztree getAllOrganUserTree(String regionCode) {
        List<PfOrgan> organList = getOrganList(regionCode);
        HashMap<String, Ztree> organMap = new LinkedHashMap<String, Ztree>();
        Ztree root = null;
        Ztree firstNode = new Ztree();
        firstNode.setId("treeroot");
        firstNode.setName("部门用户");
        firstNode.setNocheck(true);
        firstNode.setKz1("");
        for (PfOrgan organVo : organList) {
            Ztree tree = toZtree(organVo);
            tree.setGroup(true);
            organMap.put(tree.getId(), tree);
        }
        //查询和部门关联的用户
        List<PfUser> userList =  findUser();
        if(userList!=null) {
            for (PfUser user : userList) {
                Ztree treetmp = toUserZtree(user);
                //用部门id+userid作为map的key
                organMap.put(user.getOrganId()+treetmp.getId(), treetmp);
            }
        }

        organMap.put(firstNode.getId(),firstNode);
        for (Ztree tree : organMap.values()) {
            if (tree.getPid() != null) {
                Ztree ztree = organMap.get(tree.getPid());
                if (ztree != null) {
                    ztree.addChild(tree);
                }
            } else {
                tree.setOpen(true);
                root = tree;
            }
        }

        return root;
    }

    /**
     * 检查当前行政区代码是否匹配与父行政区代码
     * @param regionCode
     * @param superRegionCode
     * @return
     */
    public boolean checkValidRegionCode(String regionCode,String superRegionCode){
        boolean isok = false;
        if (StringUtils.isNotBlank(regionCode)){
            if (StringUtils.equalsIgnoreCase("treeroot",superRegionCode) || StringUtils.isBlank(superRegionCode)){
                isok = true;
            }else {
                //判断该部门所填写的行政区代码是否和匹配父部门，避免A单位填写B单位部门
                PfOrgan superOrgan = findById(superRegionCode);
                if (superOrgan != null && StringUtils.isNotBlank(superOrgan.getOrganId())){
                    String superXzqdm = superOrgan.getRegionCode();
                    if (StringUtils.isNotBlank(superXzqdm)){
                        if (StringUtils.length(superXzqdm)==2 || StringUtils.endsWith(superXzqdm,"0000")){
                            //如果父部门是省级，则该子部门必须是市级或者省管县（也就是行政区代码为六位或者是不包含两个0的四位）
                            String tmpXzqdm = StringUtils.removeEnd(superXzqdm,"0000");
                            if (StringUtils.startsWith(regionCode,tmpXzqdm)){
                                isok = true;
                            }
                        }else if (StringUtils.length(superXzqdm)==4 || StringUtils.endsWith(superXzqdm,"00")){
                            //如果父部门是市级，则该子部门必须是市级或者省管县（也就是行政区代码为六位或者是不包含两个0的四位）
                            String tmpXzqdm = StringUtils.removeEnd(superXzqdm,"00");
                            if (StringUtils.startsWith(regionCode,tmpXzqdm)){
                                isok = true;
                            }
                        }else{
                            //如果父部门是市级，则该子部门必须是市级或者省管县（也就是行政区代码为六位或者是不包含两个0的四位）
                            String tmpXzqdm = superXzqdm;
                            if (StringUtils.startsWith(regionCode,tmpXzqdm)){
                                isok = true;
                            }
                        }
                    }
                }
            }
        }
        return isok;
    }
}
