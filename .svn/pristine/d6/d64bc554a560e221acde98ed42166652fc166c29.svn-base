package com.gtis.portal.service.impl;

import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.*;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PfPartitionInfoServiceImpl extends BaseServiceImpl<PfPartitionInfo, String> implements PfPartitionInfoService {
    @Autowired
    PfBusinessService businessService;

    @Transactional
    public void deleteByPid(String pId){
        if (StringUtils.isNotBlank(pId)){
            String jpql = "delete from PfPartitionInfo t where t.partitionId=?0";
            baseDao.executeJpql(jpql,pId);
        }
    }

    public List<PfPartitionInfo> getListByPid(String pId){
        if (StringUtils.isNotBlank(pId)){
            QPfPartitionInfo qPfPartitionInfo = QPfPartitionInfo.pfPartitionInfo;
            JPQLQuery query = new JPAQuery(em);
            return query.from(qPfPartitionInfo).where(qPfPartitionInfo.partitionId.eq(pId)).orderBy(qPfPartitionInfo.elementId.asc()).list(qPfPartitionInfo);
        }
        return null;
    }
}
