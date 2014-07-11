package com.ybase.dorm.vo;

import com.ybase.dorm.annotation.Column;
import com.ybase.dorm.annotation.Table;

/**
 * 主题留言<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 */
@Table("dr_talk")
public class DrTalk extends DormVO {

	/** 编号 */
	@Column(name = "id", type = "int", key = true)
	private Integer id;

	/** 留言内容 */
	@Column(name = "talkdesc", type = "string")
	private String talkDesc;

	/** 创建日期 */
	@Column(name = "crdate", type = "string")
	private String crDate;

	/** 创建时间 */
	@Column(name = "crtime", type = "string")
	private String crTime;

	/** 创建人ID */
	@Column(name = "crusr", type = "int")
	private Integer crUsr;

	/** 创建人Name */
	@Column(name = "usrname", type = "string")
	private String usrName;

	/** 主题ID */
	@Column(name = "blogid", type = "int")
	private Integer blogId;

	/** 点赞 */
	@Column(name = "yescount", type = "int")
	private Integer yesCount;

	/** 吐槽 */
	@Column(name = "nocount", type = "int")
	private Integer noCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTalkDesc() {
		return talkDesc;
	}

	public void setTalkDesc(String talkDesc) {
		this.talkDesc = talkDesc;
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

	public Integer getCrUsr() {
		return crUsr;
	}

	public void setCrUsr(Integer crUsr) {
		this.crUsr = crUsr;
	}

	public Integer getBlogId() {
		return blogId;
	}

	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public Integer getYesCount() {
		return yesCount;
	}

	public void setYesCount(Integer yesCount) {
		this.yesCount = yesCount;
	}

	public Integer getNoCount() {
		return noCount;
	}

	public void setNoCount(Integer noCount) {
		this.noCount = noCount;
	}

	@Override
	public String toString() {
		return "DrTalk [id=" + id + ", talkDesc=" + talkDesc + ", crDate=" + crDate + ", crTime=" + crTime + ", crUsr=" + crUsr + ", usrName=" + usrName + ", blogId=" + blogId + ", yesCount="
				+ yesCount + ", noCount=" + noCount + "]";
	}

}
