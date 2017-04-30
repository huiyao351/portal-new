package com.gtis.portal.service.impl;

import com.gtis.portal.dao.BaseDao;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfBusinessService;
import com.gtis.portal.service.PfResourceService;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PfBusinessServiceImpl implements PfBusinessService {
    @Autowired
    BaseDao baseDao;
    @PersistenceContext
    public EntityManager em;

    public PfBusiness findById(String bsId){
        if (StringUtils.isNotBlank(bsId)){
            return baseDao.getById(PfBusiness.class,bsId);
        }
        return null;
    }

    public List<PfBusiness> getAll(){
        QPfBusiness qPfBusiness = QPfBusiness.pfBusiness;
        JPQLQuery query = new JPAQuery(em);
        return query.from(qPfBusiness).orderBy(qPfBusiness.businessNo.asc()).orderBy(qPfBusiness.businessOrder.asc()).list(qPfBusiness);
    }

    public void update(PfBusiness business){
        if (business != null && StringUtils.isNotBlank(business.getBusinessId())){
            PfBusiness tmpBs = findById(business.getBusinessId());
            if (tmpBs != null){
                tmpBs.setBusinessName(business.getBusinessName());
                tmpBs.setBusinessCode(business.getBusinessCode());
                tmpBs.setBusinessNo(business.getBusinessNo());
                tmpBs.setBusinessUrl(business.getBusinessUrl());
                tmpBs.setDatasourceType(business.getDatasourceType());
                tmpBs.setDatasourceUrl(business.getDatasourceUrl());
                tmpBs.setDatasourceUser(business.getDatasourceUser());
                tmpBs.setDatasourcePass(business.getDatasourcePass());
                tmpBs.setRemark(business.getRemark());
                baseDao.update(tmpBs);
            }
        }
    }

    public List<Ztree> getBusinessTree() {
        List<Ztree> treeList = new ArrayList<Ztree>();
        //获取业务列表、工作流列表
        List<PfBusiness> businessList = getAll();
        if (businessList != null && businessList.size() > 0){
            for (PfBusiness business : businessList) {
                Ztree tree = toZtreeByBusiness(business);
                treeList.add(tree);
            }
        }
        return treeList;
    }

    private static Ztree toZtreeByBusiness(PfBusiness business) {
        Ztree tree = new Ztree();
        tree.setId(business.getBusinessId());
        tree.setName(business.getBusinessName());
        return tree;
    }

}
