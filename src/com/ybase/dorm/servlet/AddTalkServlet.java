package com.ybase.dorm.servlet;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.AbstractAjaxDispatch;
import com.ybase.dorm.bas.DormErrCode;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrTalkManager;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.DrUser;

/**
 * �������<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-26<br/>
 * 
 */
public class AddTalkServlet extends AbstractAjaxDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		DrTalkManager talkDAO = ServiceFactory.getTalkManager();
		setJsonMap("status", "0");
		String blogId = getPar("blogId");
		String talkDesc = getPar("talkDesc");
		try {
			if (DormUtil.isNullOrEmpty(blogId) || DormUtil.isNullOrEmpty(talkDesc)) {
				throw new DormException(DormErrCode.D0004);
			}

			DrUser loginUsr = getLoginUsrObj();
			if (loginUsr == null) {
				throw new DormException(DormErrCode.D0005);
			}

			DrTalk talk = new DrTalk();
			talk.setBlogId(Integer.valueOf(blogId));
			talk.setCrDate(DormUtil.getDate8Str());
			talk.setCrTime(DormUtil.getTime9Str());
			talk.setCrUsr(loginUsr.getId());
			talk.setUsrName(loginUsr.getName());
			talk.setTalkDesc(talkDesc);
			talk.setYesCount(0);
			talk.setNoCount(0);

			if (!talkDAO.addTalk(talk)) {
				throw new DormException(DormErrCode.D0006);
			}

			setJsonMap("status", "1");
			setJsonMap("tip", "��ӳɹ�");
		} catch (DormException e) {
			setJsonMap("tip", "δ��¼ϵͳ �� ϵͳ����");
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			setJsonMap("tip", "���ʧ��");
			log.error(e.getMessage(), e);
		}
	}

}
