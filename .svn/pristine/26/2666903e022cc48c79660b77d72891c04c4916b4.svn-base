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
@Table(name = "PF_RESOURCE_PARTITION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfResourcePartition implements java.io.Serializable{
	
	@Id
	@Column
	private String partitionId;//PARTITION_ID
	
	@Column
	private String resourceId;//RESOURCE_ID
	
	@Column
	private String partitionName;//分区名称
	
	@Column
	private Integer partitionType;//分区类型

	@Transient
	private Integer operType;
		
	public void setPartitionId(String value) {
		this.partitionId = value;
	}
	
	public String getPartitionId() {
		return this.partitionId;
	}
		
	public void setResourceId(String value) {
		this.resourceId = value;
	}
	
	public String getResourceId() {
		return this.resourceId;
	}
		
	public void setPartitionName(String value) {
		this.partitionName = value;
	}
	
	public String getPartitionName() {
		return this.partitionName;
	}

	public Integer getPartitionType() {
		return partitionType;
	}

	public void setPartitionType(Integer partitionType) {
		this.partitionType = partitionType;
	}

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}
}

