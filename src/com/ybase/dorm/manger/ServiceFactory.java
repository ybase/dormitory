package com.ybase.dorm.manger;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.TransactionCglib;
import com.ybase.dorm.bas.TransactionWrapper;
import com.ybase.dorm.manger.impl.DrBlogManagerImpl;
import com.ybase.dorm.manger.impl.DrImageManagerImpl;
import com.ybase.dorm.manger.impl.DrPlanManagerImpl;
import com.ybase.dorm.manger.impl.DrRecordManagerImpl;
import com.ybase.dorm.manger.impl.DrTalkManagerImpl;
import com.ybase.dorm.manger.impl.DrTopManagerImpl;
import com.ybase.dorm.manger.impl.DrUserManagerImpl;
import com.ybase.dorm.service.CommonService;
import com.ybase.dorm.vo.DrBlog;

public class ServiceFactory {
	private static final Logger log = Logger.getLogger(ServiceFactory.class.getName());
	private static Hashtable<String, Object> daoCache = new Hashtable<String, Object>();
	private static final String BOLG_DAO_KEY = "blogManager";
	private static final String IMAGE_DAO_KEY = "imageManger";
	private static final String RECORD_DAO_KEY = "recordManager";
	private static final String TALK_DAO_KEY = "talkManager";
	private static final String TOP_DAO_KEY = "topManager";
	private static final String USER_DAO_KEY = "userManager";
	private static final String PLAN_DAO_KEY = "planManager";
	private static final String COMMON_SERVICE_KEY = "commonService";

	public static DrBlogManager getBlogManger() {
		DrBlogManager blogDAO = (DrBlogManager) daoCache.get(BOLG_DAO_KEY);
		if (blogDAO == null) {
			try {
				blogDAO = (DrBlogManager) TransactionWrapper.decorate(new DrBlogManagerImpl());
				daoCache.put(BOLG_DAO_KEY, blogDAO);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return blogDAO;
	}

	public static DrPlanManager getPlanManger() {
		DrPlanManager planDAO = (DrPlanManager) daoCache.get(PLAN_DAO_KEY);
		if (planDAO == null) {
			try {
				planDAO = (DrPlanManager) TransactionWrapper.decorate(new DrPlanManagerImpl());
				daoCache.put(PLAN_DAO_KEY, planDAO);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return planDAO;
	}

	public static DrImageManager getImageManager() {
		DrImageManager imageDAO = (DrImageManager) daoCache.get(IMAGE_DAO_KEY);
		if (imageDAO == null) {
			try {
				imageDAO = (DrImageManager) TransactionWrapper.decorate(new DrImageManagerImpl());
				daoCache.put(IMAGE_DAO_KEY, imageDAO);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return imageDAO;
	}

	public static DrRecordManager getRecordManager() {
		DrRecordManager recordDAO = (DrRecordManager) daoCache.get(RECORD_DAO_KEY);
		if (recordDAO == null) {
			try {
				recordDAO = (DrRecordManager) TransactionWrapper.decorate(new DrRecordManagerImpl());
				daoCache.put(RECORD_DAO_KEY, recordDAO);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		return recordDAO;
	}

	public static DrTalkManager getTalkManager() {
		DrTalkManager talkDAO = (DrTalkManager) daoCache.get(TALK_DAO_KEY);
		if (talkDAO == null) {
			try {
				talkDAO = (DrTalkManager) TransactionWrapper.decorate(new DrTalkManagerImpl());
				daoCache.put(TALK_DAO_KEY, talkDAO);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return talkDAO;
	}

	public static DrTopManager getTopManager() {
		DrTopManager topDAO = (DrTopManager) daoCache.get(TOP_DAO_KEY);
		if (topDAO == null) {
			try {
				topDAO = (DrTopManager) TransactionWrapper.decorate(new DrTopManagerImpl());
				daoCache.put(TOP_DAO_KEY, topDAO);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return topDAO;
	}

	public static DrUserManager getUserManager() {
		DrUserManager userDAO = (DrUserManager) daoCache.get(USER_DAO_KEY);
		if (userDAO == null) {
			try {
				userDAO = (DrUserManager) TransactionWrapper.decorate(new DrUserManagerImpl());
				daoCache.put(USER_DAO_KEY, userDAO);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return userDAO;
	}

	public static CommonService getCommonService() {
		CommonService commonService = (CommonService) daoCache.get(COMMON_SERVICE_KEY);
		if (commonService == null) {
			try {
				commonService = (CommonService) TransactionCglib.getInstance(new CommonService());
				daoCache.put(COMMON_SERVICE_KEY, commonService);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return commonService;
	}

	public static void main(String args[]) throws DormException {
		DrBlogManager blogManager = ServiceFactory.getBlogManger();
		DrBlog blog = new DrBlog();
		blog.setBlogDesc("test");
		blog.setCrDate("20140205");
		blog.setCrTime("201223000");
		blog.setCrUsr(1);
		blog.setImgId(1);
		blog.setPicPath("/xx/xx");
		blog.setUsrName("À¿≈÷◊”2");
		blog.setTheme("test");
		blog.setYesCount(1);
		blogManager.addDrBlog(blog);
	}

}
