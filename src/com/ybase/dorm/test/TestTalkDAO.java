package com.ybase.dorm.test;

import java.util.List;

import org.junit.Test;

import com.ybase.dorm.DormException;
import com.ybase.dorm.bas.DormUtil;
import com.ybase.dorm.manger.DrTalkManager;
import com.ybase.dorm.manger.impl.DrTalkManagerImpl;
import com.ybase.dorm.vo.DrTalk;
import com.ybase.dorm.vo.DrUser;

public class TestTalkDAO {

	private DrTalkManager talkDAO = new DrTalkManagerImpl();

	@Test
	public void testAddTalk() {
		DrTalk talk = new DrTalk();
		talk.setBlogId(1);
		talk.setCrDate(DormUtil.getDate8Str());
		talk.setCrTime(DormUtil.getTime9Str());
		talk.setCrUsr(3);
		talk.setUsrName("À¿≈÷◊”3");
		talk.setTalkDesc("≤‚ ‘º«¬º3");
		talk.setYesCount(0);
		talk.setNoCount(0);
		System.out.println(talkDAO.addTalk(talk));
	}

	@Test
	public void testQueryListDrTalkByBlogId() throws DormException {
		List<DrTalk> talks = talkDAO.queryListDrTalkByBlogId(1);
		System.out.println("Result:" + talks.size());
	}

	@Test
	public void testQueryTop20DrTalkByBlogId() throws DormException {
		List<DrTalk> talks = talkDAO.queryTop20DrTalkByBlogId(1);
		System.out.println("Result:" + talks.size());
	}

	@Test
	public void testUpdateY() {
		DrUser usr = new DrUser();
		usr.setId(419005);
		usr.setName("À¿≈÷◊”");
		System.out.println(talkDAO.updateY(2, usr));
	}

	@Test
	public void testUpdateN() {
		DrUser usr = new DrUser();
		usr.setId(419005);
		usr.setName("À¿≈÷◊”");
		System.out.println(talkDAO.updateN(2, usr));
	}

}
