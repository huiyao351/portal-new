/*
 * Created with [rapid-framework]
 * 2016-06-07
 */

package com.gtis.portal.entity;

import com.gtis.plat.vo.PfWorkFlowDefineVo;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;


@Entity
@Table(name = "PF_BUSINESS_GROUP")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfBusinessGroup implements java.io.Serializable{
	@Id
	@Column
	private String businessGroupId;//业务分组
	
	@Column
	private String businessGroupName;//业务分组名称
	
	@Column
	private Integer businessGroupNo;//业务分组编号排序用
	
	@Column
	private String businessIds;//业务定义ID

	@Transient
	private LinkedHashMap<String,List<PfWorkFlowDefineVo>> workflowDefineMap;

	public String getBusinessGroupId() {
		return businessGroupId;
	}

	public void setBusinessGroupId(String businessGroupId) {
		this.businessGroupId = businessGroupId;
	}

	public String getBusinessGroupName() {
		return businessGroupName;
	}

	public void setBusinessGroupName(String businessGroupName) {
		this.businessGroupName = businessGroupName;
	}

	public Integer getBusinessGroupNo() {
		return businessGroupNo;
	}

	public void setBusinessGroupNo(Integer businessGroupNo) {
		this.businessGroupNo = businessGroupNo;
	}

	public String getBusinessIds() {
		return businessIds;
	}

	public void setBusinessIds(String businessIds) {
		this.businessIds = businessIds;
	}

	public LinkedHashMap<String, List<PfWorkFlowDefineVo>> getWorkflowDefineMap() {
		return workflowDefineMap;
	}

	public void setWorkflowDefineMap(LinkedHashMap<String, List<PfWorkFlowDefineVo>> workflowDefineMap) {
		this.workflowDefineMap = workflowDefineMap;
	}
}

