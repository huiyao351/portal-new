package com.gtis.portal.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by shenjian on 2014-05-22.
 */
@Entity
@Table(name = "PF_AUTHORIZE")
public class PfAuthorize  implements Serializable {
    @Id
    @Column
    private java.lang.String authorizeId;//AUTHORIZE_ID
    @Column
    private java.lang.String undertakerId;//授权角色
    @Column
    private Integer authorizeObjType;//授权对象类型
//    @Column
//    private java.lang.String undertakeWorkflowId;//授权工作流ID
//    @Column
//    private java.lang.String undertakeActivityId;//授权活动ID
    @Column
    private Integer operateType;//操作类型
    @Column
    private java.lang.String authorizeObjId;//授权对象ID
    @Column
    private Integer menuVisible;//菜单是否可见

    public String getAuthorizeId() {
        return authorizeId;
    }

    public void setAuthorizeId(String authorizeId) {
        this.authorizeId = authorizeId;
    }

    public String getUndertakerId() {
        return undertakerId;
    }

    public void setUndertakerId(String undertakerId) {
        this.undertakerId = undertakerId;
    }

    public Integer getAuthorizeObjType() {
        return authorizeObjType;
    }

    public void setAuthorizeObjType(Integer authorizeObjType) {
        this.authorizeObjType = authorizeObjType;
    }

    public Integer getMenuVisible() {
        return menuVisible;
    }

    public void setMenuVisible(Integer menuVisible) {
        this.menuVisible = menuVisible;
    }

    public String getAuthorizeObjId() {
        return authorizeObjId;
    }

    public void setAuthorizeObjId(String authorizeObjId) {
        this.authorizeObjId = authorizeObjId;
    }

//    public String getUndertakeWorkflowId() {
//        return undertakeWorkflowId;
//    }
//
//    public void setUndertakeWorkflowId(String undertakeWorkflowId) {
//        this.undertakeWorkflowId = undertakeWorkflowId;
//    }
//
//    public String getUndertakeActivityId() {
//        return undertakeActivityId;
//    }
//
//    public void setUndertakeActivityId(String undertakeActivityId) {
//        this.undertakeActivityId = undertakeActivityId;
//    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }
}
