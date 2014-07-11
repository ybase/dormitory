package com.ybase.dorm.manger;

import java.util.List;

import org.apache.log4j.Logger;

import com.ybase.dorm.DormException;
import com.ybase.dorm.vo.DrPlan;

/**
 * 计划Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014年5月26日
 */
public interface DrPlanManager {

	public static final Logger log = Logger.getLogger(DrPlanManager.class.getName());

	/**
	 * 增加计划<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014-7-2
	 * 
	 * @param drPlan
	 */
	public boolean addPlan(DrPlan drPlan);

	/**
	 * 查询前top 条计划<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014-7-2
	 * @param top
	 * @return
	 */
	public List<DrPlan> queryPlanTopx(int top) throws DormException;

	/**
	 * 根据id查询计划<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014-7-2
	 * @param id
	 * @return
	 */
	public DrPlan queryPlan(Integer id) throws DormException;

}
