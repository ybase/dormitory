package com.ybase.dorm.manger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ybase.dorm.DormException;
import com.ybase.dorm.annotation.Service;
import com.ybase.dorm.bas.BasDAOHolder;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.DrRecordManager;
import com.ybase.dorm.manger.DrTopManager;
import com.ybase.dorm.vo.DrRecord;
import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;

public class DrTopManagerImpl extends BasDAOHolder implements DrTopManager {

	@Service("recordManager")
	private DrRecordManager recordDAO;

	@Override
	public boolean addDrTop(DrTop top) {
		boolean flag = false;
		try {
			top = addVO(top);
			if (top.getId() != 0) {
				// 插入TOP 成功
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public synchronized boolean updateY(Integer id, DrUser usr) {
		boolean flag = false;
		try {
			if (usr == null) {
				throw new DormException("updateY 参数[usr] 为空!");
			}

			if (id == null) {
				throw new Exception("updateY 参数[id] 为空!");
			}

			Map<String, Object> updateProp = new HashMap<String, Object>();
			updateProp.put("yesCount", "yescount+1");
			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("id_Eq", new Object[] { id, SQL_AND });
			int rows = update(DrTop.class, updateProp, conProp);
			if (rows > 0) {
				if (!recordDAO.existRecordByUsrDtTp(usr.getId(), DormUtil.getDate8Str(), Integer.valueOf(DormConstant.DR_RECORD_TYPE_3), id)) {
					DrRecord record = new DrRecord();
					record.setRelId(id);
					record.setCrDate(DormUtil.getDate8Str());
					record.setCrTime(DormUtil.getTime9Str());
					record.setDrType(Integer.valueOf(DormConstant.DR_RECORD_TYPE_3));
					record.setUsrName(usr.getName());
					record.setCrUsr(usr.getId());
					if (recordDAO.addDrRecord(record)) {
						flag = true;
					}
				} else {
					log.debug(String.format("当前用户[%s]已点赞过当前争霸赛[%s]", usr.getId(), id));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public synchronized boolean updateN(Integer id, DrUser usr) {
		boolean flag = false;
		try {
			if (usr == null) {
				throw new DormException("updateN 参数[usr] 为空!");
			}

			if (id == null) {
				throw new Exception("updateN 参数[id] 为空!");
			}

			Map<String, Object> updateProp = new HashMap<String, Object>();
			updateProp.put("noCount", "nocount+1");
			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("id_Eq", new Object[] { id, SQL_AND });
			int rows = update(DrTop.class, updateProp, conProp);
			if (rows > 0) {
				if (!recordDAO.existRecordByUsrDtTp(usr.getId(), DormUtil.getDate8Str(), Integer.valueOf(DormConstant.DR_RECORD_TYPE_3), id)) {
					DrRecord record = new DrRecord();
					record.setRelId(id);
					record.setCrDate(DormUtil.getDate8Str());
					record.setCrTime(DormUtil.getTime9Str());
					record.setDrType(Integer.valueOf(DormConstant.DR_RECORD_TYPE_3));
					record.setUsrName(usr.getName());
					record.setCrUsr(usr.getId());
					if (recordDAO.addDrRecord(record)) {
						flag = true;
					}
				} else {
					log.debug(String.format("当前用户[%s]已点踩过当前争霸赛[%s]", usr.getId(), id));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public DrTop queryRandomTop(int i) {
		if (i > 0) {
			try {
				Map<String, Object> conProp = new HashMap<String, Object>();
				conProp.put("id_Eq", new Object[] { i, SQL_AND });
				List<DrTop> list = executeQuery(DrTop.class, conProp);
				return DormUtil.getOne(list);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}

	@Override
	public List<Object> queryIds() {
		List<Object> list = null;
		try {
			list = executeQuery(DrTop.class, null, "id");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

}
