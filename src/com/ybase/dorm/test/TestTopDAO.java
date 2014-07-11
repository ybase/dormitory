package com.ybase.dorm.test;

import org.junit.Test;

import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.DrTopManager;
import com.ybase.dorm.manger.impl.DrTopManagerImpl;
import com.ybase.dorm.vo.DrTop;
import com.ybase.dorm.vo.DrUser;

public class TestTopDAO {

	private DrTopManager topDAO = new DrTopManagerImpl();

	@Test
	public void testAddTop() {
		DrTop top = new DrTop();
		top.setCrDate(DormUtil.getDate8Str());
		top.setCrTime(DormUtil.getTime9Str());
		top.setCrUsr(2);
		top.setName("来盘废材操蛋");
		top.setNoCount(0);
		top.setYesCount(0);
		top.setTopDesc("厕所堵了好几天，薰了好几天，受不了了，棍法诞生了");
		System.out.println(topDAO.addDrTop(top));
	}

	@Test
	public void testUpdateY() {
		DrUser usr = new DrUser();
		usr.setId(419004);
		usr.setName("管理员");
		System.out.println(topDAO.updateY(20, usr));
	}

	@Test
	public void testUpdateN() {
		DrUser usr = new DrUser();
		usr.setId(419005);
		usr.setName("死胖子");
		System.out.println(topDAO.updateN(1, usr));
	}

	@Test
	public void testQueryRandomTop() {
		System.out.println(topDAO.queryRandomTop(1));
	}

	@Test
	public void testQueryIds() {
		System.out.println(topDAO.queryIds().get(2));
	}
}
