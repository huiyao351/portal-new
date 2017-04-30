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
@Table(name = "PF_RESOURCE_GROUP")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfResourceGroup implements java.io.Serializable{
	
	@Id
	@Column
	private String groupId;//GROUP_ID
	
	@Column
	private String groupName;//资源名称
	
	@Column
	private String businessId;//BUSINESS_ID

	@Transient
	private PfBusiness business;
		
	public void setGroupId(String value) {
		this.groupId = value;
	}
	
	public String getGroupId() {
		return this.groupId;
	}
		
	public void setGroupName(String value) {
		this.groupName = value;
	}
	
	public String getGroupName() {
		return this.groupName;
	}
		
	public void setBusinessId(String value) {
		this.businessId = value;
	}
	
	public String getBusinessId() {
		return this.businessId;
	}

	public PfBusiness getBusiness() {
		return business;
	}

	public void setBusiness(PfBusiness business) {
		this.business = business;
	}
}

