package com.gtis.portal.entity;

import com.google.common.collect.Sets;

import javax.persistence.*;
import java.util.Set;

/**
* 菜单和主题关联关系
*/
@Entity
@Embeddable
@Table(name = "PF_SUBSYSTEM_MENU_REL")
public class PfSubsystemMenuRel {
//    @Id
//    @Column
//    private String keyId;
    @Column
    private String subsystemId;
    @Id
    @Column
    private String menuId;
    @Transient
    private PfMenu menu;

    public String getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(String subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public PfMenu getMenu() {
        return menu;
    }

    public void setMenu(PfMenu menu) {
        this.menu = menu;
    }

//    public String getKeyId() {
//        return keyId;
//    }
//
//    public void setKeyId(String keyId) {
//        this.keyId = keyId;
//    }
}
