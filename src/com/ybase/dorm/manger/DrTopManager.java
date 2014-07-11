package com.ybase.dorm.manger;

import java.util.List;

import org.apache.log4j.Logger;

import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;

/**
 * TOP Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014��5��24��
 */
public interface DrTopManager {
	public static final Logger log = Logger.getLogger(DrTopManager.class.getName());

	/**
	 * ����Top<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 */
	public boolean addDrTop(DrTop top);

	/**
	 * ����top id ���µ���<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��26��<br/>
	 * @param id
	 * @param usr
	 */
	public boolean updateY(Integer id, DrUser usr);

	/**
	 * ����top id �����²�<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��26��<br/>
	 * @param id
	 * @param usr
	 */
	public boolean updateN(Integer id, DrUser usr);

	/**
	 * �����ȡtop<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��27��<br/>
	 * @param i
	 * @return
	 */
	public DrTop queryRandomTop(int i);

	/**
	 * ��ȡtop ID<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��27��<br/>
	 * @return
	 */
	public List<Object> queryIds();

}
