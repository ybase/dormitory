package com.ybase.dorm.manger;

import org.apache.log4j.Logger;

import com.ybase.dorm.DormException;
import com.ybase.dorm.vo.DrUser;

/**
 * 用户Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014年5月24日
 */
public interface DrUserManager {

	public static final Logger log = Logger.getLogger(DrUserManager.class.getName());

	/*
	 * 增加用户<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日
	 */
	public boolean addDrUser(DrUser drUser);

	/*
	 * 根据ID 查找用户信息<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日
	 * 
	 * @param id
	 */
	public DrUser queryDrUserById(Integer id);

	/*
	 * 根据ID 更新用户状态<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日
	 * 
	 * @param id
	 */
	public boolean updateStatus(Integer id, Integer status) throws DormException;

	/*
	 * 根据ID 更新用户访问次数<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日
	 * 
	 * @param id
	 */
	public boolean updateVisit(Integer id) throws DormException;

	/*
	 * 根据ID 更新用户信息访问次数、最后访问日期和时间<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日
	 * 
	 * @param id
	 */
	public boolean updateVDTS(Integer id) throws DormException;

	/*
	 * 根据email 查找USER<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日
	 * 
	 * @param email
	 */
	public DrUser queryUsrByEmail(String email) throws DormException;

	/**
	 * 修改密码<br/>
	 * 
	 * @param id
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月29日
	 * @param newPass
	 * @return
	 */
	public boolean updatePass(Integer id, String newPass) throws DormException;

}
