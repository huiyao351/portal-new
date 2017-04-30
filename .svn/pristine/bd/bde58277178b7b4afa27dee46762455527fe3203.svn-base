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
@Table(name = "PF_USER_ORGAN_REL")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfUserOrganRel implements java.io.Serializable{
	
	
	@Column
	private String organId;//organId
	
	@Column
	private String userId;//userId
	@Id
	@Column
	private String udrId;//udrId
		
	public void setOrganId(String value) {
		this.organId = value;
	}
	
	public String getOrganId() {
		return this.organId;
	}
		
	public void setUserId(String value) {
		this.userId = value;
	}
	
	public String getUserId() {
		return this.userId;
	}
		
	public void setUdrId(String value) {
		this.udrId = value;
	}
	
	public String getUdrId() {
		return this.udrId;
	}

}

