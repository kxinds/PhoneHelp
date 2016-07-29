package com.example.phone.entity;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class ProgramEntity {
  private Drawable img;
  private String   info;
  private String   info2;
  private boolean  ischeck;
  private  Intent intent;
  
  
public Intent getIntent() {
	return intent;
}
public void setIntent(Intent intent) {
	this.intent = intent;
}
public boolean isIscheck() {
	return ischeck;
}
public void setIscheck(boolean ischeck) {
	this.ischeck = ischeck;
}
public Drawable getImg() {
	return img;
}
public void setImg(Drawable img) {
	this.img = img;
}
public String getInfo() {
	return info;
}
public void setInfo(String info) {
	this.info = info;
}
public String getInfo2() {
	return info2;
}
public void setInfo2(String info2) {
	this.info2 = info2;
}
public ProgramEntity(Drawable img, String info, String info2,boolean ischeck) {
	super();
	this.img = img;
	this.info = info;
	this.info2 = info2;
	this.ischeck=ischeck;
}
public ProgramEntity() {
	super();
	// TODO Auto-generated constructor stub
}
  
  

}
