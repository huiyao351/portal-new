package com.gtis.portal.entity;

import com.gtis.portal.model.Ztree;

import javax.persistence.*;
import java.util.Set;

/**
 * 子系统
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/3/9
 */
@Entity
@Table(name = "PF_SUBSYSTEM")
public class PfSubsystem {
    @Id
    @Column
    private String subsystemId;
    @Column
    private String subsystemName;
    @Column
    private String subsystemTitle;
    @Column
    private Boolean enabled;
    @Column
    private Integer subType;//主题类型：0菜单还是1超链接
    @Column
    private Integer subNo;//编号（排序码）
    @Column
    private String subUrl;//超链接地址
    @Column
    private String subMenuType;//菜单风格，two左两级、three三级
    @Transient
    private Ztree submenuTree;

    public String getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(String subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    public String getSubsystemTitle() {
        return subsystemTitle;
    }

    public void setSubsystemTitle(String subsystemTitle) {
        this.subsystemTitle = subsystemTitle;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Ztree getSubmenuTree() {
        return submenuTree;
    }

    public void setSubmenuTree(Ztree submenuTree) {
        this.submenuTree = submenuTree;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Integer getSubNo() {
        return subNo;
    }

    public void setSubNo(Integer subNo) {
        this.subNo = subNo;
    }

    public String getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }

    public String getSubMenuType() {
        return subMenuType;
    }

    public void setSubMenuType(String subMenuType) {
        this.subMenuType = subMenuType;
    }
}
