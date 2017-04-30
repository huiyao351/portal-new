/*
 * Created with [rapid-framework]
 * 2016-06-07
 */

package com.gtis.portal.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "PF_DISTRICT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PfDistrict implements java.io.Serializable{

	@Id
	@Column
	private String districtId;//行政id
	@Column
	private String districtName;//行政区名称
	@Column
	private String districtCode;//行政区编码
	@Column
	private String districtParentId;//父节点
	@Transient
	private String blankStr;

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictParentId() {
		return districtParentId;
	}

	public void setDistrictParentId(String districtParentId) {
		this.districtParentId = districtParentId;
	}

	public String getBlankStr() {
		return blankStr;
	}

	public void setBlankStr(String blankStr) {
		this.blankStr = blankStr;
	}
}

