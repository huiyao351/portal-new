package com.gtis.portal.entity;

import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
/**
 * Created with IntelliJ IDEA.
 * User: jibo
 * Date: 14-4-14
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "PF_MENU")
public class PfMenu implements Serializable {

    @Id
    @Column
    private String menuId;

    @Column
    private String menuCode;

    @Column
    private String menuName;

    @Column
    private int menuOrder;

    @Column
    private String menuCss;

    @Column
    private String menuParentId;

    @Column
    private String resourceId;

    @Column
    private boolean menuExpanded;

    @Column
    private int menuModel;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "pf_subsystem_menu_rel", joinColumns = @JoinColumn(name = "menu_id"), inverseJoinColumns = {@JoinColumn(name = "subsystem_id")})
//    private Set<PfSubsystem> subsystems = Sets.newHashSet();

    @Transient
    private PfResource resource;

    @Transient
    private boolean check;


    public int getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(int menuModel) {
        this.menuModel = menuModel;
    }

    public boolean isMenuExpanded() {
        return menuExpanded;
    }

    public void setMenuExpanded(boolean menuExpanded) {
        this.menuExpanded = menuExpanded;
    }

    public String getMenuCss() {
        return menuCss;
    }

    public void setMenuCss(String menuCss) {
        this.menuCss = menuCss;
    }

    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }


//    public Set<PfSubsystem> getSubsystems() {
//        return subsystems;
//    }
//
//    public void setSubsystems(Set<PfSubsystem> subsystems) {
//        this.subsystems = subsystems;
//    }

    public PfResource getResource() {
        return resource;
    }

    public void setResource(PfResource resource) {
        this.resource = resource;
    }
}
