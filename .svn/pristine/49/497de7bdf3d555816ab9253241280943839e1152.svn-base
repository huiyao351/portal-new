package com.gtis.portal.entity;

import org.apache.commons.lang.StringUtils;

/**
 * 公共类Vo，用于List中的name、value(name、id)
 * name对应名称,value对应id
 * @文件名 PublicVo.java
 * @作者 卢向伟
 * @创建日期 May 31, 2011
 * @创建时间 2:10:43 PM 
 * @版本号 V 1.0
 */
public class PublicVo implements Comparable<PublicVo> {
	private String value;//实际数据
	private String name;//用于显示
	/**
	 * 参数显示数据
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public PublicVo() {
		super();
	}
	public PublicVo(String value, String name) {
		super();
		if (StringUtils.isBlank(value)) {
			value = "";
		}
		if (StringUtils.isBlank(name)) {
			name = "";
		}
		this.value = value.replace("null", "");
		this.name = name.replace("null", "");
	}
	/**
	 * 参数保存数据
	 * @return
	 */
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int compareTo(PublicVo arg0) {
		if (arg0 == null) {
			return 1;
		}else {
			return this.getValue().compareTo(arg0.getValue());//升序排列
//			return obj.getXm_xcsj().compareTo(this.getXm_xcsj());//倒叙
		}

	}
}
