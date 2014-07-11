package com.ybase.dorm.vo;

import com.ybase.dorm.annotation.Column;
import com.ybase.dorm.annotation.Table;

/**
 * 点赞/吐槽记录<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 */
@Table("dr_record")
public class DrRecord extends DormVO {

	/** 编号 */
	@Column(name = "id", type = "int", key = true)
	private Integer id;

	/** 点赞范围 0-BLOG 1-IMAGE 2-TALK 3-TOP */
	@Column(name = "drtype", type = "int")
	private Integer drType;

	/** 关联Id */
	@Column(name = "relid", type = "int")
	private Integer relId;

	/** 点赞日期 */
	@Column(name = "crdate", type = "string")
	private String crDate;

	/** 点赞时间 */
	@Column(name = "crtime", type = "string")
	private String crTime;

	/** 点赞用户ID */
	@Column(name = "crusr", type = "int")
	private Integer crUsr;

	/** 点赞用户名称 */
	@Column(name = "usrname", type = "string")
	private String usrName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDrType() {
		return drType;
	}

	public void setDrType(Integer drType) {
		this.drType = drType;
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

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public Integer getRelId() {
		return relId;
	}

	public void setRelId(Integer relId) {
		this.relId = relId;
	}

	@Override
	public String toString() {
		return "DrRecord [id=" + id + ", drType=" + drType + ", relId=" + relId + ", crDate=" + crDate + ", crTime=" + crTime + ", crUsr=" + crUsr + ", usrName=" + usrName + "]";
	}

}
