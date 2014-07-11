package com.ybase.dorm.manger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.BasDAOHolder;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.DrPlanManager;
import com.ybase.dorm.vo.DrPlan;

public class DrPlanManagerImpl extends BasDAOHolder implements DrPlanManager {

	@Override
	public boolean addPlan(DrPlan plan) {
		boolean flag = false;
		try {
			plan = addVO(plan);
			if (plan.getId() != null) {
				// 插入Talk 成功
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public List<DrPlan> queryPlanTopx(int x) throws DormException {
		try {
			Map<String, String> sort = new HashMap<String, String>();
			sort.put("crTime", SQL_SORT_DESC);
			sort.put("crDate", SQL_SORT_DESC);

			return executeQuery(DrPlan.class, null, sort, x);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new DormException(e);
		}
	}

	@Override
	public DrPlan queryPlan(Integer id) throws DormException {
		try {
			if (id == null) {
				throw new DormException("queryPlan 参数id为空!");
			}
			Map<String, Object> con = new HashMap<String, Object>();
			con.put("id_Eq", id);

			List<DrPlan> list = executeQuery(DrPlan.class, con);
			return DormUtil.getOne(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new DormException(e);
		}
	}

}
