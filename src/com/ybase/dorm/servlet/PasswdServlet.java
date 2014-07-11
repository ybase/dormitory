package com.ybase.dorm.servlet;

import com.ybase.dorm.bas.AbstractCommonDispatch;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.service.CommonService;
import com.ybase.dorm.vo.DrTop;

/**
 * –ﬁ∏ƒ√‹¬Î“≥√Ê<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-29
 * 
 */
public class PasswdServlet extends AbstractCommonDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		CommonService commonService = ServiceFactory.getCommonService();
		DrTop top = commonService.getOneRandomTop(1, true);
		setAttr(SCOPE_REQ, "top", top);
		setDUrl("passwdDorm.jsp");
	}

}
