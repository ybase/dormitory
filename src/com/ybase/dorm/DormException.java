package com.ybase.dorm;

/*
 * Dromitory 异常<br/>
 *
 * @DORMITORY_V1.0, yangxb, 2014年5月25日<br/>
 */
public class DormException extends Exception {

	private static final long serialVersionUID = 1L;

	public DormException() {
		super();
	}

	public DormException(String msg) {
		super(msg);
	}

	public DormException(String msg, Throwable thr) {
		super(msg, thr);
	}

	public DormException(Throwable thr) {
		super(thr);
	}
}
