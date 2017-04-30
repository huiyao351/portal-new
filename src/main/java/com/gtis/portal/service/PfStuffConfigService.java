package com.gtis.portal.service;


import com.gtis.portal.entity.PfResourceGroup;
import com.gtis.portal.entity.PfStuffConfig;
import com.gtis.portal.model.Ztree;

import java.util.List;

public interface PfStuffConfigService extends BaseService<PfStuffConfig, String> {
    public List<PfStuffConfig> getListByWfdId(String wfdId);

    public Ztree getZtreeByWfdId(String wfdId);

    public List<PfStuffConfig> getAllChildStuffListById(String stuffId);

    public void deleteStuffAndChildById(String stuffId);
}
