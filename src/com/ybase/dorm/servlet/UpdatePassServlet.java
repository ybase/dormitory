package com.ybase.dorm.servlet;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.AbstractCommonDispatch;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.bas.MD5EncrytUtil;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrUserManager;
import com.ybase.dorm.service.CommonService;
import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;

/**
 * ĞŞ¸ÄÃÜÂë<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-29
 * 
 */
public class UpdatePassServlet extends AbstractCommonDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		DrUserManager userDAO = ServiceFactory.getUserManager();
		CommonService commonService = ServiceFactory.getCommonService();
		DrUser user = getLoginUsrObj();
		try {
			if (user == null) {
				throw new DormException("ÏµÍ³Î´µÇÂ¼");
			}

			String oldPass = getPar("oldpass");
			if (DormUtil.isNullOrEmpty(oldPass)) {
				throw new DormException("ÇëÌîĞ´¾ÉÃÜÂë");
			}

			String newPass = getPar("newpass");
			if (DormUtil.isNullOrEmpty(newPass)) {
				throw new DormException("ÇëÌîĞ´ĞÂÃÜÂë");
			}

			String cfPass = getPar("cfpass");
			if (DormUtil.isNullOrEmpty(cfPass)) {
				throw new DormException("ÇëÌîĞ´È·ÈÏÃÜÂë");
			}

			if (!cfPass.equals(newPass)) {
				throw new DormException("È·ÈÏÃÜÂë´íÎó");
			}

			if (!user.getPasswd().equals(MD5EncrytUtil.md5Encrypt(oldPass.trim()))) {
				throw new DormException("¾ÉÃÜÂë´íÎó");
			}

			if (!userDAO.updatePass(user.getId(), newPass)) {
				throw new DormException("ÃÜÂëĞŞ¸ÄÊ§°Ü");
			}

			DrTop top = commonService.getOneRandomTop(1, true);
			setReqAttr("top", top);

			setTip("ÃÜÂëĞŞ¸Ä³É¹¦");
			setCrrStatus();
		} catch (DormException e) {
			setTip(e.getMessage());
		} catch (Exception ex) {
			setTip("ÏµÍ³´íÎó");
		}
		setDUrl("passwdDorm.jsp");
	}
}
