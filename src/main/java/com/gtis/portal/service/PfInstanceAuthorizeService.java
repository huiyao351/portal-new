package com.gtis.portal.service;


import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;

import java.util.HashMap;
import java.util.List;

public interface PfInstanceAuthorizeService extends BaseService<PfInstanceAuthorize, String> {
    public List<PfInstanceAuthorize> getAuthorizeListByWdId(String wdid);
    public PfInstanceAuthorize getAuthorizeListByParam(String wdid,String roleId,String resourceId);
    public List<PfRole> getAuthorizeRoleListByWdId(String wdid);
    public List<PfResource> getAuthorizeResourceListByWdId(String wdid,String roleId,boolean ischeck);

    public List<Ztree> getAuthorizeRoleTreeListByWdId(String wdid);

    public List<Ztree> getAuthorizeResourceTreeListByWdId(String wdid,String roleId,boolean ischeck);

    public HashMap<String,PfResourcePartition> getInstancePartMap(String wdid,String roleId,String resourceId);

    public void updateVisible(String wauthorizeId,Integer visible);

    public void addRoleRel(String wdid,List<ZtreeChanged> changeList);

    public void addResourceRel(String wdid,List<ZtreeChanged> changeList);

    public void updateResourceRel(String wdid,String roleId,List<ZtreeChanged> changeList);

    public void updatePartOperType(String wdid,String roleId,String resourceId,List<ZtreeChanged> changeList);

    public void deleteAuthorizeListByWdIdAndRole(String wdid,String roleId);

    public void deleteAuthorizeListByRole(String roleId);

    public void deleteAuthorizeListByResource(String resourceId);

    public void deleteAuthorizeListByWdIdAndResource(String wdid,String resourceId);


}
