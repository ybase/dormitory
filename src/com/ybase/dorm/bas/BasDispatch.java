package com.ybase.dorm.bas;

import org.apache.log4j.Logger;

public interface BasDispatch {
	public static final Logger log = Logger.getLogger(BasDispatch.class);
	public void process() throws Exception;
}
