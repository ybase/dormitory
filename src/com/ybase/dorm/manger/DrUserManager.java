package com.ybase.dorm.manger;

import org.apache.log4j.Logger;

import com.ybase.dorm.DormException;
import com.ybase.dorm.vo.DrUser;

/**
 * �û�Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014��5��24��
 */
public interface DrUserManager {

	public static final Logger log = Logger.getLogger(DrUserManager.class.getName());

	/*
	 * �����û�<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 */
	public boolean addDrUser(DrUser drUser);

	/*
	 * ����ID �����û���Ϣ<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param id
	 */
	public DrUser queryDrUserById(Integer id);

	/*
	 * ����ID �����û�״̬<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param id
	 */
	public boolean updateStatus(Integer id, Integer status) throws DormException;

	/*
	 * ����ID �����û����ʴ���<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param id
	 */
	public boolean updateVisit(Integer id) throws DormException;

	/*
	 * ����ID �����û���Ϣ���ʴ��������������ں�ʱ��<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param id
	 */
	public boolean updateVDTS(Integer id) throws DormException;

	/*
	 * ����email ����USER<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param email
	 */
	public DrUser queryUsrByEmail(String email) throws DormException;

	/**
	 * �޸�����<br/>
	 * 
	 * @param id
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��29��
	 * @param newPass
	 * @return
	 */
	public boolean updatePass(Integer id, String newPass) throws DormException;

}
