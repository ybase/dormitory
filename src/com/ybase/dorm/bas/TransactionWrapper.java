package com.ybase.dorm.bas;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ybase.dorm.annotation.Service;

public final class TransactionWrapper {
	private static final String MANGET_IMPL = "Impl";
	private static final Logger log = Logger.getLogger(TransactionWrapper.class);

	/**
	 * װ��ԭʼ��ҵ�������󣬷���һ����ҵ������������ͬ�ӿڵĴ������
	 * 
	 * @throws Exception
	 */
	public static Object decorate(Object delegate) throws Exception {
		if (delegate != null) {
			Field[] services = delegate.getClass().getDeclaredFields();
			for (Field service : services) {
				if (service.isAnnotationPresent(Service.class)) {
					try {
						service.setAccessible(true);
						String clzName = convertImplPackage(service.getType().getCanonicalName());
						service.set(delegate, Class.forName(clzName).newInstance());
					} catch (Exception e) {
						throw e;
					}
				}
			}
		}
		return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), delegate.getClass().getInterfaces(), new XAWrapperHandler(delegate));
	}

	private static String convertImplPackage(String daoName) {
		String splits[] = daoName.split("\\.");
		StringBuffer implName = new StringBuffer();
		int count = 0;
		for (String split : splits) {
			if (count == splits.length - 1) {
				split = convertFirstCase(MANGET_IMPL) + "." + split + MANGET_IMPL;
				implName.append(split);
			} else {
				implName.append(split + ".");
			}
			count++;
		}
		return implName.toString();
	}

	private static String convertFirstCase(String str) {
		StringBuffer rst = new StringBuffer();
		if (!DormUtil.isNullOrEmpty(str)) {
			rst.append(str.substring(0, 1).toLowerCase());
			rst.append(str.substring(1));
		}
		return rst.toString();
	}

	// ��̬������
	static final class XAWrapperHandler implements InvocationHandler {
		private final Object delegate;

		XAWrapperHandler(Object delegate) {
			this.delegate = delegate;
		}

		// ���������װҵ�����������е�ҵ�񷽷�
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Object result = null;
			Connection con = ConnectPool.getInstance().getConnection("");
			log.debug("Thread No[" + Thread.currentThread().getId() + "]," + "Connection[ " + con + " ], Where[TransactionWrapper>>Delegate>>" + method.getName() + "]");
			try {
				// ��ʼһ������
				con.setAutoCommit(false);
				// ����ԭʼҵ������ҵ�񷽷�
				result = method.invoke(delegate, args);
				con.commit(); // �ύ����
				con.setAutoCommit(true);
			} catch (Throwable t) {
				// �ع�
				con.rollback();
				con.setAutoCommit(true);
				throw t;
			} finally {
				ConnectPool.getInstance().freeConnection("", con);
			}
			return result;
		}
	}
}
