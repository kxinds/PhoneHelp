package com.example.phone.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：常用的一些公共函数
 * 
 * @author yxchao 下午10:59:32
 */
public class CommonUtil{
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * 得到字符串方式的时间
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
	 * 得到字符串方式的文件大小
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
