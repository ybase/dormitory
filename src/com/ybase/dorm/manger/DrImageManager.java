package com.ybase.dorm.manger;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ybase.dorm.DormException;
import com.ybase.dorm.vo.DrImage;
import com.ybase.dorm.vo.DrUser;
import com.ybase.dorm.vo.Page;

/**
 * 图片Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014年5月24日
 */
public interface DrImageManager {

	public static final Logger log = Logger.getLogger(DrImageManager.class.getName());

	/**
	 * 增加图片<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日<br/>
	 */
	public boolean addDrImage(DrImage image) throws DormException;

	/**
	 * 根据ID 查找图片信息<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日<br/>
	 * 
	 * @param id
	 */
	public DrImage queryDrImageById(Integer id) throws DormException;

	/**
	 * 查找图片信息<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日<br/>
	 * 
	 * @param id
	 */
	public List<DrImage> queryAllDrImage();

	/**
	 * 分页查找图片信息<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月24日<br/>
	 * 
	 * @param page
	 */
	public Map<String, Object> pageAllDrImage(Page page) throws DormException;

	/**
	 * 查找前四条首页照片信息<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月26日<br/>
	 */
	public List<DrImage> queryIndex4DrImage();

	/**
	 * 查找前10条经典照片信息<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月26日<br/>
	 */
	public List<DrImage> queryClassic10DrImage();

	/**
	 * 查找前10条照片信息,根据上传时间排序<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月26日<br/>
	 * 
	 * @param x
	 */
	public List<DrImage> queryTopXDrImage(int x);

	/**
	 * 点赞图片<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 2014年5月28日<br/>
	 * @param id
	 */
	public boolean updateY(Integer id, DrUser usr) throws DormException;

}
