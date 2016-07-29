package com.example.phone.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ���������õ�һЩ��������
 * 
 * @author yxchao ����10:59:32
 */
public class CommonUtil{
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * �õ��ַ�����ʽ��ʱ��
	 * 
	 * @param time
	 * @return 2015-01-01 11:11:11
	 */
	public static String getFormatTime(long time) {
		Date date = new Date(time);
		return dateFormat.format(date);
	}

	private static DecimalFormat df = new DecimalFormat("#.00");

	/**
	 * �õ��ַ�����ʽ���ļ���С
	 * 
	 * @param filesize
	 * @return B? KB? MB? GB?
	 */
	public static String getFileSize(long filesize) {
		StringBuffer mstrbuf = new StringBuffer();
		if (filesize < 1024) {
			mstrbuf.append(filesize);
			mstrbuf.append(" B");
		}
		else if (filesize < 1048576) {
			mstrbuf.append(df.format((double) filesize / 1024));
			mstrbuf.append(" K");
		}
		else if (filesize < 1073741824) {
			mstrbuf.append(df.format((double) filesize / 1048576));
			mstrbuf.append(" M");
		}
		else {
			mstrbuf.append(df.format((double) filesize / 1073741824));
			mstrbuf.append(" G");
		}
		return mstrbuf.toString();
	}
}
