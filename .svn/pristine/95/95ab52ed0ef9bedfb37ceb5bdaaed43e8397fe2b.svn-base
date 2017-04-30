package com.gtis.portal.service;


import com.gtis.plat.vo.UserInfo;
import com.gtis.portal.entity.PfBusiness;
import com.gtis.portal.entity.PfBusinessGroup;
import com.gtis.portal.entity.PublicVo;
import com.gtis.portal.entity.QPfBusinessGroup;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.impl.BaseServiceImpl;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

import java.util.List;

public interface PfBusinessGroupService extends BaseService<PfBusinessGroup, String> {
    public List<PfBusinessGroup> getGroupListByName(String name);

    public List<PfBusinessGroup> getBusinessGroupList();

    public List<Ztree> getBusinessGroupTree();

    public List<PublicVo> getBusinessRelList(String groupId);

    public List<PfBusinessGroup> getBusinessGroupListByRole(UserInfo userInfo,String rid);

    public void updateDetail(PfBusinessGroup businessGroup);

    public void updateBusinessIds(String groupId,List<ZtreeChanged> changeList);
}
