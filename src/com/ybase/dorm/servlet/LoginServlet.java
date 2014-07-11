package com.ybase.dorm.servlet;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.AbstractAjaxDispatch;
import com.ybase.dorm.bas.DormErrCode;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.service.CommonService;

/**
 * 登录<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-26<br/>
 * 
 */
public class LoginServlet extends AbstractAjaxDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() {
		String usrPasswd = getPar("usrPasswd");
		String usrEmail = getPar("usrEmail");

		try {
			CommonService commonService = ServiceFactory.getCommonService();
			if (commonService.checkLogin(usrEmail, usrPasswd, wrappReq.get())) {
				setJsonMap("status", "1");
				setJsonMap("show", getLoginUsr());
				setJsonMap("tip", "登录成功");
			} else {
				setJsonMap("status", "0");
				setJsonMap("tip", "登录失败");
			}
		} catch (DormException e) {
			setJsonMap("status", "0");
			if (DormErrCode.E0010.equals(e.getMessage())) {
				setJsonMap("tip", "用户状态异常");
			} else if (DormErrCode.E0011.equals(e.getMessage())) {
				setJsonMap("tip", "用户已登录");
			} else if (DormErrCode.E0012.equals(e.getMessage())) {
				setJsonMap("tip", "用户已停用");
			} else {
				setJsonMap("tip", "登录失败");
			}
		}
	}

}
