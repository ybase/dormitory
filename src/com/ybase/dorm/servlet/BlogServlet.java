package com.ybase.dorm.servlet;

import java.util.List;
import java.util.Map;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.AbstractCommonDispatch;
import com.ybase.dorm.bas.DormErrCode;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrBlogManager;
import com.ybase.dorm.service.CommonService;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.Page;

/**
 * Ã÷¬€<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 * 
 */
public class BlogServlet extends AbstractCommonDispatch {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void process() throws Exception {
		try {
			CommonService commonService = ServiceFactory.getCommonService();
			DrBlogManager blogDAO = ServiceFactory.getBlogManger();
			Page page = commonService.createPage(wrappReq.get());
			Map<String, Object> rsMap = blogDAO.pageAllBlogWrapTalk(page);

			Page changePage = (Page) rsMap.get("page");
			setAttr(SCOPE_REQ, "page", changePage);
			Object rs = rsMap.get("rs1");
			if (rs == null) {
				throw new DormException(DormErrCode.D0002);
			}

			Map<String, Object> subRs = (Map<String, Object>) rs;
			Object srcBlog = subRs.get("blog");
			if (srcBlog == null) {
				throw new DormException(DormErrCode.D0002);
			}
			DrBlog blog = (DrBlog) srcBlog;
			setAttr(SCOPE_REQ, "blog", blog);

			Object srcTalk = subRs.get("talk");
			if (srcTalk != null) {
				setAttr(SCOPE_REQ, "talk", (List<DrTalk>) srcTalk);
			}

			List<Map<String, Object>> fiveMap = blogDAO.queryTopFiveBlog();
			setAttr(SCOPE_REQ, "fiveMap", fiveMap);
		} catch (DormException e) {
			setAttr(SCOPE_REQ, "status", "0");
			log.error(e.getMessage(), e);
		}
		setDUrl("blogDorm.jsp");
	}

}
