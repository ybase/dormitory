package com.ybase.dorm.manger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.BasDAOHolder;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.DrRecordManager;
import com.ybase.dorm.vo.DrRecord;

public class DrRecordManagerImpl extends BasDAOHolder implements DrRecordManager {

	@Override
	public boolean addDrRecord(DrRecord record) {
		boolean flag = false;
		try {
			record = addVO(record);
			if (record.getId() != null) {
				// 插入RECORD 成功
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public DrRecord queryRecordByUsrDtTp(Integer crusr, String date, Integer type, Integer relId) throws DormException {
		try {
			if (crusr == null || DormUtil.isNullOrEmpty(date) || !DormUtil.checkDrRecordTp(type) || relId == null) {
				throw new DormException("queryRecordByUsrDtTp 四个参数之一可能为空，或者不合法");
			}

			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("crUsr_Eq", new Object[] { crusr, SQL_AND });
			conProp.put("crDate_Eq", new Object[] { date, SQL_AND });
			conProp.put("drType_Eq", new Object[] { type, SQL_AND });
			conProp.put("relId_Eq", new Object[] { relId, SQL_AND });
			List<DrRecord> list = executeQuery(DrRecord.class, conProp);
			return DormUtil.getOne(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<DrRecord> queryListRecordByUsrDtTp(Integer crusr, String date, Integer type, Integer relId) throws DormException {
		try {
			if (crusr == null || DormUtil.isNullOrEmpty(date) || !DormUtil.checkDrRecordTp(type) || relId == null) {
				throw new DormException("queryRecordByUsrDtTp 四个参数之一可能为空，或者不合法");
			}

			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("crUsr_Eq", new Object[] { crusr, SQL_AND });
			conProp.put("crDate_Eq", new Object[] { date, SQL_AND });
			conProp.put("drType_Eq", new Object[] { type, SQL_AND });
			conProp.put("relId_Eq", new Object[] { relId, SQL_AND });
			return executeQuery(DrRecord.class, conProp);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public boolean existRecordByUsrDtTp(Integer crusr, String date, Integer type, Integer relId) throws DormException {
		try {
			if (crusr != null && !DormUtil.isNullOrEmpty(date) && DormUtil.checkDrRecordTp(type) && relId != null) {
				Map<String, Object> props = new HashMap<String, Object>();
				props.put("crUsr_Eq", new Object[] { crusr, SQL_AND });
				props.put("crDate_Eq", new Object[] { date, SQL_AND });
				props.put("drType_Eq", new Object[] { type, SQL_AND });
				props.put("relId_Eq", new Object[] { relId, SQL_AND });
				List<DrRecord> list = executeQuery(DrRecord.class, props);
				if (list != null && list.size() > 0) {
					return true;
				}
			} else {
				throw new DormException("四个参数之一可能为空，或者不合法");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

}
