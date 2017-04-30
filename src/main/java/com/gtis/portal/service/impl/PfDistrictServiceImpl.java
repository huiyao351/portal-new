package com.gtis.portal.service.impl;

import com.gtis.portal.entity.PfDistrict;
import com.gtis.portal.entity.QPfDistrict;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfDistrictService;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PfDistrictServiceImpl extends BaseServiceImpl<PfDistrict, String> implements PfDistrictService {

    public List<PfDistrict> getDistrictList(String regionCode){
        List<PfDistrict> districtList = new ArrayList<PfDistrict>();
        QPfDistrict qPfDistrict = QPfDistrict.pfDistrict;
        JPQLQuery query = new JPAQuery(em);

        if (StringUtils.isNotBlank(regionCode)){
            String xzqdm = regionCode;
            if (StringUtils.length(regionCode)==6){
                //行政区代码六位，去除结尾的0000或者是00，如果是0000代表省级，如果是00，代表市级，如果没有则是区县级
                xzqdm = StringUtils.removeEnd(regionCode,"0000");
                xzqdm = StringUtils.removeEnd(regionCode,"00");
            }
            districtList = query.from(qPfDistrict).where(qPfDistrict.districtCode.like(xzqdm + "%")).orderBy(qPfDistrict.districtCode.asc()).list(qPfDistrict);
        }else {
            districtList = query.from(qPfDistrict).orderBy(qPfDistrict.districtCode.asc()).list(qPfDistrict);
        }
        return districtList;
    }

    public LinkedHashMap<String, Ztree> getDistrictMap(String regionCode){
        List<PfDistrict> districtList = getDistrictList(regionCode);
        LinkedHashMap<String, Ztree> districtMap = new LinkedHashMap<String, Ztree>();
        for (PfDistrict districtVo : districtList) {
            Ztree tree = toZtree(districtVo);
            districtMap.put(tree.getId(), tree);
        }
        return districtMap;
    }

    @Override
    public Ztree getAllDistrictTree(String regionCode) {
        LinkedHashMap<String, Ztree> districtMap = getDistrictMap(regionCode);
        Ztree root = null;
        for (Ztree tree : districtMap.values()) {
            if (tree.getPid() != null) {
                Ztree ztree = districtMap.get(tree.getPid());
                if (ztree != null) {
                    ztree.addChild(tree);
                }
            } else {
                tree.setOpen(true);
                root = tree;
            }
        }
        return root;
    }

    public Ztree toZtree(PfDistrict districtVo) {
        Ztree tree = new Ztree();
        tree.setId(districtVo.getDistrictId());
        tree.setName(districtVo.getDistrictName());
        tree.setPid(StringUtils.trimToNull(districtVo.getDistrictParentId()));
        tree.setKz1(districtVo.getDistrictCode());
        return tree;
    }

    @Override
    public List<PfDistrict> getAllDistrictList(String regionCode) {
        List<PfDistrict> districtList = getDistrictList(regionCode);
        if (districtList != null && districtList.size() > 0){
            HashMap<String, PfDistrict> districtMap = new LinkedHashMap<String, PfDistrict>();
            for (PfDistrict districtVo : districtList) {
                districtMap.put(districtVo.getDistrictId(), districtVo);
            }
            PfDistrict firstDistrict = districtList.get(0);
            firstDistrict.setBlankStr("");
            PfDistrict parentDistrict = firstDistrict;
            for (int i = 1; i < districtList.size(); i++) {
                PfDistrict districtVo = districtList.get(i);
                if (districtVo.getDistrictParentId().equals(firstDistrict.getDistrictCode())){
                    districtVo.setBlankStr("　　|--"+firstDistrict.getBlankStr());
                    parentDistrict = districtVo;
                }else {
                    if (!districtVo.getDistrictParentId().equals(parentDistrict.getDistrictCode())){
                        parentDistrict = districtMap.get(districtVo.getDistrictParentId());
                    }
                    districtVo.setBlankStr("　　"+parentDistrict.getBlankStr());
                }
                districtVo.setDistrictName(districtVo.getBlankStr()+districtVo.getDistrictName());
                districtList.set(i,districtVo);
            }
        }
        return districtList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteDistrict(PfDistrict district) {
        QPfDistrict qPfDistrict = QPfDistrict.pfDistrict;
        JPQLQuery query = new JPAQuery(em);
        List<PfDistrict> districtList = query.from(qPfDistrict).where(qPfDistrict.districtParentId.eq(district.getDistrictId())).list(qPfDistrict);
        if (districtList == null || districtList.size() == 0) {
            new JPADeleteClause(em,qPfDistrict).where(qPfDistrict.districtId.eq(district.getDistrictId())).execute();
        } else {
            for (PfDistrict districtVo : districtList) {
                this.deleteDistrict(districtVo);
            }
        }
    }
}
