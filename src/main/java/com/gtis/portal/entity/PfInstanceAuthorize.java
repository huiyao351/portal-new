/*
 * Created with [rapid-framework]
 * 2016-06-27
 */

package com.gtis.portal.entity;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.math.BigDecimal;

import java.util.*;



@Entity
@Table(name = "PF_INSTANCE_AUTHORIZE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfInstanceAuthorize implements java.io.Serializable{
	@Id
	@Column
	private String wauthorizeId;//WAUTHORIZE_ID
	@Column
	private String workflowDefinitionId;//实例定义ID
	@Column
	private String resourceId;//资源ID
	@Column
	private String roleId;//角色ID
	@Column
	private Integer visible;//是否可见
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@JSONField(serialize = false)
	@Column(name="AUTHORIZE_INFO", columnDefinition="CLOB", nullable=true)
	private String authorizeInfo;//授权信息

	@Transient
	private String resourceName;

	public void setWauthorizeId(String value) {
		this.wauthorizeId = value;
	}
	
	public String getWauthorizeId() {
		return this.wauthorizeId;
	}
		
	public void setWorkflowDefinitionId(String value) {
		this.workflowDefinitionId = value;
	}
	
	public String getWorkflowDefinitionId() {
		return this.workflowDefinitionId;
	}
		
	public void setResourceId(String value) {
		this.resourceId = value;
	}
	
	public String getResourceId() {
		return this.resourceId;
	}
		
	public void setRoleId(String value) {
		this.roleId = value;
	}
	
	public String getRoleId() {
		return this.roleId;
	}

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	public String getAuthorizeInfo() {
		return authorizeInfo;
	}

	public void setAuthorizeInfo(String authorizeInfo) {
		this.authorizeInfo = authorizeInfo;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
}

