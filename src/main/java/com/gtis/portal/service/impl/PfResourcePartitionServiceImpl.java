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
public class PfResourcePartitionServiceImpl extends BaseServiceImpl<PfResourcePartition, String> implements PfResourcePartitionService {
    @Autowired
    PfPartitionInfoService partitionInfoService;
    @Autowired
    PfAuthorizeService authorizeService;

    @Transactional
    public void deleteRPAndPIByIds(String ids){
        if (StringUtils.isNotBlank(ids)){
            String[] id = ids.toString().split(",");
            for (int i = 0; i < id.length; i++) {
                //删除元素表
                partitionInfoService.deleteByPid(id[i]);
                //删除菜单权限配置记录
                authorizeService.deleteAuthorizeListByPartitionId(id[i]);
                deleteById(id[i]);
            }
        }
    }

    public List<PfResourcePartition> getListByRid(String rId){
        if (StringUtils.isNotBlank(rId)){
            QPfResourcePartition qPfResourcePartition = QPfResourcePartition.pfResourcePartition;
            JPQLQuery query = new JPAQuery(em);
            return query.from(qPfResourcePartition).where(qPfResourcePartition.resourceId.eq(rId)).orderBy(qPfResourcePartition.partitionName.asc()).list(qPfResourcePartition);
        }
        return null;
    }
    public List<PfResourcePartition> getListByRid(String rId,Integer type){
        if (StringUtils.isNotBlank(rId) && type != null){
            QPfResourcePartition qPfResourcePartition = QPfResourcePartition.pfResourcePartition;
            JPQLQuery query = new JPAQuery(em);
            return query.from(qPfResourcePartition).where(qPfResourcePartition.resourceId.eq(rId).and(qPfResourcePartition.partitionType.eq(type))).orderBy(qPfResourcePartition.partitionName.asc()).list(qPfResourcePartition);
        }
        return null;
    }
}
