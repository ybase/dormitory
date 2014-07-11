package com.ybase.dorm.servlet;

import com.ybase.dorm.bas.AbstractAjaxDispatch;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.DrTopManager;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;

public class TopAddController extends AbstractAjaxDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		DrUser usr = getLoginUsrObj();
		if (usr == null) {
			setErrStatus();
			setTip("您还未登陆系统");
			return;
		}

		String topDesc = getPar("topDesc");
		if (DormUtil.isNullOrEmpty(topDesc)) {
			setErrStatus();
			setTip("描述为空");
			return;
		}
		
		String topName = getPar("topName");
		if (DormUtil.isNullOrEmpty(topName)) {
			setErrStatus();
			setTip("名称为空");
			return;
		}

		DrTop top = new DrTop();
		top.setTopDesc(topDesc.trim());
		top.setName(topName.trim());
		top.setYesCount(0);
		top.setNoCount(0);
		setCommonVO(top);
		DrTopManager topManager = ServiceFactory.getTopManager();
		if (!topManager.addDrTop(top)) {
			setErrStatus();
			setTip("添加失败");
			return;
		}

		setSuccStatus();
		setTip("添加成功");
	}

}
