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
@Table(name = "PF_STUFF_CONFIG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfStuffConfig implements java.io.Serializable{
	
	@Id
	@Column
	private String stuffId;//STUFF_ID
	
	@Column
	private String proId;//PRO_ID
	
	@Column
	private Integer stuffXh;//附件序号
	
	@Column
	private String stuffName;//附件名称
	
	@Column
	private String meterial;//附件形式
	
	@Column
	private Integer stuffCount;//附件总数
	
	@Column
	private String remark;//备注
	
	@Column
	private String workflowDefinitionId;//工作流定义表
	
	@Column
	private Integer ysnum;//已收份数
	
	@Column
	private Integer dbnum;//待补份数
		
	public void setStuffId(String value) {
		this.stuffId = value;
	}
	
	public String getStuffId() {
		return this.stuffId;
	}
		
	public void setProId(String value) {
		this.proId = value;
	}
	
	public String getProId() {
		return this.proId;
	}
		
	public void setStuffXh(Integer value) {
		this.stuffXh = value;
	}
	
	public Integer getStuffXh() {
		return this.stuffXh;
	}
		
	public void setStuffName(String value) {
		this.stuffName = value;
	}
	
	public String getStuffName() {
		return this.stuffName;
	}
		
	public void setMeterial(String value) {
		this.meterial = value;
	}
	
	public String getMeterial() {
		return this.meterial;
	}
		
	public void setStuffCount(Integer value) {
		this.stuffCount = value;
	}
	
	public Integer getStuffCount() {
		return this.stuffCount;
	}
		
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
		
	public void setWorkflowDefinitionId(String value) {
		this.workflowDefinitionId = value;
	}
	
	public String getWorkflowDefinitionId() {
		return this.workflowDefinitionId;
	}
		
	public void setYsnum(Integer value) {
		this.ysnum = value;
	}
	
	public Integer getYsnum() {
		return this.ysnum;
	}
		
	public void setDbnum(Integer value) {
		this.dbnum = value;
	}
	
	public Integer getDbnum() {
		return this.dbnum;
	}
}

