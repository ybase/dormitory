package com.ybase.dorm.vo;

import com.ybase.dorm.annotation.Column;
import com.ybase.dorm.annotation.Table;

/*
 * 论题<br/>
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 */
@Table("dr_blog")
public class DrBlog extends DormVO {

	/** 编号 */
	@Column(name = "id", type = "int", key = true)
	private Integer id;

	/** 主题 */
	@Column(name = "theme", type = "string")
	private String theme;

	/** 主题描述 */
	@Column(name = "blogdesc", type = "blob")
	private String blogDesc;

	/** 创建日期 */
	@Column(name = "crdate", type = "string")
	private String crDate;

	/** 创建时间 */
	@Column(name = "crtime", type = "string")
	private String crTime;

	/** 点赞次数 */
	@Column(name = "yescount", type = "int")
	private Integer yesCount = 0;

	/** 创建人ID */
	@Column(name = "crusr", type = "int")
	private Integer crUsr;

	/** 创建人名称 */
	@Column(name = "usrname", type = "string")
	private String usrName;

	/** 关联图片ID */
	@Column(name = "imgid", type = "int")
	private Integer imgId;

	/** 关联图片picpath */
	@Column(name = "picpath", type = "string")
	private String picPath;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getBlogDesc() {
		return blogDesc;
	}

	public void setBlogDesc(String blogDesc) {
		this.blogDesc = blogDesc;
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

	public Integer getCrUsr() {
		return crUsr;
	}

	public void setCrUsr(Integer crUsr) {
		this.crUsr = crUsr;
	}

	public Integer getImgId() {
		return imgId;
	}

	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	@Override
	public String toString() {
		return "DrBlog [id=" + id + ", theme=" + theme + ", blogDesc=" + blogDesc + ", crDate=" + crDate + ", crTime=" + crTime + ", yesCount=" + yesCount + ", crUsr=" + crUsr + ", usrName="
				+ usrName + ", imgId=" + imgId + "]";
	}

}
