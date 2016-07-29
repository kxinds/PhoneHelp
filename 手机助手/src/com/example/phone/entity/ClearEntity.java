package com.example.phone.entity;

import android.graphics.drawable.Drawable;

import com.example.phone.R.drawable;

public class ClearEntity {
	//APP图标
	private Drawable icon;
    //APP名称
	private String name;
    //大小
	private long size;
    //APP的包名
    private  boolean  ischecked;
    //APP的包名 从而可以获得名字
    private String ApkName;
    //APP的路径
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
