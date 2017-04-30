/*
 * Created with [rapid-framework]
 * 2016-06-20
 */

package com.gtis.portal.entity;

import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.math.BigDecimal;

import java.util.*;



@Entity
@Table(name = "PF_PARTITION_INFO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfPartitionInfo implements java.io.Serializable{
	
	@Id
	@Column
	private String pfPartitionInfoId;//PF_PARTITION_INFO_ID
	
	@Column
	private String partitionId;//PARTITION_ID
	
	@Column
	private String elementId;//元素ID
	
	@Column
	private String elementName;//元素名
	
	@Column
	private String elementJs;//元素脚本
	
	@Column
	private String elementIcon;//元素图标
		
	public void setPfPartitionInfoId(String value) {
		this.pfPartitionInfoId = value;
	}
	
	public String getPfPartitionInfoId() {
		return this.pfPartitionInfoId;
	}
		
	public void setPartitionId(String value) {
		this.partitionId = value;
	}
	
	public String getPartitionId() {
		return this.partitionId;
	}
		
	public void setElementId(String value) {
		this.elementId = value;
	}
	
	public String getElementId() {
		return this.elementId;
	}
		
	public void setElementName(String value) {
		this.elementName = value;
	}
	
	public String getElementName() {
		return this.elementName;
	}
		
	public void setElementJs(String value) {
		this.elementJs = value;
	}
	
	public String getElementJs() {
		return this.elementJs;
	}
		
	public void setElementIcon(String value) {
		this.elementIcon = value;
	}
	
	public String getElementIcon() {
		return this.elementIcon;
	}
}

