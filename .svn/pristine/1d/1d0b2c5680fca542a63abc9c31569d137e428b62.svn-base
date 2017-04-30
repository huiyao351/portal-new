package com.gtis.portal.service.impl;


import com.gtis.common.util.UUIDGenerator;
import com.gtis.config.AppConfig;
import com.gtis.portal.entity.PfOrgan;
import com.gtis.portal.entity.PfUserOrganRel;
import com.gtis.portal.entity.QPfUserOrganRel;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.PfUserOrganService;

import com.gtis.portal.util.RequestUtils;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PfUserOrganServiceImpl extends BaseServiceImpl<PfUserOrganRel, String> implements PfUserOrganService {
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteByOrganId(String organId) {
        QPfUserOrganRel userOrganRel= QPfUserOrganRel.pfUserOrganRel;
     new JPADeleteClause(em, userOrganRel).where(userOrganRel.organId.eq(organId)).execute();
    }

    @Override
    public List<Ztree> findOrganbyUserId(String userId) {
        List<Ztree> treeList = new ArrayList<Ztree>();
        List<PfOrgan> organList = getOrganListByUserId(userId);
        if (organList != null && organList.size() > 0){
            if (organList != null && organList.size() > 0){
                for (int i = 0; i < organList.size(); i++) {
                    Ztree tree = toZtreeByOrgan(organList.get(i));
                    treeList.add(tree);
                }
            }
        }
        return treeList;
    }

    private Ztree toZtreeByOrgan(PfOrgan organ) {
        Ztree tree = new Ztree();
        tree.setId(organ.getOrganId());
        tree.setName(organ.getOrganName());
        tree.setPid("treeroot");
        tree.setIcon(RequestUtils.initOptProperties(AppConfig.getProperty("portal.url") + "/static/images/folder.gif"));
        return tree;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void deleteUserOrganRelByUserIdAndOrganId(String user_id, String organ_id) {
        QPfUserOrganRel userOrganRel= QPfUserOrganRel.pfUserOrganRel;
        new JPADeleteClause(em, userOrganRel).where(userOrganRel.organId.eq(organ_id)).where(userOrganRel.userId.eq(user_id)).execute();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean findUserOrgan(PfUserOrganRel userOrganRel) {
        QPfUserOrganRel qfpuserOrganRel= QPfUserOrganRel.pfUserOrganRel;
        JPQLQuery query = new JPAQuery(em);
        List<PfUserOrganRel> pfUserOrganList=  query.from(qfpuserOrganRel).where(qfpuserOrganRel.userId.eq(userOrganRel.getUserId())).where(qfpuserOrganRel.organId.eq(userOrganRel.getOrganId())).list(qfpuserOrganRel);
        if(pfUserOrganList!=null&&pfUserOrganList.size()>=1){
            return true;
        }
        return false;
    }

    public List<PfOrgan> getOrganListByUserId(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            String jpql = "select distinct t1 from PfOrgan t1,PfUserOrganRel t " +
                    " where t.organId=t1.organId and t.userId=?0 ";
            jpql += " order by t1.organNo ";
            return baseDao.getByJpql(jpql, userId);
        }
        return null;
    }

    public boolean checkHasOrgan(String userId,String organId){
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(organId)){
            String jpql = "select t from PfUserOrganRel t where t.userId=?0 and  t.organId=?1 and rownum=1";
            PfUserOrganRel obj = (PfUserOrganRel)baseDao.getUniqueResultByJpql(jpql,userId,organId);
            if (obj != null && StringUtils.isNotBlank(obj.getUdrId())){
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void addOrganRelByUserId(String userId,List<ZtreeChanged> changeList){
        if (changeList != null && changeList.size() > 0 && StringUtils.isNotBlank(userId)){
            for (int i = 0; i < changeList.size(); i++) {
                ZtreeChanged change = changeList.get(i);
                if (change.isLeaf()){
                    boolean hasObj = checkHasOrgan(userId, change.getId());
                    if (!hasObj){
                        PfUserOrganRel rel = new PfUserOrganRel();
                        rel.setUdrId(UUIDGenerator.generate18());
                        rel.setOrganId(change.getId());
                        rel.setUserId(userId);
                        insert(rel);
                    }
                }
            }
        }
    }

    @Transactional
    public void addUserRelByOrganId(String organId,List<ZtreeChanged> changeList){
        if (changeList != null && changeList.size() > 0 && StringUtils.isNotBlank(organId)){
            for (int i = 0; i < changeList.size(); i++) {
                ZtreeChanged change = changeList.get(i);
                if (change.isLeaf()){
                    boolean hasObj = checkHasOrgan(change.getId(),organId);
                    if (!hasObj){
                        PfUserOrganRel rel = new PfUserOrganRel();
                        rel.setUdrId(UUIDGenerator.generate18());
                        rel.setOrganId(organId);
                        rel.setUserId(change.getId());
                        insert(rel);
                    }
                }
            }
        }
    }
}
