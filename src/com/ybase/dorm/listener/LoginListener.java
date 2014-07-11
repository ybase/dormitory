package com.ybase.dorm.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.manger.DrUserManager;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.vo.DrUser;

public class LoginListener implements HttpSessionListener, HttpSessionAttributeListener, ServletRequestListener {

	private static final Logger log = Logger.getLogger(LoginListener.class.getName());

	private HttpSession session = null;
	private HttpServletRequest request;

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		this.session = se.getSession();
		Object obj = this.session.getAttribute("loginusr");
		String loginName = "";
		if (obj != null) {
			DrUser login = (DrUser) obj;
			loginName = login.getName();
			log.info(String.format("��ǰϵͳ��Ա��%s�� --> ��¼ϵͳ!", loginName));
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		String logMsg = se.getSession().getId() + ":" + request.getRemotePort();
		log.info("Session Created['" + logMsg + "']");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		this.session = se.getSession();
		Object obj = this.session.getAttribute("loginusr");
		String loginName = "";
		if (obj != null) {
			DrUser login = (DrUser) obj;
			try {
				DrUserManager userManager = ServiceFactory.getUserManager();
				userManager.updateStatus(login.getId(), Integer.valueOf(DormConstant.DR_USER_STATUS_0));
				this.session.removeAttribute("loginusr");
			} catch (Exception e) {
				log.error(String.format("�û���%s��ע��ʱ���û�״̬����ʧ��", login.getName()));
				System.out.println("�û�ע��ʱ���û�״̬����ʧ��");
				e.printStackTrace();
			}
			loginName = login.getName();
			log.info(String.format("��ǰϵͳ��Ա��%s�� --> �˳�ϵͳ!", loginName));
		}
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		request = (HttpServletRequest) sre.getServletRequest();
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
	}

}
