package com.ybase.dorm.servlet;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.AbstractAjaxDispatch;
import com.ybase.dorm.bas.DormErrCode;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrImageManager;
import com.ybase.dorm.vo.DrUser;

/**
 * 点赞图片<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-26<br/>
 * 
 */
public class AddImgYServlet extends AbstractAjaxDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		DrImageManager imageDAO = ServiceFactory.getImageManager();
		setJsonMap("status", "0");

		String imageId = getPar("imageId");
		log.debug("imageId:" + imageId);
		try {
			if (DormUtil.isNullOrEmpty(imageId)) {
				throw new DormException(DormErrCode.E0009);
			}

			DrUser loginUsr = getLoginUsrObj();
			if (loginUsr == null) {
				throw new DormException(DormErrCode.E0005);
			}

			if (!imageDAO.updateY(Integer.valueOf(imageId), loginUsr)) {
				throw new DormException(DormErrCode.D0007);
			}

			setJsonMap("status", "1");
		} catch (DormException e) {
			if (DormErrCode.E0005.equals(e.getMessage().trim())) {
				setJsonMap("tip", "未登录系统");
			} else if (DormErrCode.E0005.equals(e.getMessage().trim())) {
				setJsonMap("tip", "系统故障");
			} else {
				setJsonMap("tip", "每人每天仅只能点赞一次");
			}
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			setJsonMap("tip", "点赞失败");
			log.error(e.getMessage(), e);
		}
	}

}
