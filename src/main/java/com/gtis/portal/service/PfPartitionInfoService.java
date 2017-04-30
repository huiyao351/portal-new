package com.gtis.portal.service;


import com.gtis.portal.entity.PfPartitionInfo;
import com.gtis.portal.entity.PfResourcePartition;

import java.util.List;

public interface PfPartitionInfoService extends BaseService<PfPartitionInfo, String> {
    public void deleteByPid(String pId);
    public List<PfPartitionInfo> getListByPid(String pId);
}
