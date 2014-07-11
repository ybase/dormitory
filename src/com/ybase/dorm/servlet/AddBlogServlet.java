package com.ybase.dorm.servlet;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.AbstractCommonDispatch;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.manger.DrBlogManager;
import com.ybase.dorm.manger.DrImageManager;
import com.ybase.dorm.service.CommonService;
import com.ybase.dorm.vo.DrBlog;
import com.ybase.dorm.vo.DrImage;
import com.ybase.dorm.vo.DrUser;

/**
 * 添加主题<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014-5-24
 * 
 */
public class AddBlogServlet extends AbstractCommonDispatch {

	private static final long serialVersionUID = 1L;
	private static final String[] PIC_FORMAT = { "jpg", "gif", "png", "bmp", "jpeg" };

	@Override
	public void process() throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(wrappReq.get());
		DrBlogManager blogDAO = ServiceFactory.getBlogManger();
		DrImageManager imageDAO = ServiceFactory.getImageManager();
		CommonService commonService = ServiceFactory.getCommonService();
		if (isMultipart) {
			try {
				// 获得磁盘文件条目工厂
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);

				DrBlog blog = new DrBlog();
				DrImage image = new DrImage();
				DrUser usr = (DrUser) getAttr(SCOPE_SESSION, "loginusr");
				if (usr != null && usr.getId() > 0) {
					blog.setCrUsr(usr.getId());
					blog.setUsrName(usr.getName());
				} else {
					throw new DormException("请登录系统");
				}

				Iterator<?> items = upload.parseRequest(wrappReq.get()).iterator();
				while (items.hasNext()) {
					FileItem item = (FileItem) items.next();
					if (!item.isFormField()) {
						// 取出上传文件的文件名称
						String name = item.getName();
						String fileName = name.substring(name.lastIndexOf('\\') + 1, name.length());
						if (!checkPicFormat(fileName)) {
							throw new DormException("上传图片格式错误(jpg,jpeg,png,gif,bmp)");
						}

						@SuppressWarnings("deprecation")
						String path = wrappReq.get().getRealPath(DormConstant.SYS_UPLOAD_FILE);
						// 上传文件
						factory.setRepository(new File(path));
						// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
						factory.setSizeThreshold(1024 * 1024);

						String uuidName = UUID.randomUUID().toString().replaceAll("-", "");
						String uploadFilePath = path + File.separatorChar + getFileName(uuidName, fileName);
						String backFilePath = DormConstant.SYS_UPLOAD_FILE_BAK + getFileName(uuidName, fileName);
						File uploadedFile = new File(uploadFilePath);
						item.write(uploadedFile);
						DormUtil.fileChannelCopy(uploadedFile, new File(backFilePath));

						image.setCrDate(DormUtil.getDate8Str());
						image.setCrTime(DormUtil.getTime9Str());
						image.setPosition(DormConstant.DR_IMAGE_O);
						image.setPicPath(getFileName(uuidName, fileName));
						blog.setPicPath(getFileName(uuidName, fileName));

						commonService.createMidSmallPic(path, uuidName, getFileExpand(fileName));
						// 打印上传成功信息
					} else {
						// 获得简单域的名字
						String fieldName = item.getFieldName();
						// 获得简单域的值
						String fieldValue = DormUtil.isoToG180(item.getString());

						if ("name".equals(fieldName)) {
							blog.setTheme(fieldValue);
						} else if ("message".equals(fieldName)) {
							blog.setBlogDesc(fieldValue);
						}
					}
				}

				if (imageDAO.addDrImage(image)) {
					blog.setCrDate(DormUtil.getDate8Str());
					blog.setCrTime(DormUtil.getTime9Str());
					blog.setImgId(image.getId());
					if (!blogDAO.addDrBlog(blog)) {
						throw new DormException("发布主题失败");
					}
				} else {
					throw new DormException("发布主题失败");
				}

				setAttr(SCOPE_REQ, "prevText", "主题已发布");
			} catch (DormException de) {
				setAttr(SCOPE_REQ, "prevText", de.getMessage());
			} catch (Exception e) {
				setAttr(SCOPE_REQ, "prevText", "暂时无法发布主题");
			}
		} else {
			setAttr(SCOPE_REQ, "prevText", "发布主题");
		}
		setDUrl("dormContact");
	}

	private String getFileName(String uuidName, String fileName) {
		StringBuffer nameStr = new StringBuffer(uuidName);
		if (fileName.indexOf(".") != -1) {
			nameStr.append(".").append(fileName.split("\\.")[1]);
		}
		return nameStr.toString();
	}

	private String getFileExpand(String fileName) {
		if (!DormUtil.isNullOrEmpty(fileName) && fileName.indexOf(".") != -1) {
			return fileName.split("\\.")[1];
		}
		return "jpg";
	}

	private boolean checkPicFormat(String fileName) {
		if (fileName.indexOf(".") != -1) {
			String[] fileStr = fileName.split("\\.");
			if (Arrays.asList(PIC_FORMAT).contains(fileStr[1])) {
				return true;
			}
		}
		return false;
	}
}
