package com.ybase.dorm.test;

import java.sql.SQLException;

import org.junit.Test;

import com.ybase.dorm.bas.DbUtil;
import com.ybase.dorm.service.CommonService;
import com.ybase.dorm.vo.Page;

public class TestCommonService {

	private CommonService commonService = new CommonService();
	
	@Test
	public void testCreateBlogJson(){
		Page page = new Page();
		page.setCurrent(1);
		page.setTotalRecord(1);
		page = null;
		System.out.println(commonService.createBlogJson(null));
	}
	
	@Test
	public void testCreateMidSmallPic(){
		commonService.createMidSmallPic("C:\\Documents and Settings\\Administrator\\×ÀÃæ\\", "slide5", "jpg");
	}
	
	@Test
	public void testCreatePage(){
		
	}
	
	@Test
	public void testGetConAuto() throws SQLException{
		DbUtil util = new DbUtil();
	}
}
