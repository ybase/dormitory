package com.ybase.dorm.vo;

import com.ybase.dorm.annotation.Column;
import com.ybase.dorm.annotation.Table;

/**
 * 趣味点赞<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 */
@Table("dr_top")
public class DrTop extends DormVO {

	/** 编号 */
	@Column(name = "id", type = "int", key = true)
	private Integer id;

	/** 趣味称呼 */
	@Column(name = "name", type = "string")
	private String name;

	/** 趣味描述 */
	@Column(name = "topdesc", type = "string")
	private String topDesc;

	/** 创建日期 */
	@Column(name = "crdate", type = "string")
	private String crDate;

	/** 创建时间 */
	@Column(name = "crtime", type = "string")
	private String crTime;

	/** 创建人ID */
	@Column(name = "crusr", type = "int")
	private Integer crUsr;
	
	/** 点赞次数 */
	@Column(name = "yescount", type = "int")
	private Integer yesCount;

	/** 吐槽次数 */
	@Column(name = "nocount", type = "int")
	private Integer noCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTopDesc() {
		return topDesc;
	}

	public void setTopDesc(String topDesc) {
		this.topDesc = topDesc;
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
		return "DrTop [id=" + id + ", name=" + name + ", topDesc=" + topDesc + ", crDate=" + crDate + ", crTime=" + crTime + ", crUsr=" + crUsr + ", yesCount=" + yesCount + ", noCount=" + noCount
				+ "]";
	}

}
