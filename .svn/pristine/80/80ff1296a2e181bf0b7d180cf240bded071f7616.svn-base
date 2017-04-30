package com.gtis.portal.service;


import com.gtis.portal.entity.PfOrgan;
import com.gtis.portal.entity.PfUserOrganRel;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;

import java.util.List;


public interface PfUserOrganService extends BaseService<PfUserOrganRel, String> {

    void deleteByOrganId(String organId);

    List<Ztree> findOrganbyUserId(String userId);

    void deleteUserOrganRelByUserIdAndOrganId(String user_id, String organ_id);

    boolean findUserOrgan(PfUserOrganRel userOrganRel);

    public List<PfOrgan> getOrganListByUserId(String userId);

    /**
     * 根据用户id，保存所选择的部门列表信息
     * @param userId
     * @param changeList
     */
    public void addOrganRelByUserId(String userId,List<ZtreeChanged> changeList);

    /**
     * 根据部门id，保存所选择的用户列表信息
     * @param organId
     * @param changeList
     */
    public void addUserRelByOrganId(String organId,List<ZtreeChanged> changeList);
}
