package com.ybase.dorm.vo;

import java.io.Serializable;

import com.ybase.dorm.annotation.Column;
import com.ybase.dorm.annotation.Table;

/**
 * 图片<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 */
@Table("dr_image")
public class DrImage extends DormVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 编号 */
	@Column(name = "id", type = "int", key = true)
	private Integer id;

	/** 图片路径 */
	@Column(name = "picpath", type = "string")
	private String picPath;

	/** 创建日期 */
	@Column(name = "crdate", type = "string")
	private String crDate;

	/** 创建时间 */
	@Column(name = "crtime", type = "string")
	private String crTime;

	/** 点赞次数 */
	@Column(name = "yescount", type = "int")
	private Integer yesCount = 0;

	/** 图片系统出现位置 C:经典照片 O:其他 */
	@Column(name = "position", type = "string")
	private String position;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getCrDate() {
		return crDate;
	}

	public void setCrDate(String crDate) {
		this.crDate = crDate;
	}

	public String getCrTime() {
		return crTime;
	}

	public void setCrTime(String crTime) {
		this.crTime = crTime;
	}

	public Integer getYesCount() {
		return yesCount;
	}

	public void setYesCount(Integer yesCount) {
		this.yesCount = yesCount;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
