package com.ybase.dorm.log;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.PropertyConfigurator;

/**
 * 日志初始化<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-28
 * 
 */
public class LogInitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

	public void init() throws ServletException {
		String fileName = this.getInitParameter("log4jPro");
		String file = this.getServletContext().getRealPath("/") + fileName;

		if (fileName != null) {
			System.out.println("Log4j开始启动 : ----------------");
			PropertyConfigurator.configure(file);// 这句很关键
		} else {
			System.out.println("Log4j启动失败!");
		}
	}

}
