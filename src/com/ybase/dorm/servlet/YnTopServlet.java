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
 * 点赞/吐槽Top<br/>
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
			setJsonMap("tip", "点赞/吐槽成功");
		} catch (DormException e) {
			if (DormErrCode.E0005.equals(e.getMessage().trim())) {
				setJsonMap("tip", "未登录系统");
			} else if (DormErrCode.E0005.equals(e.getMessage().trim())) {
				setJsonMap("tip", "系统故障");
			} else {
				setJsonMap("tip", "每人每天仅只能点赞或吐槽一次");
			}
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			setJsonMap("tip", "点赞/吐槽失败");
			log.error(e.getMessage(), e);
		}

	}

}
