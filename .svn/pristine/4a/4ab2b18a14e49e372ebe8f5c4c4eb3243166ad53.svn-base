/*
 * Created with [rapid-framework]
 * 2016-06-07
 */

package com.gtis.portal.entity;

import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.math.BigDecimal;

import java.util.*;



@Entity
@Table(name = "PF_RESOURCE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfResource implements java.io.Serializable{

	@Id
	@Column
	private String resourceId;//RESOURCE_ID
	@Column
	private String businessId;//businessId
	@Column
	private String resourceName;//资源名称
	@Column
	private String resourceCode;//资源代码
	@Column
	private Integer resourceType;//资源类型
	@Column
	private Integer loadMode;//加载方式
	@Column
	private String resourceUrl;//资源URL
	@Column
	private String resourceNo;//资源编码
	@Column
	private String groupId;//GROUP_ID
	@Transient
	private PfBusiness business;
		
	public void setResourceId(String value) {
		this.resourceId = value;
	}
	
	public String getResourceId() {
		return this.resourceId;
	}
		
	public void setBusinessId(String value) {
		this.businessId = value;
	}
	
	public String getBusinessId() {
		return this.businessId;
	}
		
	public void setResourceName(String value) {
		this.resourceName = value;
	}
	
	public String getResourceName() {
		return this.resourceName;
	}
		
	public void setResourceCode(String value) {
		this.resourceCode = value;
	}
	
	public String getResourceCode() {
		return this.resourceCode;
	}
		
	public void setResourceType(Integer value) {
		this.resourceType = value;
	}
	
	public Integer getResourceType() {
		return this.resourceType;
	}
		
	public void setLoadMode(Integer value) {
		this.loadMode = value;
	}
	
	public Integer getLoadMode() {
		return this.loadMode;
	}
		
	public void setResourceUrl(String value) {
		this.resourceUrl = value;
	}
	
	public String getResourceUrl() {
		return this.resourceUrl;
	}
		
	public void setResourceNo(String value) {
		this.resourceNo = value;
	}
	
	public String getResourceNo() {
		return this.resourceNo;
	}
		
	public void setGroupId(String value) {
		this.groupId = value;
	}
	
	public String getGroupId() {
		return this.groupId;
	}

	public PfBusiness getBusiness() {
		return business;
	}

	public void setBusiness(PfBusiness business) {
		this.business = business;
	}
}

