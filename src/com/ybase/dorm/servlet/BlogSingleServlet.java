package com.ybase.dorm.servlet;

import java.util.List;
import java.util.Map;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.AbstractCommonDispatch;
import com.ybase.dorm.bas.DormErrCode;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrBlogManager;
import com.ybase.dorm.manger.DrTalkManager;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.Page;

/**
 * 讨论2<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 * 
 */
public class BlogSingleServlet extends AbstractCommonDispatch {

	private static final long serialVersionUID = 1L;

	private DrBlog convertMap(Map<String, Object> map) {
		DrBlog blog = new DrBlog();
		blog.setId((Integer) map.get("id"));
		blog.setBlogDesc((String) map.get("blogdesc"));
		blog.setCrDate((String) map.get("crdate"));
		blog.setCrTime((String) map.get("crtime"));
		blog.setCrUsr((Integer) map.get("crusr"));
		blog.setUsrName((String) map.get("usrname"));
		blog.setImgId((Integer) map.get("imgid"));
		blog.setPicPath((String) map.get("picpath"));
		blog.setTheme((String) map.get("theme"));
		blog.setYesCount((Integer) map.get("yescount"));
		return blog;
	}

	@Override
	public void process() throws Exception {
		DrBlogManager blogDAO = ServiceFactory.getBlogManger();
		DrTalkManager talkDAO = ServiceFactory.getTalkManager();

		try {
			String imageId = getPar("imageId");
			if (DormUtil.isNullOrEmpty(imageId)) {
				log.error("前台参数 imageId 为空!");
				throw new DormException(DormErrCode.D0009);
			}

			Map<String, Object> map = blogDAO.queryBlogByImageId(Integer.valueOf(imageId));
			if (map == null) {
				log.error(String.format("根据imageId[%s]参数查找DrBlog 结果为空", imageId));
				throw new DormException(DormErrCode.D0002);
			}

			DrBlog drBlog = convertMap(map);
			setAttr(SCOPE_REQ, "blog", drBlog);

			List<Map<String, Object>> fiveMap = blogDAO.queryTopFiveBlog();
			setAttr(SCOPE_REQ, "fiveMap", fiveMap);

			List<DrTalk> talk = talkDAO.queryListDrTalkByBlogId(drBlog.getId());
			setAttr(SCOPE_REQ, "talk", talk);

			Page page = new Page();
			page.setCurrent(1);
			page.setTotalRecord(1);
			page.setPageRecord(1);
			page.initPage();
			setAttr(SCOPE_REQ, "page", page);
			setDUrl("blogDorm.jsp");
		} catch (DormException e) {
			setReqAttr("status", 1);
			log.error(e.getMessage(), e);
			String url = getPar("redirectURL");
			if (url == null || DormUtil.isNullOrEmpty(url)) {
				setDOwnUrl();
			} else {
				setDUrl(url);
			}
		}
	}

}
