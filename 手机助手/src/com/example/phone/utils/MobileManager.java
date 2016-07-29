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
 * ���������ֻ�ϵͳ�����ҵ���߼������ݴ���,�����豸��Wifi,Phone��System��Ϣ;
 * 
 */
public class MobileManager {
	/** �����Ψһʵ������ */
	private static MobileManager phoneManager;
	private Context context;
	private WifiInfo2 wifiInfo2; // Wifi��Ϣʵ������(�����ڲ���)
	private PhoneInfo2 phoneInfo2; // �豸��Ϣʵ������(�����ڲ���)
	private SystemInfo2 systemInfo2; // ϵͳ��Ϣʵ������(�����ڲ���)

	private MobileManager(Context context) {
		this.context = context;
		wifiInfo2 = new WifiInfo2(context);
		phoneInfo2 = new PhoneInfo2(context);
		systemInfo2 = new SystemInfo2();
	}

	/**
	 * ��ȡ�����Ψһʵ������ @see {@link #wifiInfo2}, {@link #phoneInfo2},
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

	/** Wifi���� */
	public class WifiInfo2 {
		private WifiManager wifiManager;

		private WifiInfo2(Context context) {
			wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		}

		/** �豸Wifi���� */
		public String getPhoneWifiName() {
			WifiInfo info = wifiManager.getConnectionInfo();
			return info.getSSID() + "";
		}

		/** �豸Wifi��IP */
		public String getPhoneWifiIP() {
			WifiInfo info = wifiManager.getConnectionInfo();
			long ip = info.getIpAddress();
			return longToIP(ip);
		}

		/** �豸Wifi���ٶ� */
		public String getPhoneWifiSpeed() {
			WifiInfo info = wifiManager.getConnectionInfo();
			return info.getLinkSpeed() + "";
		}

		/** �豸Wifi��MAC */
		public String getPhoneWifiMac() {
			WifiInfo info = wifiManager.getConnectionInfo();
			return info.getMacAddress() + "";
		}

		private String longToIP(long longIp) {
			StringBuffer sb = new StringBuffer("");
			// ����24λ��0
			sb.append(String.valueOf((longIp & 0x000000FF)));
			sb.append(".");
			// ����1λ��0��Ȼ������8λ
			sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
			sb.append(".");
			// ����8λ��0��Ȼ������16λ
			sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
			sb.append(".");
			// ֱ������24λ
			sb.append(String.valueOf((longIp >>> 24)));
			return sb.toString();
		}
	}

	/** �豸���� */
	public class PhoneInfo2 {
		private Context context;
		private ConnectivityManager connManager;
		private TelephonyManager telManager;

		private PhoneInfo2(Context context) {
			this.context = context;
			connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		}

		/** �豸CPU�������� (Ʒ�ƣ�) */
		public String getPhoneCPUName() {
			return Build.CPU_ABI;
		}

		/** �豸Ʒ��(moto?) */
		public String getPhoneName1() {
			return Build.BRAND.toUpperCase();
		}

		/** �豸������(moto?) */
		public String getPhoneName2() {
			return Build.MANUFACTURER;
		}

		/** �豸�ͺ�����(xt910) */
		public String getPhoneModelName() {
			// �������� PRODUCT
			return Build.MODEL.toUpperCase();
		}

		// �Ƿ�ͬ��ACCESS_NETWORK_STATEȨ��
		private boolean isGranterNetworkPermission() {
			PackageManager pm = context.getPackageManager();
			String permission = Manifest.permission.ACCESS_NETWORK_STATE;
			int result = pm.checkPermission(permission, context.getPackageName());
			// �ж��Ƿ���READ_PHONE_STATEȨ��
			if (result == PackageManager.PERMISSION_GRANTED) {
				return true;
			}
			return false;
		}

		/** �豸������������ (OFFLINE ? WIFI ? MOBILE) permission.ACCESS_NETWORK_STATE */
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
			return "δͬ���Ȩ��";
		}

		private boolean isOnline(NetworkInfo info) {
			if (info != null && info.isConnected()) {
				return true;
			}
			return false;
		}

		// �Ƿ�ͬ��READ_PHONE_STATEȨ��
		private boolean isGranterReadPhonePermission() {
			PackageManager pm = context.getPackageManager();
			String permission = Manifest.permission.READ_PHONE_STATE;
			int result = pm.checkPermission(permission, context.getPackageName());
			// �ж��Ƿ���READ_PHONE_STATEȨ��
			if (result == PackageManager.PERMISSION_GRANTED) {
				return true;
			}
			return false;
		}

		/**
		 * �豸�绰���� (�������ж����õ�����Ӫ�̽��ֻ�������д�뵽sim���еľ���,һ���ò���) Permission:
		 * READ_PHONE_STATE
		 */
		public String getPhoneNumber() {
			// ���ͬ����Ȩ��-ȡ����ź���
			if (isGranterReadPhonePermission()) {
				return telManager.getLine1Number();
			}
			return "δͬ���Ȩ��";
		}

		/** �豸��Ӫ������ (�й��ƶ����й���ͨ��) */
		public String getPhoneTelSimName() {
			return telManager.getSimOperatorName();
		}

		/** �豸���� permission.READ_PHONE_STATE */
		public String getPhoneIMEI() {
			// ����Ƿ���Ȩ��
			if (isGranterReadPhonePermission()) {
				return telManager.getDeviceId();
			}
			return "δͬ���Ȩ��";
		}

		/** �豸CPU���Ƶ�� */
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

		/** �豸CPU��СƵ�� */
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

		/** �豸CPU��ǰƵ�� */
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

		/** �豸CPU���� */
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

		/** �豸CPU���� */
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
		 * ��ȡ�ֻ��ֱ���
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
			// ����Ƿ���Ȩ��
			if (result == PackageManager.PERMISSION_GRANTED) {
				return true;
			}
			return false;
		}

		/**
		 * ��ȡ��Ƭ���ֱ���
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
			
			return "δͬ���Ȩ��";
		}

		/**
		 * ��ȡ������ߴ�
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
				cameraResolution = (size.width * size.height) / 10000 + "������";
				camera.release();
				return cameraResolution;
			}
			return "δͬ���Ȩ��";
		}

		/**
		 * ��ȡ�����״̬
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
			return "δͬ���Ȩ��";
		}

		/**
		 * ��ȡ�����ܶ�
		 */
		public float getPixDensity() {
			float density = 0;
			density = context.getResources().getDisplayMetrics().density;
			return density;
		}

		/**
		 * �ж��豸�Ƿ�֧�ֶ�㴥��
		 */
		public boolean isSupportMultiTouch() {
			PackageManager pm = context.getPackageManager();
			boolean isSupportMultiTouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
			return isSupportMultiTouch;
		}

		// �Ƿ�ͬ������Ȩ��
		private boolean isGranterBluetoothPermission() {
			PackageManager pm = context.getPackageManager();
			String permission = Manifest.permission.BLUETOOTH;
			int result = pm.checkPermission(permission, context.getPackageName());
			// ����Ƿ���Ȩ��
			if (result == PackageManager.PERMISSION_GRANTED) {
				return true;
			}
			return false;
		}

		/** ��ȡ��������״̬ */
		public String getBlueToothState() {
			BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
			if (bAdapter == null) {
				return "�豸��֧������";
			}
			if (isGranterBluetoothPermission()) {
				int state = bAdapter.getState();
				switch (state) {
				case BluetoothAdapter.STATE_TURNING_OFF:
					return "�����ر���";
				case BluetoothAdapter.STATE_TURNING_ON:
					return "����������";
				case BluetoothAdapter.STATE_OFF:
					return "�����ر�";
				case BluetoothAdapter.STATE_ON:
					return "��������";
				}
				return "δ֪";
			}
			return "δͬ���Ȩ��";
		}
	}

	/** ϵͳ���� */
	public class SystemInfo2 {
		private SystemInfo2() {
		}

		/** �豸ϵͳ�����汾 */
		public String getPhoneSystemBasebandVersion() {
			return Build.RADIO;
		}

		/** �豸ϵͳ�汾�� (4.1.2?) */
		public String getPhoneSystemVersion() {
			return Build.VERSION.RELEASE;
		}

		/** �豸ϵͳSDK�汾�� (16?) */
		public int getPhoneSystemVersionSDK() {
			return Build.VERSION.SDK_INT;
		}

		/** �豸���ð汾�� */
		public String getPhoneSystemVersionID() {
			// Build.DISPLAY
			return Build.ID;
		}

		/**
		 * �жϵ�ǰ�ֻ��Ƿ���ROOTȨ��
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
