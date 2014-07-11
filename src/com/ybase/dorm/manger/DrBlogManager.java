package com.ybase.dorm.manger;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ybase.dorm.DormException;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrUser;
import com.ybase.dorm.vo.Page;

/**
 * 主题Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014年5月24日
 */
public interface DrBlogManager {

	public static Logger log = Logger.getLogger(DrBlogManager.class.getName());

	/*
	 * 增加主题<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日
	 * 
	 * @param blog
	 */
	public boolean addDrBlog(DrBlog blog) throws DormException;

	/*
	 * 根据ID 查找主题信息<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日
	 * 
	 * @param id
	 */
	public DrBlog queryDrBlogById(Integer id) throws DormException;

	/*
	 * 根据ID 更新主题点赞<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日
	 * 
	 * @param id
	 * 
	 * @param usr
	 */
	public boolean updateYesCount(Integer id, DrUser usr) throws DormException;

	/*
	 * 根据图片ID 查找BLOG<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月26日<br/>
	 * 
	 * @param imageId
	 */
	public Map<String, Object> queryBlogByImageId(Integer imageId) throws DormException;

	/*
	 * 找出最热门的5个BLOG<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月26日<br/>
	 */
	public List<Map<String, Object>> queryTopFiveBlog();

	/*
	 * 分页BLOG 返回时，附带talk 列表<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月25日<br/>
	 */
	public Map<String, Object> pageAllBlogWrapTalk(Page page) throws DormException;

	/*
	 * 分页BLOG <br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月25日<br/>
	 */
	public Map<String, Object> pageAllBlog(Page page) throws DormException;

}
