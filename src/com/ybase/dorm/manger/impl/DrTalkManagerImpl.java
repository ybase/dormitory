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
import com.ybase.dorm.manger.DrTalkManager;
import com.ybase.dorm.vo.DrRecord;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.DrUser;

public class DrTalkManagerImpl extends BasDAOHolder implements DrTalkManager {

	@Service("recordManager")
	private DrRecordManager recordDAO;

	@Override
	public boolean addTalk(DrTalk talk) {
		boolean flag = false;
		try {
			talk = addVO(talk);
			if (talk.getId() != null) {
				// 插入Talk 成功
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public List<DrTalk> queryListDrTalkByBlogId(Integer blogId) throws DormException {
		try {
			if (blogId == null) {
				throw new DormException("queryListDrTalkByBlogId 参数[blogId] 为空!");
			}

			Map<String, Object> con = new HashMap<String, Object>();
			con.put("blogId_Eq", new Object[] { blogId, SQL_AND });

			Map<String, String> sort = new HashMap<String, String>();
			sort.put("crTime", SQL_SORT_DESC);
			sort.put("crDate", SQL_SORT_DESC);
			return executeQuery(DrTalk.class, con, sort);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<DrTalk> queryTop20DrTalkByBlogId(Integer blogId) throws DormException {
		try {
			if (blogId == null) {
				throw new DormException("queryTop20DrTalkByBlogId 参数[blogId] 为空!");
			}

			Map<String, Object> con = new HashMap<String, Object>();
			con.put("blogId_Eq", new Object[] { blogId, SQL_AND });

			Map<String, String> sort = new HashMap<String, String>();
			sort.put("crTime", SQL_SORT_DESC);
			sort.put("crDate", SQL_SORT_DESC);
			return executeQuery(DrTalk.class, con, sort, 20);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
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

			int rows = update(DrTalk.class, updateProp, conProp);
			if (rows > 0) {
				if (!recordDAO.existRecordByUsrDtTp(usr.getId(), DormUtil.getDate8Str(), Integer.valueOf(DormConstant.DR_RECORD_TYPE_2), id)) {
					DrRecord record = new DrRecord();
					record.setRelId(id);
					record.setCrDate(DormUtil.getDate8Str());
					record.setCrTime(DormUtil.getTime9Str());
					record.setDrType(Integer.valueOf(DormConstant.DR_RECORD_TYPE_2));
					record.setUsrName(usr.getName());
					record.setCrUsr(usr.getId());
					if (recordDAO.addDrRecord(record)) {
						flag = true;
					}
				} else {
					log.debug(String.format("当前用户[%s]已点赞过当前评论[%s]", usr.getId(), id));
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
				throw new DormException("updateN 参数[id] 为空!");
			}

			Map<String, Object> updateProp = new HashMap<String, Object>();
			updateProp.put("noCount", "nocount+1");

			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("id_Eq", new Object[] { id, SQL_AND });

			int rows = update(DrTalk.class, updateProp, conProp);
			if (rows > 0) {
				if (!recordDAO.existRecordByUsrDtTp(usr.getId(), DormUtil.getDate8Str(), Integer.valueOf(DormConstant.DR_RECORD_TYPE_2), id)) {
					DrRecord record = new DrRecord();
					record.setRelId(id);
					record.setCrDate(DormUtil.getDate8Str());
					record.setCrTime(DormUtil.getTime9Str());
					record.setDrType(Integer.valueOf(DormConstant.DR_RECORD_TYPE_2));
					record.setUsrName(usr.getName());
					record.setCrUsr(usr.getId());
					if (recordDAO.addDrRecord(record)) {
						flag = true;
					}
				} else {
					log.debug(String.format("当前用户[%s]已吐槽过当前评论[%s]", usr.getId(), id));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public DrTalk queryTalkById(Integer id) throws DormException {
		try {
			if (id == null) {
				throw new DormException("queryTalkById 参数id为空!");
			}
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("id_Eq", id);

			List<DrTalk> list = executeQuery(DrTalk.class, con);
			return DormUtil.getOne(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
