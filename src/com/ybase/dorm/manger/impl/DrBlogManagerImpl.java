package com.ybase.dorm.manger.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ybase.dorm.DormException;
import com.ybase.dorm.annotation.Service;
import com.ybase.dorm.bas.BasDAOHolder;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.DrBlogManager;
import com.ybase.dorm.manger.DrRecordManager;
import com.ybase.dorm.manger.DrTalkManager;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrRecord;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.DrUser;
import com.ybase.dorm.vo.Page;

public class DrBlogManagerImpl extends BasDAOHolder implements DrBlogManager {

	@Service("recordManager")
	private DrRecordManager recordDAO;

	@Service("blogManager")
	private DrTalkManager talkDAO;

	@Override
	public boolean addDrBlog(DrBlog blog) throws DormException {
		boolean flag = false;
		try {
			if (DormUtil.isNullOrEmpty(blog.getTheme())) {
				throw new DormException("addDrBlog 字段theme为空!");
			}

			blog = addVO(blog);
			if (blog != null) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	@Override
	public DrBlog queryDrBlogById(Integer id) throws DormException {
		if (id != null) {
			Map<String, Object> par = new HashMap<String, Object>();
			par.put("id_Eq", id);
			List<DrBlog> list;
			try {
				list = executeQuery(DrBlog.class, par);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new DormException(e.getMessage());
			}
			return DormUtil.getOne(list);
		} else {
			throw new DormException("参数id为空!");
		}
	}

	@Override
	public boolean updateYesCount(Integer id, DrUser usr) throws DormException {
		boolean flag = false;

		if (usr == null) {
			throw new DormException("updateYesCount 参数[usr] 为空!");
		}

		if (id == null) {
			throw new DormException("updateYesCount 参数[id] 为空!");
		}

		try {
			Map<String, Object> updateProps = new HashMap<String, Object>();
			updateProps.put("yesCount", "yescount+1");
			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("id_Eq", id);
			int row = update(DrBlog.class, updateProps, conProp);
			if (row > 0) {
				if (!recordDAO.existRecordByUsrDtTp(usr.getId(), DormUtil.getDate8Str(), Integer.valueOf(DormConstant.DR_RECORD_TYPE_0), id)) {
					DrRecord record = new DrRecord();
					record.setRelId(id);
					record.setCrDate(DormUtil.getDate8Str());
					record.setCrTime(DormUtil.getTime9Str());
					record.setDrType(Integer.valueOf(DormConstant.DR_RECORD_TYPE_0));
					record.setUsrName(usr.getName());
					record.setCrUsr(usr.getId());
					if (recordDAO.addDrRecord(record)) {
						flag = true;
					}
				} else {
					log.debug(String.format("当前用户[%s]已点赞过当前主题[%s]", usr.getId(), id));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new DormException(e.getMessage());
		}

		return flag;
	}

	@Override
	public Map<String, Object> queryBlogByImageId(Integer imageId) throws DormException {
		try {
			if (imageId == null) {
				throw new DormException("参数[imageId] 为空!");
			}

			List<Map<String, Object>> list = execXmlSqlQuery("selectBlogAndImage", null, imageId);
			if (list.size() > 1) {
				throw new DormException("参数[imageId=" + imageId + "]返回不止一条数据，实际返回:" + list.size() + " 条数据");
			}

			return list != null && list.size() == 1 ? list.get(0) : null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new DormException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryTopFiveBlog() {
		try {
			List<Map<String, Object>> rst = execXmlSqlQuery("selectBlogAndImageT5", null);
			if (rst != null && rst.size() > 0) {
				return rst;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	private int countAllBlog() {
		try {
			return executeCountQuery(DrBlog.class, null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public Map<String, Object> pageAllBlogWrapTalk(Page page) throws DormException {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		try {
			if (page == null) {
				throw new DormException("Page 参数为空!");
			}
			page.setPageRecord(1);
			page.setTotalRecord(countAllBlog());
			page.initPage();

			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", SQL_SORT_DESC);
			sort.put("crDate", SQL_SORT_DESC);// 反序
			List<DrBlog> blogs = executeQuery(DrBlog.class, null, page, sort);

			if (!DormUtil.isNullOrEmpty(blogs)) {
				Iterator<DrBlog> iter = blogs.iterator();
				int count = 1;
				while (iter.hasNext()) {
					DrBlog blog = iter.next();
					Map<String, Object> arg = new HashMap<String, Object>();
					arg.put("blog", blog);
					List<DrTalk> talks = talkDAO.queryTop20DrTalkByBlogId(blog.getId());
					arg.put("talk", talks);
					rsMap.put("rs" + count, arg);
					count++;
				}
			}
			rsMap.put("page", page);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rsMap;
	}

	@Override
	public Map<String, Object> pageAllBlog(Page page) throws DormException {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		try {
			if (page == null) {
				throw new DormException("Page 参数为空!");
			}
			page.setPageRecord(1);
			page.setTotalRecord(countAllBlog());
			page.initPage();

			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", SQL_SORT_DESC);
			sort.put("crDate", SQL_SORT_DESC);
			List<DrBlog> blogs = executeQuery(DrBlog.class, null, page, sort);

			rsMap.put("result", blogs);
			rsMap.put("page", page);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rsMap;
	}

}
