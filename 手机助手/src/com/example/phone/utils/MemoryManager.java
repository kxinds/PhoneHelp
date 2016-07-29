package com.example.phone.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;


/**
 * �ڴ漰�洢�ռ���Ϣ��ȡ
 * 
 * 1. �Դ��ռ䲻��, ֧����չ�� TF 2. �Դ��ռ䲻��, ���ÿռ�16G,32G, ��֧����չTF 3. �Դ��ռ䲻��, ���ÿռ�16G,32G,
 * ֧����չTF
 * 
 * 1. �Դ��ռ䲻��, ���ÿռ�16G,32G 2. �Դ��ռ䲻��, ���ÿռ�16G,32G, ֧����չTF 3. �Դ��ռ䲻��2G
 * 
 */
public class MemoryManager {
	// --------------------------------------------------------------------
	/** �豸����洢ȫ����С��λB */
	public static long getSelfSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long blockCount = stat.getBlockCount();
		long dataFileSize = blockCount * blockSize;
		path = Environment.getDownloadCacheDirectory();
		stat = new StatFs(path.getPath());
		blockSize = stat.getBlockSize();
		blockCount = stat.getBlockCount();
		long cacheFileSize = blockCount * blockSize;

		path = Environment.getRootDirectory();
		stat = new StatFs(path.getPath());
		blockSize = stat.getBlockSize();
		blockCount = stat.getBlockCount();
		long rootFileSize = blockCount * blockSize;

		return dataFileSize + cacheFileSize + rootFileSize;
	}

	/** �豸����洢������Ч�ռ��С��λB */
	public static long getSelfAvailableSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long blockCount = stat.getAvailableBlocks();
		long dataFileSize = blockCount * blockSize;

		path = Environment.getDownloadCacheDirectory();
		stat = new StatFs(path.getPath());
		blockSize = stat.getBlockSize();
		blockCount = stat.getAvailableBlocks();
		long cacheFileSize = blockCount * blockSize;

		path = Environment.getRootDirectory();
		stat = new StatFs(path.getPath());
		blockSize = stat.getBlockSize();
		blockCount = stat.getAvailableBlocks();
		long rootFileSize = blockCount * blockSize;
		return dataFileSize + cacheFileSize + rootFileSize;
	}

	
	
	// --------------------------------------------------------------------
	/** ��ȡ�ֻ�����sdcard·��, Ϊnull��ʾ������TF�� */
	public static String getPhoneOutTFPath() {
		
//		Map<String, String> map = System.getenv();
//		//LogUtil.i("map"+map);
//		if (map.containsKey("EXTERNAL_STORAGE2")) {
//			String paths = map.get("EXTERNAL_STORAGE2");
//			String path[] = paths.split(":");
//			if (path == null || path.length <= 0) {
//				return null;
//			}
//			return path[0];
//		}SECONDARY_STORAGE
//		return null;
		
		Map<String, String> map = System.getenv();
		Log.i("oracle",map+"");
		if (map.containsKey("EXTERNAL_STORAGE")) {
			//Environment.getExternalStorageDirectory  ����ka
			String paths = map.get("EXTERNAL_STORAGE");
			// /storage/extSdCard
			String path[] = paths.split(":");
			if (path == null || path.length <= 0) {
				return null;
			}
			return path[0];
		}
		return null;
	}

	/** ��ȡ�ֻ�����sdcard·��, Ϊnull��ʾ������TF�� */
	public static String getPhoneInTFPath() {
		String sdcardState = Environment.getExternalStorageState();
		if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
			return null;
		}
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	/** ��ȡ�ֻ��洢����С(�����û�ȡ��������TF����С, �����û�ȡ�������ô�С) */
	public static long getSDCardSize() {
		String tfPath = "";
		// ��ȡ����TF��·�� - ��������TF����С
		tfPath = getPhoneOutTFPath();
		if (tfPath != null) {
			File path = new File(tfPath);
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long blockCount = stat.getBlockCount();
			return blockCount * blockSize;
		}
		// û�л�ȡ��������TF��·�� - ��������TF����С
		tfPath = getPhoneInTFPath();
		if (tfPath != null) {
			File path = new File(tfPath);
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long blockCount = stat.getBlockCount();
			return blockCount * blockSize;
		}
		return 0;
	}

	public static long getSDCardAvailableSize() {
		String tfPath = "";
		// ��ȡ����TF��·�� - ��������TF����С
		tfPath = getPhoneOutTFPath();
		if (tfPath != null) {
			File path = new File(tfPath);
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long blockCount = stat.getAvailableBlocks();
			return blockCount * blockSize;
		}
		// û�л�ȡ��������TF��·�� - ��������TF����С
		tfPath = getPhoneInTFPath();
		if (tfPath != null) {
			File path = new File(tfPath);
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long blockCount = stat.getAvailableBlocks();
			return blockCount * blockSize;
		}
		return 0;
	}

	// --------------------------------------------------------------------
	/** �豸���������ڴ� ��λB */
	public static long getPhoneFreeRamMemory(Context context) {
		MemoryInfo info = new MemoryInfo();
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		am.getMemoryInfo(info);
		return info.availMem;
	}

	/** �豸���������ڴ� ��λB */
	public static long getPhoneTotalRamMemory() {
		try {
			FileReader fr = new FileReader("/proc/meminfo");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split("\\s+");
			return Long.valueOf(array[1]) * 1024; // ԭΪkb, תΪb
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
