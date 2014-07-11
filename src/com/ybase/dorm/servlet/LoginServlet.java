package com.ybase.dorm.servlet;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.AbstractAjaxDispatch;
import com.ybase.dorm.bas.DormErrCode;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.service.CommonService;

/**
 * ��¼<br/>
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
				setJsonMap("tip", "��¼�ɹ�");
			} else {
				setJsonMap("status", "0");
				setJsonMap("tip", "��¼ʧ��");
			}
		} catch (DormException e) {
			setJsonMap("status", "0");
			if (DormErrCode.E0010.equals(e.getMessage())) {
				setJsonMap("tip", "�û�״̬�쳣");
			} else if (DormErrCode.E0011.equals(e.getMessage())) {
				setJsonMap("tip", "�û��ѵ�¼");
			} else if (DormErrCode.E0012.equals(e.getMessage())) {
				setJsonMap("tip", "�û���ͣ��");
			} else {
				setJsonMap("tip", "��¼ʧ��");
			}
		}
	}

}
