package com.example.phone.entity;

import android.graphics.drawable.Drawable;

import com.example.phone.R.drawable;

public class ClearEntity {
	//APPͼ��
	private Drawable icon;
    //APP����
	private String name;
    //��С
	private long size;
    //APP�İ���
    private  boolean  ischecked;
    //APP�İ��� �Ӷ����Ի������
    private String ApkName;
    //APP��·��
    private String filePath;
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public boolean isIschecked() {
		return ischecked;
	}
	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}
	public String getApkName() {
		return ApkName;
	}
	public void setApkName(String apkName) {
		ApkName = apkName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public ClearEntity(Drawable icon, String name, long size,
			boolean ischecked, String apkName, String filePath) {
		super();
		this.icon = icon;
		this.name = name;
		this.size = size;
		this.ischecked = ischecked;
		ApkName = apkName;
		this.filePath = filePath;
	}
	public ClearEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
