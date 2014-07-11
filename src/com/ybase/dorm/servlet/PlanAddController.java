package com.ybase.dorm.servlet;

import com.ybase.dorm.bas.AbstractAjaxDispatch;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.DrPlanManager;
import com.ybase.dorm.manger.ServiceFactory;
import com.ybase.dorm.vo.DrPlan;
import com.ybase.dorm.vo.DrUser;

public class PlanAddController extends AbstractAjaxDispatch {

	private static final long serialVersionUID = 1L;

	@Override
	public void process() throws Exception {
		DrUser usr = getLoginUsrObj();
		if (usr == null) {
			setErrStatus();
			setTip("您还未登陆系统");
			return;
		}

		String planDesc = getPar("planDesc");
		if (DormUtil.isNullOrEmpty(planDesc)) {
			setErrStatus();
			setTip("计划描述为空");
			return;
		}

		DrPlan plan = new DrPlan();
		plan.setPlanDesc(planDesc);
		setCommonVO(plan);
		DrPlanManager planManager = ServiceFactory.getPlanManger();
		if (!planManager.addPlan(plan)) {
			setErrStatus();
			setTip("添加失败");
			return;
		}

		setSuccStatus();
		setTip("添加成功");
	}

}
