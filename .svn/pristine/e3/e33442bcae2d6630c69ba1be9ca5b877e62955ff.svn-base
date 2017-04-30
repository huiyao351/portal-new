package com.gtis.portal.service;


import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface PfAuthorizeService extends BaseService<PfAuthorize, String> {
    public List<PfAuthorize> getAuthPartListByRoleId(String roleId,String menuId);

    /**
     * 查询该配置功能下，有哪些权限可见
     * @param authorizeObjType
     * @param authorizeObjId
     * 针对菜单在角色下是否可见的功能配置类型authorizeObjType为1
     * 针对菜单在角色下功能分区的功能配置类型authorizeObjType为0
     * 针对主题在角色下是否可见的功能配置类型authorizeObjType为8
     *
     * @return
     */
    public List<PfAuthorize> getAuthPartListByObjId(Integer authorizeObjType,String authorizeObjId);
    /**
     * 根据菜单id，获取那些角色可以看到该菜单
     * @param menuId
     * @return
     */
    public List<PfRole> getAuthorizeRoleListByMenuId(String menuId);

    public List<PfMenu> getAuthorizeMenuListByRoleId(String roleId);

    public boolean checkHasAuth(String undertakerId,String authorizeObjId,Integer authorizeObjType);

    public PfAuthorize getAuthByRoleAndObjId(String undertakerId,String authorizeObjId,Integer authorizeObjType);

    public void delAuthByRoleAndObjId(String undertakerId,String authorizeObjId,Integer authorizeObjType);

    public List<ZtreeChanged> getAuthorizeRoleTreeListByMenuId(String menuId);

    public void updateRoleMenuVisible(String roleId,String menuId,Integer visible,Integer authorizeObjType);
    public void updateMenuRoleRel(String menuId,List<ZtreeChanged> changeList);

    public void updateMenuRoleAuthorizePart(String roleId,String menuId,Integer authorizeObjType);
    public void updatePartOperType(String menuId,String roleId,List<ZtreeChanged> changeList);

    public void deleteAuthorizeListByRole(String roleId);
    public void delAuthByObjIdAndType(String authorizeObjId,Integer authorizeObjType);
    public void deleteAuthorizeListByMenu(String menuId);
    public void deleteAuthorizeListByResource(String resourceId);
    public void deleteAuthorizeListByPartitionId(String partitionId);


}
