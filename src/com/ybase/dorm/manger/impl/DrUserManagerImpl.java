package com.ybase.dorm.manger.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.BasDAOHolder;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.bas.MD5EncrytUtil;
import com.ybase.dorm.manger.DrUserManager;
import com.ybase.dorm.vo.DrUser;

public class DrUserManagerImpl extends BasDAOHolder implements DrUserManager {

	@Override
	public boolean addDrUser(DrUser drUser) {
		boolean flag = false;
		try {
			drUser.setPasswd(MD5EncrytUtil.md5Encrypt(drUser.getPasswd()));
			drUser = addVO(drUser);
			if (drUser.getId() != 0) {
				// 插入USER 成功
				return flag;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public DrUser queryDrUserById(Integer id) {
		try {
			if (id != null) {
				Map<String, Object> prop = new HashMap<String, Object>();
				prop.put("id_Eq", new Object[] { id, SQL_AND });
				return DormUtil.getOne(executeQuery(DrUser.class, prop));
			} else {
				return DormUtil.getOne(executeQuery(DrUser.class, null));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public synchronized boolean updateStatus(Integer id, Integer status) throws DormException {
		boolean flag = false;

		if (!Arrays.asList(DormConstant.DR_USER_STATU_ARR).contains(String.valueOf(status))) {
			throw new DormException("DR_USER.STATUS 错误!");
		}

		try {
			if (id == null) {
				throw new DormException("updateStatus 参数[id] 为空!");
			}

			Map<String, Object> update = new HashMap<String, Object>();
			update.put("status", status);
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("id_Eq", new Object[] { id, SQL_AND });
			int rows = update(DrUser.class, update, con);
			if (rows > 0) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public synchronized boolean updateVisit(Integer id) throws DormException {
		boolean flag = false;
		try {
			if (id == null) {
				throw new DormException("updateVisit 参数[id] 为空!");
			}

			Map<String, Object> update = new HashMap<String, Object>();
			update.put("visit", "visit+1");
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("id_Eq", new Object[] { id, SQL_AND });
			int rows = update(DrUser.class, update, con);
			if (rows > 0) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public synchronized boolean updateVDTS(Integer id) throws DormException {
		boolean flag = false;
		try {
			if (id == null) {
				throw new DormException("updateVDT 参数[id] 为空!");
			}

			Map<String, Object> update = new HashMap<String, Object>();
			update.put("visit", "visit+1");
			update.put("loginDate", DormUtil.getDate8Str());
			update.put("loginTime", DormUtil.getTime9Str());
			update.put("status", Integer.valueOf(DormConstant.DR_USER_STATUS_1));
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("id_Eq", new Object[] { id, SQL_AND });
			int rows = update(DrUser.class, update, con);
			if (rows > 0) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public DrUser queryUsrByEmail(String email) throws DormException {
		try {
			if (DormUtil.isNullOrEmpty(email)) {
				throw new DormException("queryUsrByEmail 参数[email]为空!");
			}

			Map<String, Object> con = new HashMap<String, Object>();
			con.put("email_Eq", new Object[] { email, SQL_AND });
			List<DrUser> usrs = executeQuery(DrUser.class, con);
			if (usrs != null && usrs.size() > 1) {
				throw new DormException("queryUsrByEmail 参数[email]=" + email + ",查询结果不止一条!");
			}
			return DormUtil.getOne(usrs);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public synchronized boolean updatePass(Integer id, String newPass) throws DormException {
		boolean flag = false;
		try {
			if (id == null) {
				throw new DormException("updatePass 参数[id] 为空!");
			}

			Map<String, Object> update = new HashMap<String, Object>();
			update.put("passwd", MD5EncrytUtil.md5Encrypt(newPass));
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("id_Eq", new Object[] { id, SQL_AND });

			int rows = update(DrUser.class, update, con);
			if (rows > 0) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}
}
