/*
 * Created with [rapid-framework]
 * 2016-06-19
 */

package com.gtis.portal.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "PF_ROLE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfRole implements java.io.Serializable{
	@Id
	@Column
	private String roleId;//ROLE_ID
	@Column
	private String roleName;//角色名称
	@Column
	private String regionCode;//行政区代码
	@Column
	private String roleNo;//角色编码

	@Transient
	private List<PfUser> user;

	@Transient
	private PfDistrict district;


	@Transient
	private String userid;//用户列表的ID字符串集

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOriginaluserid() {
		return originaluserid;
	}

	public void setOriginaluserid(String originaluserid) {
		this.originaluserid = originaluserid;
	}

	@Transient

	private String originaluserid;//用户列表的ID字符串集


	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public List<PfUser> getUser() {
		return user;
	}

	public void setUser(List<PfUser> user) {
		this.user = user;
	}

	public PfDistrict getDistrict() {
		return district;
	}

	public void setDistrict(PfDistrict district) {
		this.district = district;
	}
}

