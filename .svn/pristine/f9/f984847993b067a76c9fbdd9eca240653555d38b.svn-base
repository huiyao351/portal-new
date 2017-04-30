package com.gtis.portal.service;


import com.gtis.portal.entity.PfUser;
import com.gtis.portal.model.Ztree;

import java.sql.Blob;
import java.util.List;


public interface PfUserService extends BaseService<PfUser, String> {

    public void deleteById(String userId);
    public List<PfUser> getListByRoleId(String roleId);
    public List<Ztree> getTreeByRoleId(String roleId);
    public List<PfUser> getListByOrganId(String organId);
    public List<Ztree> getTreeByOrganId(String organId);

    void updateUserBlob(PfUser user,int mark) throws  Exception;

    void updateUserLogin(PfUser user);

    void updateUserinfo(PfUser user);
}
