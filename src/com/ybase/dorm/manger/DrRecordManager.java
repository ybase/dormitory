package com.ybase.dorm.manger;

import java.util.List;

import org.apache.log4j.Logger;

import com.ybase.dorm.DormException;
import com.ybase.dorm.vo.DrRecord;

/**
 * Record Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014年6月4日
 */
public interface DrRecordManager {

	public static final Logger log = Logger.getLogger(DrRecordManager.class.getName());

	/**
	 * 增加点赞/吐槽记录<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年6月4日
	 */
	public boolean addDrRecord(DrRecord record);

	/**
	 * 根据用户ID、日期、范围，查找点赞/吐槽一条记录<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年6月4日<br/>
	 * @param crusr
	 * @param date
	 * @param type
	 * @param relId
	 */
	public DrRecord queryRecordByUsrDtTp(Integer crusr, String date, Integer type, Integer relId) throws DormException;

	/**
	 * 根据用户ID、日期、范围，查找点赞/吐槽记录列表<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年6月4日<br/>
	 * @param crusr
	 * @param date
	 * @param type
	 * @param relId
	 */
	public List<DrRecord> queryListRecordByUsrDtTp(Integer crusr, String date, Integer type, Integer relId) throws DormException;

	/**
	 * 根据用户ID、日期、范围，判断点赞/吐槽记录是否存在<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年6月4日<br/>
	 * @param crusr
	 * @param date
	 * @param type
	 * @param relId
	 */
	public boolean existRecordByUsrDtTp(Integer crusr, String date, Integer type, Integer relId) throws DormException;

}
