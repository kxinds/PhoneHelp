package com.example.phone.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * 类描述：手机系统管理的业务逻辑及数据处理,包含设备的Wifi,Phone和System信息;
 * 
 */
public class MobileManager {
	/** 本类的唯一实例对象 */
	private static MobileManager phoneManager;
	private Context context;
	private WifiInfo2 wifiInfo2; // Wifi信息实例对象(本类内部类)
	private PhoneInfo2 phoneInfo2; // 设备信息实例对象(本类内部类)
	private SystemInfo2 systemInfo2; // 系统信息实例对象(本类内部类)

	private MobileManager(Context context) {
		this.context = context;
		wifiInfo2 = new WifiInfo2(context);
		phoneInfo2 = new PhoneInfo2(context);
		systemInfo2 = new SystemInfo2();
	}

	/**
	 * 获取本类的唯一实例对象 @see {@link #wifiInfo2}, {@link #phoneInfo2},
	 * {@link #systemInfo2}
	 */
	public static MobileManager getPhoneManager(Context context) {
		if (phoneManager == null) {
			phoneManager = new MobileManager(context);
		}
		return phoneManager;
	}

	public WifiInfo2 getWifiInfo() {
		if (wifiInfo2 == null) {
			wifiInfo2 = new WifiInfo2(context);
		}
		return wifiInfo2;
	}

	public PhoneInfo2 getPhoneInfo() {
		if (phoneInfo2 == null) {
			phoneInfo2 = new PhoneInfo2(context);
		}
		return phoneInfo2;
	}

	public SystemInfo2 getSystemInfo() {
		if (systemInfo2 == null) {
			systemInfo2 = new SystemInfo2();
		}
		return systemInfo2;
	}

	/** Wifi数据 */
	public class WifiInfo2 {
		private WifiManager wifiManager;

		private WifiInfo2(Context context) {
			wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		}

		/** 设备Wifi名称 */
		public String getPhoneWifiName() {
			WifiInfo info = wifiManager.getConnectionInfo();
			return info.getSSID() + "";
		}

		/** 设备Wifi的IP */
		public String getPhoneWifiIP() {
			WifiInfo info = wifiManager.getConnectionInfo();
			long ip = info.getIpAddress();
			return longToIP(ip);
		}

		/** 设备Wifi的速度 */
		public String getPhoneWifiSpeed() {
			WifiInfo info = wifiManager.getConnectionInfo();
			return info.getLinkSpeed() + "";
		}

		/** 设备Wifi的MAC */
		public String getPhoneWifiMac() {
			WifiInfo info = wifiManager.getConnectionInfo();
			return info.getMacAddress() + "";
		}

		private String longToIP(long longIp) {
			StringBuffer sb = new StringBuffer("");
			// 将高24位置0
			sb.append(String.valueOf((longIp & 0x000000FF)));
			sb.append(".");
			// 将高1位置0，然后右移8位
			sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
			sb.append(".");
			// 将高8位置0，然后右移16位
			sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
			sb.append(".");
			// 直接右移24位
			sb.append(String.valueOf((longIp >>> 24)));
			return sb.toString();
		}
	}

	/** 设备数据 */
	public class PhoneInfo2 {
		private Context context;
		private ConnectivityManager connManager;
		private TelephonyManager telManager;

		private PhoneInfo2(Context context) {
			this.context = context;
			connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		}

		/** 设备CPU类型名称 (品牌？) */
		public String getPhoneCPUName() {
			return Build.CPU_ABI;
		}

		/** 设备品牌(moto?) */
		public String getPhoneName1() {
			return Build.BRAND.toUpperCase();
		}

		/** 设备制造商(moto?) */
		public String getPhoneName2() {
			return Build.MANUFACTURER;
		}

		/** 设备型号名称(xt910) */
		public String getPhoneModelName() {
			// 带国家用 PRODUCT
			return Build.MODEL.toUpperCase();
		}

		// 是否同意ACCESS_NETWORK_STATE权限
		private boolean isGranterNetworkPermission() {
			PackageManager pm = context.getPackageManager();
			String permission = Manifest.permission.ACCESS_NETWORK_STATE;
			int result = pm.checkPermission(permission, context.getPackageName());
			// 判断是否有READ_PHONE_STATE权限
			if (result == PackageManager.PERMISSION_GRANTED) {
				return true;
			}
			return false;
		}

		/** 设备网络连接类型 (OFFLINE ? WIFI ? MOBILE) permission.ACCESS_NETWORK_STATE */
		public String getPhoneNetworkType() {
			if (isGranterNetworkPermission()) {
				NetworkInfo info = connManager.getActiveNetworkInfo();
				if (!isOnline(info)) {
					return "OFFLINE";
				}
				if (info != null) {
					return info.getTypeName();
				} else {
					return "OFFLINE";
				}
			}
			return "未同意此权限";
		}

		private boolean isOnline(NetworkInfo info) {
			if (info != null && info.isConnected()) {
				return true;
			}
			return false;
		}

		// 是否同意READ_PHONE_STATE权限
		private boolean isGranterReadPhonePermission() {
			PackageManager pm = context.getPackageManager();
			String permission = Manifest.permission.READ_PHONE_STATE;
			int result = pm.checkPermission(permission, context.getPackageName());
			// 判断是否有READ_PHONE_STATE权限
			if (result == PackageManager.PERMISSION_GRANTED) {
				return true;
			}
			return false;
		}

		/**
		 * 设备电话号码 (不是所有都能拿到，运营商将手机号码已写入到sim卡中的就行,一般拿不到) Permission:
		 * READ_PHONE_STATE
		 */
		public String getPhoneNumber() {
			// 如果同意了权限-取出最放号码
			if (isGranterReadPhonePermission()) {
				return telManager.getLine1Number();
			}
			return "未同意此权限";
		}

		/** 设备运营商名称 (中国移动？中国联通？) */
		public String getPhoneTelSimName() {
			return telManager.getSimOperatorName();
		}

		/** 设备串号 permission.READ_PHONE_STATE */
		public String getPhoneIMEI() {
			// 检查是否有权限
			if (isGranterReadPhonePermission()) {
				return telManager.getDeviceId();
			}
			return "未同意此权限";
		}

		/** 设备CPU最大频率 */
		public String getPhoneCpuMaxFreq() {
			String result = "";
			ProcessBuilder cmd;
			try {
				String[] args = { "/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" };
				cmd = new ProcessBuilder(args);
				Process process = cmd.start();
				InputStream in = process.getInputStream();
				byte[] re = new byte[24];
				while (in.read(re) != -1) {
					result = result + new String(re);
				}
				in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				result = "N/A";
			}
			return result.trim();
		}

		/** 设备CPU最小频率 */
		public String getPhoneCpuMinFreq() {
			String result = "";
			ProcessBuilder cmd;
			try {
				String[] args = { "/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq" };
				cmd = new ProcessBuilder(args);
				Process process = cmd.start();
				InputStream in = process.getInputStream();
				byte[] re = new byte[24];
				while (in.read(re) != -1) {
					result = result + new String(re);
				}
				in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				result = "N/A";
			}
			return result.trim();
		}

		/** 设备CPU当前频率 */
		public String getPhoneCpuCurrentFreq() {
			String result = "N/A";
			try {
				FileReader fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
				BufferedReader br = new BufferedReader(fr);
				String text = br.readLine();
				result = text.trim();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		/** 设备CPU名称 */
		public String getPhoneCpuName() {
			try {
				FileReader fr = new FileReader("/proc/cpuinfo");
				BufferedReader br = new BufferedReader(fr);
				String text = br.readLine();
				String[] array = text.split(":\\s+", 2);
				return array[1];
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		/** 设备CPU数量 */
		public int getPhoneCpuNumber() {
			class CpuFilter implements FileFilter {
				public boolean accept(File pathname) {
					if (Pattern.matches("cpu[0-9]", pathname.getName())) {
						return true;
					}
					return false;
				}
			}
			try {
				File dir = new File("/sys/devices/system/cpu/");
				File[] files = dir.listFiles(new CpuFilter());
				return files.length;
			} catch (Exception e) {
				e.printStackTrace();
				return 1;
			}
		}

		/**
		 * 获取手机分辨率
		 */
		public String getResolution() {
			String resolution = "";
			DisplayMetrics metrics = new DisplayMetrics();

			((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

			resolution = metrics.widthPixels + "*" + metrics.heightPixels;

			return resolution;
		}

		private boolean isGranterCameraPermission() {
			PackageManager pm = context.getPackageManager();
			String permission = Manifest.permission.CAMERA;
			int result = pm.checkPermission(permission, context.getPackageName());
			// 检查是否有权限
			if (result == PackageManager.PERMISSION_GRANTED) {
				return true;
			}
			return false;
		}

		/**
		 * 获取照片最大分辨率
		 */
		public String getMaxPhotoSize() {
			try {
				if (isGranterCameraPermission()) {
					String maxSize = "";
					Camera camera = Camera.open();
					Camera.Parameters parameters = camera.getParameters();
					List<Size> sizes = parameters.getSupportedPictureSizes();
					Size size = null;
					for (Size s : sizes) {
						if (size == null) {
							size = s;
						} else if (size.height * size.width < s.height * s.width) {
							size = s;
						}
					}
					maxSize = size.width + "*" + size.height;
					camera.release();
					return maxSize;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return "未同意此权限";
		}

		/**
		 * 获取相机最大尺寸
		 */
		public String getCameraResolution() {
			if (isGranterCameraPermission()) {
				String cameraResolution = "";
				Camera camera = Camera.open();
				Camera.Parameters parameters = camera.getParameters();
				List<Size> sizes = parameters.getSupportedPictureSizes();
				Size size = null;
				for (Size s : sizes) {
					if (size == null) {
						size = s;
					} else if (size.height * s.width < s.height * s.width) {
						size = s;
					}
				}
				cameraResolution = (size.width * size.height) / 10000 + "万像素";
				camera.release();
				return cameraResolution;
			}
			return "未同意此权限";
		}

		/**
		 * 获取闪光灯状态
		 */
		public String getFlashMode() {
			if (isGranterCameraPermission()) {
				String flashMode = "";
				Camera camera = Camera.open();
				Camera.Parameters parameters = camera.getParameters();
				flashMode = parameters.getFlashMode();
				camera.release();
				return flashMode;
			}
			return "未同意此权限";
		}

		/**
		 * 获取像素密度
		 */
		public float getPixDensity() {
			float density = 0;
			density = context.getResources().getDisplayMetrics().density;
			return density;
		}

		/**
		 * 判断设备是否支持多点触控
		 */
		public boolean isSupportMultiTouch() {
			PackageManager pm = context.getPackageManager();
			boolean isSupportMultiTouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
			return isSupportMultiTouch;
		}

		// 是否同意蓝牙权限
		private boolean isGranterBluetoothPermission() {
			PackageManager pm = context.getPackageManager();
			String permission = Manifest.permission.BLUETOOTH;
			int result = pm.checkPermission(permission, context.getPackageName());
			// 检查是否有权限
			if (result == PackageManager.PERMISSION_GRANTED) {
				return true;
			}
			return false;
		}

		/** 获取蓝牙连接状态 */
		public String getBlueToothState() {
			BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
			if (bAdapter == null) {
				return "设备不支持蓝牙";
			}
			if (isGranterBluetoothPermission()) {
				int state = bAdapter.getState();
				switch (state) {
				case BluetoothAdapter.STATE_TURNING_OFF:
					return "蓝牙关闭中";
				case BluetoothAdapter.STATE_TURNING_ON:
					return "蓝牙开启中";
				case BluetoothAdapter.STATE_OFF:
					return "蓝牙关闭";
				case BluetoothAdapter.STATE_ON:
					return "蓝牙开启";
				}
				return "未知";
			}
			return "未同意此权限";
		}
	}

	/** 系统数据 */
	public class SystemInfo2 {
		private SystemInfo2() {
		}

		/** 设备系统基带版本 */
		public String getPhoneSystemBasebandVersion() {
			return Build.RADIO;
		}

		/** 设备系统版本号 (4.1.2?) */
		public String getPhoneSystemVersion() {
			return Build.VERSION.RELEASE;
		}

		/** 设备系统SDK版本号 (16?) */
		public int getPhoneSystemVersionSDK() {
			return Build.VERSION.SDK_INT;
		}

		/** 设备设置版本号 */
		public String getPhoneSystemVersionID() {
			// Build.DISPLAY
			return Build.ID;
		}

		/**
		 * 判断当前手机是否有ROOT权限
		 * 
		 * @return
		 */
		public boolean isRoot() {
			boolean bool = false;

			try {
				if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
					bool = false;
				} else {
					bool = true;
				}
			} catch (Exception e) {

			}
			return bool;
		}
	}

}
