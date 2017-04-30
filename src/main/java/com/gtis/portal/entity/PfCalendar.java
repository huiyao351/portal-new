/*
 * Created with [rapid-framework]
 * 2016-10-18
 */

package com.gtis.portal.entity;

import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.math.BigDecimal;

import java.util.*;



@Entity
@Table(name = "PF_CALENDAR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfCalendar implements java.io.Serializable{
	
	@Id
	@Column
	private String calId;//CAL_ID
	
	@Column
	private Date calDate;//日期（天）
	
	@Column
	private String calWeek;//星期
	
	@Column
	private String calType;//工作日类型
	
	@Column
	private Date amBegin;//上午开始时间
	
	@Column
	private Date amEnd;//上午结束时间
	
	@Column
	private Date pmBegin;//下午开始时间
	
	@Column
	private Date pmEnd;//下午结束时间
	
	@Column
	private String remark;//备注

	@Transient
	private String amBeginStr;//上午开始时间

	@Transient
	private String amEndStr;//上午结束时间

	@Transient
	private String pmBeginStr;//下午开始时间

	@Transient
	private String pmEndStr;//下午结束时间
		
	public void setCalId(String value) {
		this.calId = value;
	}
	
	public String getCalId() {
		return this.calId;
	}
		
	public void setCalDate(Date value) {
		this.calDate = value;
	}
	
	public Date getCalDate() {
		return this.calDate;
	}
		
	public void setCalWeek(String value) {
		this.calWeek = value;
	}
	
	public String getCalWeek() {
		return this.calWeek;
	}
		
	public void setCalType(String value) {
		this.calType = value;
	}
	
	public String getCalType() {
		return this.calType;
	}
		
	public void setAmBegin(Date value) {
		this.amBegin = value;
	}
	
	public Date getAmBegin() {
		return this.amBegin;
	}
		
	public void setAmEnd(Date value) {
		this.amEnd = value;
	}
	
	public Date getAmEnd() {
		return this.amEnd;
	}
		
	public void setPmBegin(Date value) {
		this.pmBegin = value;
	}
	
	public Date getPmBegin() {
		return this.pmBegin;
	}
		
	public void setPmEnd(Date value) {
		this.pmEnd = value;
	}
	
	public Date getPmEnd() {
		return this.pmEnd;
	}
		
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}

	public String getAmBeginStr() {
		return amBeginStr;
	}

	public void setAmBeginStr(String amBeginStr) {
		this.amBeginStr = amBeginStr;
	}

	public String getAmEndStr() {
		return amEndStr;
	}

	public void setAmEndStr(String amEndStr) {
		this.amEndStr = amEndStr;
	}

	public String getPmBeginStr() {
		return pmBeginStr;
	}

	public void setPmBeginStr(String pmBeginStr) {
		this.pmBeginStr = pmBeginStr;
	}

	public String getPmEndStr() {
		return pmEndStr;
	}

	public void setPmEndStr(String pmEndStr) {
		this.pmEndStr = pmEndStr;
	}
}

