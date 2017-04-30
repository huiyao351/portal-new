/*
 * Created with [rapid-framework]
 * 2016-06-18
 */

package com.gtis.portal.entity;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.math.BigDecimal;

import java.sql.Blob;
import java.util.*;



@Entity
@Table(name = "PF_WORKFLOW_DEFINITION")
public class PfWorkflowDefinition implements java.io.Serializable{
	@Id
	@Column
	private String workflowDefinitionId;//WORKFLOW_DEFINITION_ID
	@Column
	private String businessId;//BUSINESS_ID
	@Column
	private String regionCode;//行政区代码
	@Column
	private String workflowCode;//工作流定义编码
	@Column
	private String workflowName;//工作流定义名称
	@Column
	private Date modifyTime;//工作流修改时间
	@Column
	private String workflowVersion;//工作流版本
	@Column
	private String priority;//办理优先级
	@Column
	private String remark;//备注
	@Column
	private Date createTime;//创建时间
	@Column
	private String createUrl;//新建任务URL
	@Column
	private Integer createHeight;//新建任务网页高度
	@Column
	private Integer createWidth;//新建任务网页宽度
	@Column
	private String workflowDefinitionNo;//工作流定义编号
	@Column
	private String workfloweventBean;//工作流事件编号
	@Column
	private Integer isValid;//是否可用
	@Column
	private Integer isMonitor;//是否监控
	@Column
	private String groupId;//分组编号
	@Column
	private String timeLimit;//timeLimit

	@Lob
	@Basic(fetch = FetchType.EAGER)
	@JSONField(serialize = false)
	@Column(name="WORKFLOW_XML", columnDefinition="CLOB", nullable=true)
	private String  workflowXml;//工作流定义
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@JSONField(serialize = false)
	@Column(name="FLOWCHART", columnDefinition="BLOB", nullable=true)
	private java.sql.Blob flowchart;//流程图对象
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@JSONField(serialize = false)
	@Column(name="OPERATION_DEFINE", columnDefinition="CLOB", nullable=true)
	private String  operationDefine;//OPERATION_DEFINE
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@JSONField(serialize = false)
	@Column(name="WORKFLOW_DEFINITION_SHELL", columnDefinition="CLOB", nullable=true)
	private String  workflowDefinitionShell;//工作流事件定义
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@JSONField(serialize = false)
	@Column(name="WORKFLOW_IMAGE", columnDefinition="BLOB", nullable=true)
	private java.sql.Blob workflowImage;//流程图
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@JSONField(serialize = false)
	@Column(name="WORKFLOW_LOCATION", columnDefinition="CLOB", nullable=true)
	private String  workflowLocation;//工作流活动位置信息

	@Transient
	private PfBusiness business;
		
	public void setWorkflowDefinitionId(String value) {
		this.workflowDefinitionId = value;
	}
	
	public String getWorkflowDefinitionId() {
		return this.workflowDefinitionId;
	}
		
	public void setBusinessId(String value) {
		this.businessId = value;
	}
	
	public String getBusinessId() {
		return this.businessId;
	}
		
	public void setRegionCode(String value) {
		this.regionCode = value;
	}
	
	public String getRegionCode() {
		return this.regionCode;
	}
		
	public void setWorkflowCode(String value) {
		this.workflowCode = value;
	}
	
	public String getWorkflowCode() {
		return this.workflowCode;
	}
		
	public void setWorkflowName(String value) {
		this.workflowName = value;
	}
	
	public String getWorkflowName() {
		return this.workflowName;
	}
		
	public void setModifyTime(Date value) {
		this.modifyTime = value;
	}
	
	public Date getModifyTime() {
		return this.modifyTime;
	}
		
	public void setWorkflowVersion(String value) {
		this.workflowVersion = value;
	}
	
	public String getWorkflowVersion() {
		return this.workflowVersion;
	}
		
	public void setPriority(String value) {
		this.priority = value;
	}
	
	public String getPriority() {
		return this.priority;
	}
		
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
		
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
		
	public void setCreateUrl(String value) {
		this.createUrl = value;
	}
	
	public String getCreateUrl() {
		return this.createUrl;
	}
		
	public void setCreateHeight(Integer value) {
		this.createHeight = value;
	}
	
	public Integer getCreateHeight() {
		return this.createHeight;
	}
		
	public void setCreateWidth(Integer value) {
		this.createWidth = value;
	}
	
	public Integer getCreateWidth() {
		return this.createWidth;
	}
		
	public void setWorkflowDefinitionNo(String value) {
		this.workflowDefinitionNo = value;
	}
	
	public String getWorkflowDefinitionNo() {
		return this.workflowDefinitionNo;
	}
		
	public void setWorkfloweventBean(String value) {
		this.workfloweventBean = value;
	}
	
	public String getWorkfloweventBean() {
		return this.workfloweventBean;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getIsMonitor() {
		return isMonitor;
	}

	public void setIsMonitor(Integer isMonitor) {
		this.isMonitor = isMonitor;
	}

	public void setGroupId(String value) {
		this.groupId = value;
	}
	
	public String getGroupId() {
		return this.groupId;
	}
		
	public void setTimeLimit(String value) {
		this.timeLimit = value;
	}
	
	public String getTimeLimit() {
		return this.timeLimit;
	}

	public PfBusiness getBusiness() {
		return business;
	}

	public void setBusiness(PfBusiness business) {
		this.business = business;
	}

	public String getWorkflowXml() {
		return workflowXml;
	}

	public void setWorkflowXml(String workflowXml) {
		this.workflowXml = workflowXml;
	}

	public Blob getFlowchart() {
		return flowchart;
	}

	public void setFlowchart(Blob flowchart) {
		this.flowchart = flowchart;
	}

	public String getOperationDefine() {
		return operationDefine;
	}

	public void setOperationDefine(String operationDefine) {
		this.operationDefine = operationDefine;
	}

	public String getWorkflowDefinitionShell() {
		return workflowDefinitionShell;
	}

	public void setWorkflowDefinitionShell(String workflowDefinitionShell) {
		this.workflowDefinitionShell = workflowDefinitionShell;
	}

	public Blob getWorkflowImage() {
		return workflowImage;
	}

	public void setWorkflowImage(Blob workflowImage) {
		this.workflowImage = workflowImage;
	}

	public String getWorkflowLocation() {
		return workflowLocation;
	}

	public void setWorkflowLocation(String workflowLocation) {
		this.workflowLocation = workflowLocation;
	}
}

