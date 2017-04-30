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
@Table(name = "PF_BUSINESS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfBusiness implements java.io.Serializable{
	
	@Id
	@Column
	private String businessId;//BUSINESS_ID
	
	@Column
	private String businessName;//业务名称
	
	@Column
	private Integer businessOrder;//业务排序编码
	
	@Column
	private String businessCode;//业务代码
	
	@Column
	private String remark;//备注
	
	@Column
	private String businessNo;//业务编号
	
	@Column
	private String datasourceUrl;//数据源_URL
	
	@Column
	private String datasourceType;//数据源_TYPE
	
	@Column
	private String datasourceUser;//数据源_USER
	
	@Column
	private String datasourcePass;//数据源_PASS
	
	@Column
	private String businessUrl;//业务_URL
		
	public void setBusinessId(String value) {
		this.businessId = value;
	}
	
	public String getBusinessId() {
		return this.businessId;
	}
		
	public void setBusinessName(String value) {
		this.businessName = value;
	}
	
	public String getBusinessName() {
		return this.businessName;
	}

	public Integer getBusinessOrder() {
		return businessOrder;
	}

	public void setBusinessOrder(Integer businessOrder) {
		this.businessOrder = businessOrder;
	}

	public void setBusinessCode(String value) {
		this.businessCode = value;
	}
	
	public String getBusinessCode() {
		return this.businessCode;
	}
		
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
		
	public void setBusinessNo(String value) {
		this.businessNo = value;
	}
	
	public String getBusinessNo() {
		return this.businessNo;
	}
		
	public void setDatasourceUrl(String value) {
		this.datasourceUrl = value;
	}
	
	public String getDatasourceUrl() {
		return this.datasourceUrl;
	}
		
	public void setDatasourceType(String value) {
		this.datasourceType = value;
	}
	
	public String getDatasourceType() {
		return this.datasourceType;
	}
		
	public void setDatasourceUser(String value) {
		this.datasourceUser = value;
	}
	
	public String getDatasourceUser() {
		return this.datasourceUser;
	}
		
	public void setDatasourcePass(String value) {
		this.datasourcePass = value;
	}
	
	public String getDatasourcePass() {
		return this.datasourcePass;
	}
		
	public void setBusinessUrl(String value) {
		this.businessUrl = value;
	}
	
	public String getBusinessUrl() {
		return this.businessUrl;
	}
}

