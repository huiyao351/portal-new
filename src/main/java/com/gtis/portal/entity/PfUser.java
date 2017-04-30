/*
 * Created with [rapid-framework]
 * 2016-06-19
 */

package com.gtis.portal.entity;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;


@Entity
@Table(name = "PF_USER")
public class PfUser implements java.io.Serializable{
	@Id
	@Column
	private String userId;//USER_ID
	@Column
	private String userNo;//用户编号
	@Column
	private String userName;//用户姓名
	@Column
	private String loginName;//登录名
	@Column
	private String loginPassword;//登陆密码
	@Column
	private String userRank;//职称
	@Column
	private String userPost;//职位
	@Column
	private Integer isValid;//是否有效
	@Column
	private String email;//EMAIL
	@Column
	private String mobilePhone;//移动电话
	@Column
	private String officePhone;//办公电话
	@Column
	private String homePhone;//家庭电话
	@Column
	private BigDecimal userType;//USER_TYPE
	@Column
	private Date birthdate;//用户生日
	@Column
	private String userDegree;//学历
	@Column
	private String userSex;//性别
	@Column
	private String address;//家庭地址
	@Column
	private String remark;//备注

	@Transient
	private String organId;//部门Id

	@Lob
	@JSONField(serialize = false)
	@Column(name="USER_PHOTO", columnDefinition="BLOB", nullable=true)
	private java.sql.Blob userPhoto;//用户照片
	@Lob
	@JSONField(serialize = false)
	@Column(name="USER_SIGN", columnDefinition="BLOB", nullable=true)
	private java.sql.Blob userSign;//用户签名

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public void setUserId(String value) {
		this.userId = value;
	}

	public Blob getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(Blob userPhoto) {
		this.userPhoto = userPhoto;
	}

	public Blob getUserSign() {
		return userSign;
	}

	public void setUserSign(Blob userSign) {
		this.userSign = userSign;
	}

	public String getUserId() {
		return this.userId;
	}
		
	public void setUserNo(String value) {
		this.userNo = value;
	}
	
	public String getUserNo() {
		return this.userNo;
	}
		
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return this.userName;
	}
		
	public void setLoginName(String value) {
		this.loginName = value;
	}
	
	public String getLoginName() {
		return this.loginName;
	}
		
	public void setUserRank(String value) {
		this.userRank = value;
	}
	
	public String getUserRank() {
		return this.userRank;
	}
		
	public void setUserPost(String value) {
		this.userPost = value;
	}
	
	public String getUserPost() {
		return this.userPost;
	}
		
	public void setEmail(String value) {
		this.email = value;
	}
	
	public String getEmail() {
		return this.email;
	}
		
	public void setMobilePhone(String value) {
		this.mobilePhone = value;
	}
	
	public String getMobilePhone() {
		return this.mobilePhone;
	}
		
	public void setOfficePhone(String value) {
		this.officePhone = value;
	}
	
	public String getOfficePhone() {
		return this.officePhone;
	}
		
	public void setHomePhone(String value) {
		this.homePhone = value;
	}
	
	public String getHomePhone() {
		return this.homePhone;
	}
		
	public void setUserType(BigDecimal value) {
		this.userType = value;
	}
	
	public BigDecimal getUserType() {
		return this.userType;
	}
		
	public void setBirthdate(Date value) {
		this.birthdate = value;
	}
	
	public Date getBirthdate() {
		return this.birthdate;
	}
		
	public void setUserDegree(String value) {
		this.userDegree = value;
	}
	
	public String getUserDegree() {
		return this.userDegree;
	}
		
	public void setUserSex(String value) {
		this.userSex = value;
	}
	
	public String getUserSex() {
		return this.userSex;
	}
		
	public void setAddress(String value) {
		this.address = value;
	}
	
	public String getAddress() {
		return this.address;
	}
		
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
}

