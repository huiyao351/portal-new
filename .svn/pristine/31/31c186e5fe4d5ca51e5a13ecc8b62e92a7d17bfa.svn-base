/*
 * Created with [rapid-framework]
 * 2016-06-19
 */

package com.gtis.portal.entity;

import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.math.BigDecimal;

import java.util.*;



@Entity
@Table(name = "PF_ORGAN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfOrgan implements java.io.Serializable{
	
	@Id
	@Column
	private String organId;//ORGAN_ID
	
	@Column
	private String organName;//部门名称
	
	@Column
	private String regionCode;//行政区代码
	
	@Column
	private Date createDate;//创建时间
	
	@Column
	private String email;//EMAIL
	
	@Column
	private String officeTel;//办公室电话
	
	@Column
	private String superOrganId;//上级部门
	
	@Column
	private String remark;//备注
	
	@Column
	private String organNo;//部门编号
		
	public void setOrganId(String value) {
		this.organId = value;
	}
	
	public String getOrganId() {
		return this.organId;
	}
		
	public void setOrganName(String value) {
		this.organName = value;
	}
	
	public String getOrganName() {
		return this.organName;
	}
		
	public void setRegionCode(String value) {
		this.regionCode = value;
	}
	
	public String getRegionCode() {
		return this.regionCode;
	}
		
	public void setCreateDate(Date value) {
		this.createDate = value;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
		
	public void setEmail(String value) {
		this.email = value;
	}
	
	public String getEmail() {
		return this.email;
	}
		
	public void setOfficeTel(String value) {
		this.officeTel = value;
	}
	
	public String getOfficeTel() {
		return this.officeTel;
	}
		
	public void setSuperOrganId(String value) {
		this.superOrganId = value;
	}
	
	public String getSuperOrganId() {
		return this.superOrganId;
	}
		
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
		
	public void setOrganNo(String value) {
		this.organNo = value;
	}
	
	public String getOrganNo() {
		return this.organNo;
	}
}

