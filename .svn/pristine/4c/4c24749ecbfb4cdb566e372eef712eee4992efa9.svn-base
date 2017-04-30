package com.gtis.portal.service.impl;


import com.gtis.portal.entity.PfUser;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


@Service
public class PfUserServiceImpl extends BaseServiceImpl<PfUser, String> implements PfUserService {


    @Transactional
    public void deleteById(String userId){
        if (StringUtils.isNotBlank(userId)){
            String jpql = "delete from PfUserRoleRel t where t.userId=?0";
            baseDao.executeJpql(jpql,userId);
            jpql = "delete from PfUserOrganRel t where t.userId=?0";
            baseDao.executeJpql(jpql,userId);
            super.deleteById(userId);
        }
    }

    public List<PfUser> getListByRoleId(String roleId){
        String jqpl = "select t from PfUser t,PfUserRoleRel t1 where t.userId=t1.userId and t1.roleId=?0";
        return baseDao.getByJpql(jqpl,roleId);
    }

    public List<Ztree> getTreeByRoleId(String roleId){
        List<PfUser> userList = getListByRoleId(roleId);
        return initZtreeUser(userList);
    }

    public List<PfUser> getListByOrganId(String organId){
        String jqpl = "select t from PfUser t,PfUserOrganRel t1 where t.userId=t1.userId and t1.organId=?0";
        return baseDao.getByJpql(jqpl,organId);
    }

    public List<Ztree> getTreeByOrganId(String organId){
        List<PfUser> userList = getListByOrganId(organId);
        return initZtreeUser(userList);
    }

    private List<Ztree> initZtreeUser(List<PfUser> userList){
        List<Ztree> treeList = new ArrayList<Ztree>();
        if (userList != null && userList.size() > 0){
            for (int i = 0; i < userList.size(); i++) {
                treeList.add(toZtreeByUserId(userList.get(i)));
            }
        }
        return treeList;
    }

    private Ztree toZtreeByUserId(PfUser user){
        Ztree tree = new Ztree();
        tree.setId(user.getUserId());
        tree.setName(user.getUserName());
        tree.setPid("treeroot");
        return tree;
    }

    @Override
    @Transactional
    public void updateUserBlob(PfUser user,int mark) throws  Exception{
        long in = 0;
        byte[] b = null;
        if(mark==0){
            String jpql = "update PfUser t set  t.userPhoto=?0 where t.userId=?1";
            baseDao.executeJpql(jpql,user.getUserPhoto(),user.getUserId());
        }else if(mark==1){
            String jpql = "update PfUser t set  t.userSign=?0 where t.userId=?1";
            baseDao.executeJpql(jpql,user.getUserSign(),user.getUserId());
        }
    }

    @Override
    @Transactional
    public void updateUserLogin(PfUser user) {
        String jpql = "update PfUser t set  t.loginName=?0  ,t.loginPassword=?1  where t.userId=?2";
        baseDao.executeJpql(jpql,user.getLoginName(),user.getLoginPassword(),user.getUserId());
    }

    @Override
    @Transactional
    public void updateUserinfo(PfUser user) {
        if (user != null){
            PfUser usertmp = findById(user.getUserId());
            if (usertmp != null){
                user.setLoginPassword(usertmp.getLoginPassword());
                user.setLoginName(usertmp.getLoginName());
                user.setUserPhoto(usertmp.getUserPhoto());
                user.setUserSign(usertmp.getUserSign());
                user.setUserType(usertmp.getUserType());
                baseDao.update(user);
            }
        }

        /*String jpql = "update PfUser t set t.userNo='"+user.getUserNo()+"' ,t.userName='"+user.getUserName()+
                "',t.userRank='"+user.getUserRank()+"',t.userPost='"+user.getUserPost()+
                "',t.email='"+user.getEmail()+"',t.mobilePhone='"+user.getMobilePhone()+
                "',t.officePhone='"+user.getOfficePhone()+"',t.homePhone='"+user.getHomePhone()+
                "',t.birthdate="+user.getBirthdate()+",t.userDegree='"+user.getUserDegree()+
                "',t.userSex='"+user.getUserSex()+"',t.address='"+user.getAddress()+
                "',t.remark='"+user.getRemark()+"',t.isValid='"+user.getIsValid()+"'  where t.userId='"+user.getUserId()+"'";
        System.out.println(jpql);
        baseDao.executeJpql(jpql);*/
    }
}
