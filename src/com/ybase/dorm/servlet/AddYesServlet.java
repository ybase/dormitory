package com.ybase.dorm.servlet;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.AbstractAjaxDispatch;
import com.ybase.dorm.bas.DormErrCode;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrBlogManager;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrUser;

/**
 * ��ӵ���<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-26<br/>
 * 
 */
public class AddYesServlet extends AbstractAjaxDispatch {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void process() throws Exception {
		setJsonMap("status", "0");
		String blogId = getPar("blogId");
		try {
			DrBlogManager blogDAO = ServiceFactory.getBlogManger();
			if (DormUtil.isNullOrEmpty(blogId)) {
				throw new DormException(DormErrCode.E0009);
			}

			DrUser loginUsr = getLoginUsrObj();
			if (loginUsr == null) {
				throw new DormException(DormErrCode.E0005);
			}

			if (!blogDAO.updateYesCount(Integer.valueOf(blogId), loginUsr)) {
				throw new DormException(DormErrCode.D0007);
			}

			DrBlog blog = blogDAO.queryDrBlogById(Integer.valueOf(blogId));
			if (blog == null) {
				throw new DormException(DormErrCode.D0002);
			} else {
				setJsonMap("show", blog.getYesCount());
			}

			setJsonMap("status", "1");
			setJsonMap("tip", "���޳ɹ�");
		} catch (DormException e) {
			if (DormErrCode.E0005.equals(e.getMessage().trim())) {
				setJsonMap("tip", "δ��¼ϵͳ �� ϵͳ����");
			} else if (DormErrCode.E0009.equals(e.getMessage().trim())) {
				setJsonMap("tip", "ϵͳ����");
			} else {
				setJsonMap("tip", "ÿ��ÿ���ֻ�ܵ���һ��");
			}
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			setJsonMap("tip", "����ʧ��");
			log.error(e.getMessage(), e);
		}
	}

}
