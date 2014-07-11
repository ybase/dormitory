package com.ybase.dorm.manger.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ybase.dorm.DormException;
import com.ybase.dorm.annotation.Service;
import com.ybase.dorm.bas.BasDAOHolder;
import com.ybase.dorm.bas.DormConstant;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.DrImageManager;
import com.ybase.dorm.manger.DrRecordManager;
import com.ybase.dorm.vo.DrImage;
import com.ybase.dorm.vo.DrRecord;
import com.ybase.dorm.vo.DrUser;
import com.ybase.dorm.vo.Page;

public class DrImageManagerImpl extends BasDAOHolder implements DrImageManager {

	@Service(value = "imageManager")
	private DrRecordManager recordDAO;

	@Override
	public boolean addDrImage(DrImage image) throws DormException {
		boolean flag = false;

		if (DormUtil.isNullOrEmpty(image.getPicPath())) {
			throw new DormException("DrImage.picPath字段为空!");
		}

		try {
			image = addVO(image);
			if (image.getId() != null) {
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new DormException(e.getMessage());
		}

		return flag;
	}

	@Override
	public DrImage queryDrImageById(Integer id) throws DormException {
		if (id != null) {
			throw new DormException("查询字段id为空!");
		} else {
			Map<String, Object> props = new HashMap<String, Object>();
			props.put("id_Eq", new Object[] { id, SQL_AND });
			List<DrImage> list;
			try {
				list = executeQuery(DrImage.class, props);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new DormException(e.getMessage());
			}
			return DormUtil.getOne(list);
		}
	}

	@Override
	public List<DrImage> queryAllDrImage() {
		Map<String, String> sort = new TreeMap<String, String>();
		sort.put("crTime", SQL_SORT_DESC);
		sort.put("crDate", SQL_SORT_DESC);
		List<DrImage> list = null;
		try {
			list = executeQuery(DrImage.class, null, sort);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	private int countAllDrImage() {
		try {
			return executeCountQuery(DrImage.class, null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public Map<String, Object> pageAllDrImage(Page page) throws DormException {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		try {
			if (page == null) {
				throw new DormException("page 参数为空!");
			}

			page.setTotalRecord(countAllDrImage());
			page.initPage();

			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", SQL_SORT_DESC);
			sort.put("crDate", SQL_SORT_DESC);
			List<DrImage> list = executeQuery(DrImage.class, null, page, sort);

			rsMap.put("image", list);
			rsMap.put("page", page);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rsMap;
	}

	@Override
	public List<DrImage> queryIndex4DrImage() {
		try {
			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", SQL_SORT_DESC);
			sort.put("crDate", SQL_SORT_DESC);
			return executeQuery(DrImage.class, null, sort, 4);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<DrImage> queryClassic10DrImage() {
		try {
			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", SQL_SORT_DESC);
			sort.put("crDate", SQL_SORT_DESC);
			sort.put("yesCount", SQL_SORT_DESC);

			Map<String, Object> par = new HashMap<String, Object>();
			par.put("position_Eq", new Object[] { DormConstant.DR_IMAGE_C, SQL_AND });
			List<DrImage> images = executeQuery(DrImage.class, par, sort, 10);
			if (!DormUtil.isNullOrEmpty(images)) {
				List<DrImage> other = null;
				if (images.size() < 10) {
					other = queryTopXDrNonCImage(10 - images.size());
				}
				images.addAll(other);
			} else {
				return queryTopXDrNonCImage(10);
			}
			return images;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	private List<DrImage> queryTopXDrNonCImage(int x) {
		try {
			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", SQL_SORT_DESC);
			sort.put("crDate", SQL_SORT_DESC);
			sort.put("yesCount", SQL_SORT_DESC);

			Map<String, Object> par = new HashMap<String, Object>();
			par.put("position_Nq", new Object[] { DormConstant.DR_IMAGE_C, SQL_AND });
			return executeQuery(DrImage.class, par, sort, x);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<DrImage> queryTopXDrImage(int x) {
		try {
			Map<String, String> sort = new TreeMap<String, String>();
			sort.put("crTime", SQL_SORT_DESC);
			sort.put("crDate", SQL_SORT_DESC);

			return executeQuery(DrImage.class, null, sort, x);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public synchronized boolean updateY(Integer id, DrUser usr) throws DormException {
		boolean flag = false;

		try {
			if (usr == null) {
				throw new DormException("updateY 参数[usr] 为空!");
			}

			if (id == null) {
				log.error("updateY 参数[id] 为空!");
				throw new DormException("updateY 参数[id] 为空!");
			}

			Map<String, Object> updateProps = new HashMap<String, Object>();
			updateProps.put("yesCount", "yescount+1");

			Map<String, Object> conProp = new HashMap<String, Object>();
			conProp.put("id_Eq", new Object[] { id, SQL_AND });
			int rows = update(DrImage.class, updateProps, conProp);
			if (rows > 0) {
				if (!recordDAO.existRecordByUsrDtTp(usr.getId(), DormUtil.getDate8Str(), Integer.valueOf(DormConstant.DR_RECORD_TYPE_1), id)) {
					DrRecord record = new DrRecord();
					record.setRelId(id);
					record.setCrDate(DormUtil.getDate8Str());
					record.setCrTime(DormUtil.getTime9Str());
					record.setDrType(Integer.valueOf(DormConstant.DR_RECORD_TYPE_1));
					record.setUsrName(usr.getName());
					record.setCrUsr(usr.getId());
					if (recordDAO.addDrRecord(record)) {
						flag = true;
					}
				} else {
					throw new DormException(String.format("当前用户[%s]已点赞过当前图片[%s]", usr.getId(), id));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new DormException(e);
		}
		return flag;
	}
}
