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
@Table(name = "PF_USER_ROLE_REL")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfUserRoleRel implements java.io.Serializable{
	
	
	@Column
	private String userId;//userId

	@Column
	private String roleId;//roleId
	@Id
	@Column
	private String urrId;//urrId
		
	public void setUserId(String value) {
		this.userId = value;
	}
	
	public String getUserId() {
		return this.userId;
	}
		
	public void setRoleId(String value) {
		this.roleId = value;
	}
	
	public String getRoleId() {
		return this.roleId;
	}
		
	public void setUrrId(String value) {
		this.urrId = value;
	}
	
	public String getUrrId() {
		return this.urrId;
	}
}

