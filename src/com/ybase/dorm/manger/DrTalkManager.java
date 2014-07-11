package com.ybase.dorm.manger;

import java.util.List;

import org.apache.log4j.Logger;

import com.ybase.dorm.DormException;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.DrUser;

/**
 * 留言Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014年5月26日
 */
public interface DrTalkManager {
	
	public static final Logger log = Logger.getLogger(DrTalkManager.class.getName());

	/**
	 * 增加留言<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月26日
	 * 
	 * @param drTalk
	 */
	public boolean addTalk(DrTalk drTalk);

	/**
	 * 根据blog id 查找留言<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月26日<br/>
	 * 
	 * @param blogId
	 */
	public List<DrTalk> queryListDrTalkByBlogId(Integer blogId) throws DormException;

	/**
	 * 根据blog id 查找前20条留言<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月26日<br/>
	 * 
	 * @param id
	 */
	public List<DrTalk> queryTop20DrTalkByBlogId(Integer blogId) throws DormException;

	/**
	 * 根据id 点赞<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月29日<br/>
	 * @param id
	 * @param usr
	 * @return
	 */
	public boolean updateY(Integer id, DrUser usr);

	/**
	 * 根据id 踩<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月29日<br/>
	 * @param id
	 * @param usr
	 * @return
	 */
	public boolean updateN(Integer id, DrUser usr);

	/**
	 * 根据id 查找Talk<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月29日<br/>
	 * @param id
	 * @return
	 */
	public DrTalk queryTalkById(Integer id) throws DormException;

}
