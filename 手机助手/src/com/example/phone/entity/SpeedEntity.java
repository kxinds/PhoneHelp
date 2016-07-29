package com.example.phone.entity;

import android.graphics.drawable.Drawable;

public class SpeedEntity {
private String name;
private boolean checked;
private Drawable icon;
private long size;
private String packagename;

public String getPackagename() {
	return packagename;
}
public void setPackagename(String packagename) {
	this.packagename = packagename;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public boolean isChecked() {
	return checked;
}
public void setChecked(boolean checked) {
	this.checked = checked;
}
public Drawable getIcon() {
	return icon;
}
public void setIcon(Drawable icon) {
	this.icon = icon;
}
public long getSize() {
	return size;
}
public void setSize(long size) {
	this.size = size;
}
public SpeedEntity(String name, boolean checked, Drawable icon, long size,String packagename) {
	super();
	this.name = name;
	this.checked = checked;
	this.icon = icon;
	this.size = size;
	this.packagename=packagename;
}
public SpeedEntity() {
	super();
	// TODO Auto-generated constructor stub
}


}
