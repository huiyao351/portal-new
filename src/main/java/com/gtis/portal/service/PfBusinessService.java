package com.gtis.portal.service;


import com.gtis.portal.entity.PfBusiness;
import com.gtis.portal.entity.PfResource;
import com.gtis.portal.model.Ztree;

import java.util.List;

public interface PfBusinessService {
    public PfBusiness findById(String bsId);
    public List<PfBusiness> getAll();
    public void update(PfBusiness business);
    public List<Ztree> getBusinessTree();
}
