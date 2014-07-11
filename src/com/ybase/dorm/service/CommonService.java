package com.ybase.dorm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ybase.dorm.DormException;
import com.ybase.dorm.annotation.Service;
import com.ybase.dorm.bas.CompressPicVO;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.bas.DormErrCode;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.bas.MD5EncrytUtil;
import com.ybase.dorm.manger.DrBlogManager;
import com.ybase.dorm.manger.DrTopManager;
import com.ybase.dorm.manger.DrUserManager;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;
import com.ybase.dorm.vo.Page;

/*
 * 通用Service<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-26<br/>
 */
public class CommonService {

	private static final Logger log = Logger.getLogger(CommonService.class.getName());
	@Service("userManager")
	public DrUserManager userDAO;
	@Service("blogManager")
	public DrBlogManager blogDAO;
	@Service("topManager")
	public DrTopManager topDAO;

	public boolean checkLogin(String email, String passwd, HttpServletRequest request) throws DormException {
		if (!DormUtil.isNullOrEmpty(email) && !DormUtil.isNullOrEmpty(passwd)) {
			try {
				DrUser usr = userDAO.queryUsrByEmail(email);
				if (usr != null) {
					if (usr.getStatus() == null) {
						throw new DormException(DormErrCode.E0010);
					} else if (DormConstant.DR_USER_STATUS_1.equals(String.valueOf(usr.getStatus()))) {
						throw new DormException(DormErrCode.E0011);
					} else if (DormConstant.DR_USER_STATUS_2.equals(String.valueOf(usr.getStatus()))) {
						throw new DormException(DormErrCode.E0012);
					} else if (!DormConstant.DR_USER_STATUS_0.equals(String.valueOf(usr.getStatus()))) {
						throw new DormException(DormErrCode.E0010);
					}

					boolean check = MD5EncrytUtil.md5Encrypt(passwd.trim()).equals(usr.getPasswd());
					if (check) {
						userDAO.updateVDTS(usr.getId());// 更新访问次数最后登录时间
						request.getSession().setAttribute("loginusr", usr);
					}
					return check;
				}
			} catch (DormException e) {
				log.error(e.getMessage(), e);
				throw e;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public String createBlogJson(HttpServletRequest req) {
		Page page = null;
		String status = "1";
		String tip = "";
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			page = createPage(req);
			if (page == null) {
				throw new DormException(DormErrCode.D0001);
			}
			Map<String, Object> result = blogDAO.pageAllBlogWrapTalk(page);
			Page changePage = (Page) result.get("page");
			jsonMap.put("page", changePage);
			Object rs = result.get("rs1");
			if (rs == null) {
				throw new DormException(DormErrCode.D0002);
			}

			Map<String, Object> rsMap = (Map<String, Object>) rs;
			Object srcBlog = rsMap.get("blog");
			if (srcBlog == null) {
				throw new DormException(DormErrCode.D0002);
			}
			DrBlog blog = (DrBlog) srcBlog;
			jsonMap.put("blog", blog);
			Object srcTalk = rsMap.get("talk");
			if (srcTalk != null) {
				jsonMap.put("talk", (List<DrTalk>) srcTalk);
			}
		} catch (DormException e) {
			status = "0";
			tip = e.getMessage();
			log.error(e.getMessage(), e);
		}
		jsonMap.put("status", status);
		jsonMap.put("tip", tip);
		return DormUtil.isoToG180(JSONObject.fromObject(jsonMap).toString());
	}

	/**
	 * 根据Request 初始化Page<br/>
	 * 
	 * @DORMITORY_V1.O,yangxb,2014-05-27<br/>
	 * @param req
	 */
	public Page createPage(HttpServletRequest req) throws DormException {
		Page page = new Page();
		Object p1 = req.getParameter("pageCurrent");
		Object p2 = req.getParameter("pageFirst");
		Object p3 = req.getParameter("pageLast");

		if (p1 == null) {
			p1 = "1";
		}
		Integer current = Integer.valueOf((String) p1);
		page.setCurrent(current);

		if (current != 1 && (p2 == null || p3 == null)) {
			throw new DormException(DormErrCode.D0003);
		} else if (current != 1 && (DormUtil.isNullOrEmpty(((String) p2)) || DormUtil.isNullOrEmpty(((String) p3)))) {
			throw new DormException(DormErrCode.D0003);
		} else if (!DormUtil.isNullOrEmpty(((String) p2)) && !DormUtil.isNullOrEmpty(((String) p3))) {
			page.setFirst(Integer.valueOf((String) p2));
			page.setLast(Integer.valueOf((String) p3));
		}

		return page;
	}

	/**
	 * 备份上传图片<br/>
	 * 
	 * @DORMITORY_V1.O,yangxb,2014-05-27<br/>
	 * @param uploadFilePath
	 * @param uuidName
	 * @param expand
	 */
	public void createMidSmallPic(String uploadFilePath, String uuidName, String expand) {
		if (!DormUtil.isNullOrEmpty(uploadFilePath)) {
			uploadFilePath += "\\";
			CompressPicVO picVO1 = new CompressPicVO(1);
			picVO1.compressPic(uploadFilePath, uploadFilePath, uuidName + "." + expand, uuidName + "_idx." + expand);
			picVO1.compressPic(uploadFilePath, DormConstant.SYS_UPLOAD_FILE_BAK, uuidName + "." + expand, uuidName + "_idx." + expand);
			CompressPicVO picVO2 = new CompressPicVO(2);
			picVO2.compressPic(uploadFilePath, uploadFilePath, uuidName + "." + expand, uuidName + "_mid." + expand);
			picVO2.compressPic(uploadFilePath, DormConstant.SYS_UPLOAD_FILE_BAK, uuidName + "." + expand, uuidName + "_mid." + expand);
			CompressPicVO picVO3 = new CompressPicVO(3);
			picVO3.compressPic(uploadFilePath, uploadFilePath, uuidName + "." + expand, uuidName + "_sma." + expand);
			picVO3.compressPic(uploadFilePath, DormConstant.SYS_UPLOAD_FILE_BAK, uuidName + "." + expand, uuidName + "_sma." + expand);
		}
	}

	public DrTop getOneRandomTop(int id, boolean flag) {
		DrTop top = null;
		if (flag) {
			List<Object> ids = topDAO.queryIds();
			if (ids != null && ids.size() > 0) {
				int count = ids.size();
				while (top == null && count > 0) {
					int rand = DormUtil.randomOne(ids.size(), 0);
					top = topDAO.queryRandomTop((Integer) ids.get(rand));
					count--;
				}
			}
		} else {
			top = topDAO.queryRandomTop(id);
		}
		return top;
	}
}
