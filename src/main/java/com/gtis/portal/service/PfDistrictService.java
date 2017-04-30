package com.gtis.portal.service;


import com.gtis.portal.entity.PfDistrict;
import com.gtis.portal.model.Ztree;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public interface PfDistrictService extends BaseService<PfDistrict, String> {

    /**
     * 根据行政区代码获取行政区结构
     * @param regionCode
     * @return
     */
    public List<PfDistrict> getDistrictList(String regionCode);

    /**
     * 根据行政区代码获取行政区树节点的map对象
     * @param regionCode
     * @return
     */
    public LinkedHashMap<String, Ztree> getDistrictMap(String regionCode);

    /**
     * 组织行政区树
     * @param regionCode
     * @return
     */
    Ztree getAllDistrictTree(String regionCode);

    public Ztree toZtree(PfDistrict districtVo);

    /**
     * 组织行政区下拉框
     * @param regionCode
     * @return
     */
    public List<PfDistrict> getAllDistrictList(String regionCode);

    void deleteDistrict(PfDistrict district);


}
