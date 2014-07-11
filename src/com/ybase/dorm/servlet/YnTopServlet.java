package com.ybase.dorm.servlet;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.AbstractAjaxDispatch;
import com.ybase.dorm.bas.DormErrCode;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrTopManager;
import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;

/**
 * ����/�²�Top<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-27<br/>
 * 
 */
public class YnTopServlet extends AbstractAjaxDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		DrTopManager topDAO = ServiceFactory.getTopManager();
		setJsonMap("status", "0");

		String topId = getPar("topId");
		String type = getPar("type");
		try {
			if (DormUtil.isNullOrEmpty(topId) || DormUtil.isNullOrEmpty(type)) {
				throw new DormException(DormErrCode.E0009);
			}

			DrUser loginUsr = getLoginUsrObj();
			if (loginUsr == null) {
				throw new DormException(DormErrCode.E0005);
			}

			if ("Y".equals(type.trim())) {
				if (!topDAO.updateY(Integer.valueOf(topId), loginUsr)) {
					throw new DormException(DormErrCode.D0007);
				}
			} else {
				if (!topDAO.updateN(Integer.valueOf(topId), loginUsr)) {
					throw new DormException(DormErrCode.D0007);
				}
			}

			DrTop top = topDAO.queryRandomTop(Integer.valueOf(topId));
			if (top == null) {
				throw new DormException(DormErrCode.D0002);
			} else {
				setJsonMap("show1", top.getYesCount());
				setJsonMap("show2", top.getNoCount());
			}

			setJsonMap("status", "1");
			setJsonMap("tip", "����/�²۳ɹ�");
		} catch (DormException e) {
			if (DormErrCode.E0005.equals(e.getMessage().trim())) {
				setJsonMap("tip", "δ��¼ϵͳ");
			} else if (DormErrCode.E0005.equals(e.getMessage().trim())) {
				setJsonMap("tip", "ϵͳ����");
			} else {
				setJsonMap("tip", "ÿ��ÿ���ֻ�ܵ��޻��²�һ��");
			}
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			setJsonMap("tip", "����/�²�ʧ��");
			log.error(e.getMessage(), e);
		}

	}

}
