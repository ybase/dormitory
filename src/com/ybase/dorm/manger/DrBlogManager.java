package com.ybase.dorm.manger;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ybase.dorm.DormException;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrUser;
import com.ybase.dorm.vo.Page;

/**
 * ����Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014��5��24��
 */
public interface DrBlogManager {

	public static Logger log = Logger.getLogger(DrBlogManager.class.getName());

	/*
	 * ��������<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param blog
	 */
	public boolean addDrBlog(DrBlog blog) throws DormException;

	/*
	 * ����ID ����������Ϣ<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param id
	 */
	public DrBlog queryDrBlogById(Integer id) throws DormException;

	/*
	 * ����ID �����������<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��24��
	 * 
	 * @param id
	 * 
	 * @param usr
	 */
	public boolean updateYesCount(Integer id, DrUser usr) throws DormException;

	/*
	 * ����ͼƬID ����BLOG<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��26��<br/>
	 * 
	 * @param imageId
	 */
	public Map<String, Object> queryBlogByImageId(Integer imageId) throws DormException;

	/*
	 * �ҳ������ŵ�5��BLOG<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��26��<br/>
	 */
	public List<Map<String, Object>> queryTopFiveBlog();

	/*
	 * ��ҳBLOG ����ʱ������talk �б�<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��25��<br/>
	 */
	public Map<String, Object> pageAllBlogWrapTalk(Page page) throws DormException;

	/*
	 * ��ҳBLOG <br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014��5��25��<br/>
	 */
	public Map<String, Object> pageAllBlog(Page page) throws DormException;

}
