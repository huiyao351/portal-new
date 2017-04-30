package com.gtis.portal.service;


import com.gtis.portal.entity.PfOrgan;
import com.gtis.portal.entity.PfUser;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;

import java.util.List;


public interface PfOrganService extends BaseService<PfOrgan, String> {

    public void deleteById(String organId);

    public void deleteOrganAndUserById(String organId);

    public List<PfOrgan> getOrganList(String regionCode);

    Ztree getAllOrganTree(String regionCode);

    public Ztree getOrganTreeByUserId(String userId);

    public Ztree getOrganTreeByOrganList(List<PfOrgan> organList);

    List<PfUser> getUserListByid(String organId);

    List<String> findAllOrgan(String organId);

    Ztree getOtherTree(int mark);

    List<PfUser> findOtherUser();

    Ztree getAllOrganUserTree(String regionCode);

    public boolean checkValidRegionCode(String regionCode,String superRegionCode);
}
